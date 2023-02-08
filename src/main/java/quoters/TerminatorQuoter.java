package quoters;

import quoters.annots.DeprecatedClass;
import quoters.annots.InjectRandomInt;
import quoters.annots.PostProxy;
import quoters.annots.Profiling;

import javax.annotation.PostConstruct;

@Profiling
        // подменяем в обьект терминатора на т100 в бинфакторипостпроцессор
@DeprecatedClass(newImpl = T1000.class)
public class TerminatorQuoter implements Quoter {

    @InjectRandomInt(min = 1, max = 7)
    private int repeat;
    private String message;

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    /* изначально не работает потому что аннотации нет
     * @postconstruct всегда работает на оригинальный метод до того как все прокси накрутились на него
     */

    @PostConstruct
    public void init() {
        /* изначально xml ничего не знает про аннотации
         * знают бинпостпроцессоры, эту аннотуцию обрабатывает коммонаннотэйшинбинпостпроцессор
         */
        System.out.println("PHASE 2 = " + repeat);
    }

    @Override
//    @PostConstruct
    @PostProxy
    public void sayQuote() {
        System.out.println("PHASE 3");
        for (int i = 0; i < repeat; i++) {
            System.out.println("message = " + message);
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TerminatorQuoter() {
        /* 1. обьект создается java
         * спринг просканировал xml -> создал beanDefinition ->
         * спринг понял что нужно создать singleton TerminatorQuater ->
         * при помощи reflection api спринг запусти конструктор терминатора
         *
         * конструктор отработал -> обьект создался -> теперь спринг может его настраивать
         * (мы пытаемся обратиться в кострукторе к вещам которые спринг еще не настроил то мы имеем в данном слч ноль)
         */
        System.out.println("PHASE 1 = " + repeat);

    }
}
