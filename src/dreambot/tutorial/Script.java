package dreambot.tutorial;

import dreambot.tutorial.listeners.ListenerManager;
import dreambot.tutorial.listeners.events.GrandExchangeEvent;
import dreambot.tutorial.listeners.impl.GrandExchangeListener;
import dreambot.tutorial.listeners.wrappers.GrandExchangeItemWrapper;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;


@ScriptManifest(name = "Listeners Test", version = 1.0, category = Category.MISC, author = "Neffarion", description = "")
public class Script extends AbstractScript implements GrandExchangeListener {

    @Override
    public void onStart() {
        ListenerManager.getInstance().addListener(new GrandExchangeEvent(this));
    }

    @Override
    public int onLoop() {
        return 2000;
    }

    @Override
    public void onItemBought(GrandExchangeItemWrapper item) {
        MethodProvider.log("Item bought: " + item.getName());
    }

    @Override
    public void onItemSold(GrandExchangeItemWrapper item) {
        MethodProvider.log("Item sold: " + item.getName());
    }

}
