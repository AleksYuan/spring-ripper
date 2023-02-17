package screensaver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

@Component
//@Scope("prototype")
//public class ColorFrame extends JFrame {
public abstract class ColorFrame extends JFrame {
    // изначальная сборка
//        @Autowired
//        private Color color;

    /**
     * решение 1 (неверное)
     * колор не инжектим здесь, инжектим контекст
     */
//    @Autowired
//    private ApplicationContext context;

    /**
     * решение 2 (нормальное) лукап метод это остается вместе с Color color
     * - этого решения в том что мы при обращении к колор всегда создаем новый бин колора
     */

    /**
     * решение через геттер (прописываем метод гетколор к метод показать в рандомном месте
     * ничего не инжектим сразу
     */

    public ColorFrame() {
        setSize(200, 200);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void showOnRandomPlace() {
        Random random = new Random();
        setLocation(random.nextInt(1000), random.nextInt(1000));
        getContentPane().setBackground(getColor());
        repaint();
    }
//        Решение 2
//        getContentPane().setBackground(context.getBean(Color.class));
//        repaint();

    // решение 3
    protected abstract Color getColor();

}
