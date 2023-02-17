package quoters.cl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import quoters.annots.PostProxy;

import java.lang.reflect.Method;

/* Пишем свой лисенер
 * context started - контекст начал свое построение
 * context stopped - закончил построение
 * context refreshed - самый интересный. Когда контекст заканчивает построение он всегда будет делать рефреш
 *
 *
 * этот лисенер имплементит интерфейс с дженериком событие обновления бина в контексте
 * то есть когда происходит его обновление то вызывается также этот лисенер
 *
 * он проверяет все методы бина который рефрешнулся и если у метода имеется метод с аннотацией постпрокси
 * то этот метод выполняется
 */

public class PostProxyInvokerContextListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ConfigurableListableBeanFactory factory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            BeanDefinition beanDefinition = factory.getBeanDefinition(name);
            String originalClassName = beanDefinition.getBeanClassName();
            try {
                Class<?> originalClass = Class.forName(originalClassName);
                Method[] Methods = originalClass.getDeclaredMethods();
                for (Method method : Methods) {
                    if (method.isAnnotationPresent(PostProxy.class)) {
                        Object bean = context.getBean(name);
                        Method currentMethod = bean.getClass()
                                .getMethod(method.getName(), method.getParameterTypes());

                        currentMethod.invoke(bean);
                    }
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }
}
