package quoters.controller;

public class ProfilingController implements ProfilingControllerMBean {
    private boolean enable = true;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}

/* есть МБИН сервер, когда поднимается джава процесс вместе с ним поднимается МБИН сервер
 * все обьекты которые зарегистрированы в нем через GMX console можно менять (запускать методы например)
 * С помощью МБИН можно через GMXconsole можно мет
 */