package it.polimi.se2018.utils;

public class Timer extends Thread {
    private boolean timeout = false;
    private int time;
    private Object lock;

    public Timer(int time, Object lock) {
        if(lock == null) {
            throw new NullPointerException();
        }
        if(time < 0) {
            throw new IllegalArgumentException();
        }
        this.time = time;
        this.lock = lock;
    }

    public boolean isTimeout() {
        synchronized (lock) {
            return timeout;
        }
    }

    @Override
    public void run() {
        synchronized (lock) {
            timeout = false;
        }
        try {
            sleep(time * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        synchronized (lock) {
            timeout = true;
            lock.notifyAll();
        }
    }
}
