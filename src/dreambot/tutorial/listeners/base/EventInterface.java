package dreambot.tutorial.listeners.base;

public interface EventInterface {

    void start();

    void run();

    void stop();

    void fire(Object... params);

}
