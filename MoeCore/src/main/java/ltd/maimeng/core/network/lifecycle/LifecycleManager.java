package ltd.maimeng.core.network.lifecycle;

import java.util.ArrayList;

/**
 * 生命周期监听器
 */
public class LifecycleManager {

    private ArrayList<Lifecycle> monitorList = new ArrayList<>();

    public void add(Lifecycle lifecycle) {
        monitorList.add(lifecycle);
    }

    public void remove(Lifecycle lifecycle) {
        monitorList.remove(lifecycle);
    }

    public void remove(String id) {
        for (int i = 0; i < monitorList.size(); i++) {
            Lifecycle lifecycle = monitorList.get(i);
            if (lifecycle.getId().equals(id)) {
                monitorList.remove(lifecycle);
                break;
            }
        }
    }

    public synchronized void onResume() {
        for (int i = 0; i < monitorList.size(); i++) {
            Lifecycle lifecycle = monitorList.get(i);
            lifecycle.onResume();
        }
    }

    public synchronized void onPause() {
        for (int i = 0; i < monitorList.size(); i++) {
            Lifecycle lifecycle = monitorList.get(i);
            lifecycle.onPause();
        }
    }

    public synchronized void onStop() {
        for (int i = 0; i < monitorList.size(); i++) {
            Lifecycle lifecycle = monitorList.get(i);
            lifecycle.onStop();
        }
    }

    public synchronized void onDestroy() {
        for (int i = 0; i < monitorList.size(); i++) {
            Lifecycle lifecycle = monitorList.get(i);
            lifecycle.onDestroy();
        }
        monitorList.clear();
        // monitorList = null;
    }
}
