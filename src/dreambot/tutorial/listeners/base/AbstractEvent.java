package dreambot.tutorial.listeners.base;

import org.dreambot.api.script.AbstractScript;

import java.util.EventListener;

public abstract class AbstractEvent implements EventInterface {

    protected EventListener parentEvent;
    private Thread thread;
    private volatile boolean run = true;

    public AbstractEvent(AbstractScript script) {
        this.parentEvent = script;
    }

    public boolean canRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public Thread getThread() {
        return thread;
    }

    protected void setThread(Thread thread) {
        this.thread = thread;
    }

    @Override
    public void start() {
        setThread(new Thread(this::run));
        getThread().start();
    }

    @Override
    public final void stop() {
        this.setRun(false);
        setThread(null);
    }

}
