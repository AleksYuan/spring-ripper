package quoters;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("context.xml");
//        context.getBean(Quoter.class).sayQuote();
//        while (true) {
//            Thread.sleep(1000);
        /*
         *  с прифилированием не работает потому что надо делать лук ап по интерфейсу, потому что если мы достаем бин
         *  и генерируем его класс на лету у нас фактически изменяется название бина на ком.сан. что то там
         *  если раскоментить и поставить дебаг, можно вызвать контекст.гедбин(куотер.класс).геткласс
         */
//            context.getBean(TerminatorQuoter.class).sayQuote();
//            context.getBean(Quoter.class).sayQuote();
//        }

        context.getBean(Quoter.class);

    }




}



//1. BPP позволяет настроить бины до того как они попали в в ioc container (паттерн chaineOfresponsobility)
