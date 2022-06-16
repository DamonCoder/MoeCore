package ltd.maimeng.core.network.status;


public interface NetStateChangeObserver {

    void onNetDisconnected();

    void onNetConnected(NetworkType networkType);
}
