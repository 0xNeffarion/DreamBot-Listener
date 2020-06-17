package dreambot.tutorial.listeners.wrappers;

import org.dreambot.api.methods.grandexchange.GrandExchangeItem;
import org.dreambot.api.methods.grandexchange.Status;

public final class GrandExchangeItemWrapper {

    private final int id;
    private final int slot;
    private final int amount;
    private final int price;
    private final Status status;
    private final String name;

    public GrandExchangeItemWrapper(GrandExchangeItem item) {
        this.id = item.getID();
        this.slot = item.getSlot();
        this.amount = item.getAmount();
        this.price = item.getPrice();
        this.name = item.getName();
        this.status = item.getStatus();
    }

    public int getId() {
        return id;
    }

    public int getSlot() {
        return slot;
    }

    public int getAmount() {
        return amount;
    }

    public int getPrice() {
        return price;
    }

    public Status getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GrandExchangeItemWrapper that = (GrandExchangeItemWrapper) o;

        if (that.status == null) return false;
        if (!name.equals(that.name)) return false;
        if (id != that.id) return false;
        if (slot != that.slot) return false;
        if (amount != that.amount) return false;
        if (price != that.price) return false;

        return status.equals(that.status);
    }

}
