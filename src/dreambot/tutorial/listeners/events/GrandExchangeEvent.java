package dreambot.tutorial.listeners.events;

import dreambot.tutorial.listeners.base.AbstractEvent;
import dreambot.tutorial.listeners.base.EventInterface;
import dreambot.tutorial.listeners.impl.GrandExchangeListener;
import dreambot.tutorial.listeners.wrappers.GrandExchangeItemWrapper;
import org.dreambot.api.Client;
import org.dreambot.api.methods.grandexchange.GrandExchange;
import org.dreambot.api.methods.grandexchange.GrandExchangeItem;
import org.dreambot.api.methods.grandexchange.Status;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.AbstractScript;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GrandExchangeEvent extends AbstractEvent implements EventInterface {

    private final GrandExchangeListener event;

    public GrandExchangeEvent(AbstractScript script) {
        super(script);
        this.event = (GrandExchangeListener) pEvent;
    }


    @Override
    public final void run() {
        Map<Integer, GrandExchangeItemWrapper> current, next = null;
        List<GrandExchangeItemWrapper> difference;

        while (!shouldStop() && canRun()) {
            if (canVerify()) {
                current = fetchItems();
                if (current != null) {
                    difference = getDifference(next, current);
                    if (difference != null && !difference.isEmpty()) {
                        for (GrandExchangeItemWrapper i : difference) {
                            fire(i);
                        }
                    }

                    next = new HashMap<>(current);
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }
    }


    @Override
    public void fire(Object... params) {
        if (params != null && params.length > 0 && params[0] != null) {
            GrandExchangeItemWrapper item = (GrandExchangeItemWrapper) params[0];
            if (item.getStatus() != null && item.getName() != null && item.getId() != 0 && item.getPrice() != 0) {
                if (item.getStatus() == Status.BUY_COLLECT) {
                    this.event.onItemBought(item);
                } else if (item.getStatus() == Status.SELL_COLLECT) {
                    this.event.onItemSold(item);
                }
            }
        }
    }

    private List<GrandExchangeItemWrapper> getDifference(Map<Integer, GrandExchangeItemWrapper> before,
                                                         Map<Integer, GrandExchangeItemWrapper> after) {
        if (before == null || after == null) {
            return null;
        }

        List<GrandExchangeItemWrapper> list = new ArrayList<>();
        for (Map.Entry<Integer, GrandExchangeItemWrapper> entry : before.entrySet()) {
            int slot = entry.getKey();
            GrandExchangeItemWrapper item1 = entry.getValue();
            GrandExchangeItemWrapper item2 = after.get(slot);

            if ((item1.getStatus() == Status.SELL_COLLECT || item1.getStatus() == Status.BUY_COLLECT) && item2.getStatus() == Status.EMPTY) {
                continue;
            }

            if (!item1.equals(item2)) {
                if (item1.getStatus() == Status.EMPTY) {
                    list.add(item2);
                } else {
                    list.add(item1);
                }
            }
        }

        return list;
    }

    private Map<Integer, GrandExchangeItemWrapper> fetchItems() {
        GrandExchangeItem[] items = GrandExchange.getItems();
        if (items != null && items.length > 0) {
            Map<Integer, GrandExchangeItemWrapper> map = new HashMap<>();
            for (int i = 0; i < items.length; i++) {
                map.put(i, new GrandExchangeItemWrapper(items[i]));
            }

            return map;
        }

        return null;
    }

    private boolean canVerify() {
        return Client.isLoggedIn() && !Client.getInstance().getRandomManager().isSolving()
                && Players.localPlayer() != null && Players.localPlayer().exists();
    }

    private boolean shouldStop() {
        return !Client.getInstance().getScriptManager().isRunning();
    }
}
