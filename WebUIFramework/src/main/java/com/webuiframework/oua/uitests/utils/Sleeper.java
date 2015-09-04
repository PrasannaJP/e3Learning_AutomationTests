package com.webuiframework.oua.uitests.utils;

public class Sleeper {

    /**
     * Sleeps without explicitly throwing an InterruptedException
     *
     * @param timeoutInSeconds Sleep time in seconds.
     * @throws RuntimeException wrapping an InterruptedException if one gets thrown
     */
    public static void sleepTightInSeconds(long timeoutInSeconds) {
        sleepTight(timeoutInSeconds * 1000);
    }

    /**
     * Sleeps without explicitly throwing an InterruptedException
     *
     * @param timeout the amount of time to sleep
     * @throws RuntimeException wrapping an InterruptedException if one gets thrown
     */
    public static void sleepTight(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
