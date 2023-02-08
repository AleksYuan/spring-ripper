package quoters.annots;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface InjectRandomInt {
    int min();
    int max();
}

// 1. Важно поставить ретеншинполиси на рантайм (по умолчанию класс)