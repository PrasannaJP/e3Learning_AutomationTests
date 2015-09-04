package com.webuiframework.oua.uitests.panel;

import com.webuiframework.oua.uitests.control.Element;
import com.webuiframework.oua.uitests.utils.Sleeper;

public class BasePanel<T extends BasePanel> extends Element<T> {

    //Base Panel for future goal
    public final T logFailed(String msg) {
        return (T)this;
    }

    /**
     * Sleep in msec
     * @param msec - timeout in msec
     * @return BasePanel instance
     */
    public T sleep(long msec) {
        Sleeper.sleepTight(msec);
        return (T)this;
    }

    @Override
    public T waitForExists() {
        super.waitForExists();
        return (T)this;
    }

}
