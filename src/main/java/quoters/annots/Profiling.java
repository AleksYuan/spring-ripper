package quoters.annots;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Profiling {
}

/* генерация класса подмены на лету, есть 2 подхода
 * 1. либо он наследуется от оригинального класса и переопределеяет все его методы (си джи лип)
 * 2. имплементит все интерфейсы которые есть у родителя (динамик прокси)
 *
 * си джи лип считается хуже, дело в ограничениях, есть классы файнал и методы -> не всегда мы можем наследоваться
 *
 * спринг предпочитает идти через динамик прокси
 * спринг сначала смотрит есть ли у класса интерфейсы, если есть то спринг идет через динамик прокси,
 * если нет через си джи лип
 *
 *
*/