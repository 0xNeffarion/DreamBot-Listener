package dreambot.tutorial.listeners;

import dreambot.tutorial.listeners.base.AbstractEvent;
import dreambot.tutorial.listeners.base.EventInterface;

import java.util.HashSet;
import java.util.Set;

public final class ListenerManager {

    private static ListenerManager instance = null;

    private final Set<AbstractEvent> listeners = new HashSet<>();

    private ListenerManager() {
    }

    public static ListenerManager getInstance() {
        if (instance == null) {
            instance = new ListenerManager();
        }

        return instance;
    }

    public Set<AbstractEvent> getListeners() {
        return listeners;
    }

    public void addListener(AbstractEvent listener) {
        listener.start();
        this.listeners.add(listener);
    }

    public void removeListeners() {
        this.listeners.forEach(EventInterface::stop);
        this.listeners.clear();
    }

}
