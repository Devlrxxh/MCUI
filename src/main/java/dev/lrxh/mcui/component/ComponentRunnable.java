package dev.lrxh.mcui.component;

public class ComponentRunnable implements Runnable {
    private final ComponentManager componentManager;

    public ComponentRunnable(ComponentManager componentManager) {
        this.componentManager = componentManager;
    }

    @Override
    public void run() {
        componentManager.run();
    }
}
