package quoters.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import quoters.annots.Profiling;
import quoters.controller.ProfilingController;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProfilingHandlerBeanPostProcessor  implements BeanPostProcessor {

    private Map<String, Class> map = new HashMap<>();
    private ProfilingController controller = new ProfilingController();

    /** при регистрации МБИН сервер может быть много проблем, например мы не
     * инмплементировали интерфейс или этот бин уже зарегистрирован поэтому избавляемся прокидываем исключение
     * регистрируем обьект на сервере в конструкторе
     */
    public ProfilingHandlerBeanPostProcessor() throws Exception {
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        platformMBeanServer.registerMBean(controller, new ObjectName("profiling", "name", "controller"));
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Profiling.class)) {
            map.put(beanName, beanClass);
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }


    /** генерируем класс на лету
     * проверяем если наш объект есть в мапе то при условии true в контроллере мы
     * возвращаем его прокси, такой же обьект только добавляем в него логику через
     * newProxyInstance
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Class beanClass = map.get(beanName);

        if (beanClass!=null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if (controller.isEnable()) {
                        System.out.println("PROFILING....");
                        // вызываем оригинальный метод (передаем бин и аргументы оригинального методы
                        long start = System.nanoTime();
                        Object retVal = method.invoke(bean, args);
                        long finish = System.nanoTime();
                        System.out.println("time =" + (finish - start) + " PROFILING CANCEL");
                        return retVal;
                    } else {
                        return method.invoke(bean, args);
                    }
                }
            });
        }

        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
