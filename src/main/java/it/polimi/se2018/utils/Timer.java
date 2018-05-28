package it.polimi.se2018.utils;

public class Timer extends Thread {
    private boolean timeout = true;
    private int time;

    public Timer() {
        this(0);
    }

    public Timer(int time) {
        if(time < 0) {
            throw new IllegalArgumentException();
        }
        this.time = time;
    }

    @Override
    public void run() {
        synchronized (this) {
            timeout = false;
        }
        try {
            sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (this) {
            timeout = true;
        }
    }

    public synchronized boolean isTimeout() {
        return timeout;
    }
}
