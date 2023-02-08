package screensaver;

import org.springframework.context.annotation.*;

import java.awt.*;
import java.util.Random;

@Configuration
@ComponentScan(basePackages = "screensaver")
public class JavaConfig {

    @Bean
//    @Scope(value = "prototype") //, proxyMode = ScopedProxyMode.TARGET_CLASS)
    @Scope("periodical") // пишем свой скопе, цвет меняется каждые три секунды
    public Color color() {
        Random random = new Random();
        return new Color(
                random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    @Bean
    public ColorFrame frame() {
        return new ColorFrame() {
            @Override
            protected Color getColor() {
                return color();
            }
        };
    }

    /* Этот бин изначально не меняется у нас создается синглтон при поднятии контекста
     *
     * 1 действие - меняем скопе на прототайп (если ставим то новые рамки создаются постоянно и заполняют экран)
     * 2 - оставляем прототайп только на фрэйм, на колор синглтон (не меняется цвет + создаются новые рамки и заполняют экран)
     * 3 - меняем прототайп на цвете а рамка одна (цвет создался но он не меняется)
     *
     * 1 решение инжектить контекст в колорфраме
     * 2 решение @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS) над колор
     * 3 решение сделать колор фржйм абстрактным, абстрактный метод гетколор, и инжектить в конфиге бин колор в фрэйм
     */

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(JavaConfig.class);

        while (true) {
            context.getBean(ColorFrame.class).showOnRandomPlace();
            Thread.sleep(2000);
        }
    }
}
