package quoters.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;
import quoters.annots.InjectRandomInt;

import java.lang.reflect.Field;

public class InjectRandomIntAnnotationBeanPostProcessor implements BeanPostProcessor {

    //Вызывается до инит метода
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] declaredField = bean.getClass().getDeclaredFields();
        for (Field field : declaredField) {
            InjectRandomInt annot = field.getAnnotation(InjectRandomInt.class);
            if (annot != null) {
                int min = annot.min();
                int max = annot.max();
                int i = (int) (min + Math.random()*(max - min));
                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, i);
            }
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
//        return bean;
    }

    /* Вызывается после инит метода
     * Те БПП которые что то меняют в классе должны это делать на моменте bppAI
     * т.к до этого менять не чего. До постконстракт или до инит ничего еще не проиницилизировалось
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
//        return bean;
    }
}
