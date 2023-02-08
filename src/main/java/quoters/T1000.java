package quoters;

import quoters.annots.InjectRandomInt;

/*
 * Если не имплементировать интерфейс то метод не отработает как прописано в мэйне,
 * потому что мы вызываем метод
 */
public class T1000 extends TerminatorQuoter implements Quoter{

//    @InjectRandomInt(min = 1, max = 7)
//    private int repeat;

    @Override
    public void sayQuote() {
//        for (int i = 0; i < repeat; i++) {
            System.out.println("Я Т1000 - второй терминатор");
//        }
    }
}
