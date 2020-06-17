package dreambot.tutorial.listeners.impl;

import dreambot.tutorial.listeners.wrappers.GrandExchangeItemWrapper;

import java.util.EventListener;

public interface GrandExchangeListener extends EventListener {

    void onItemBought(GrandExchangeItemWrapper item);

    void onItemSold(GrandExchangeItemWrapper item);

}
