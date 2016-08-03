package com.tcg.stopwatch;

/**
 * Created by JoseR on 8/2/2016.
 */
public class StopWatchThread extends Thread {

    private StopWatchFrame stopWatchFrame;

    public volatile boolean running;

    private volatile long startTime;

    private volatile long time;

    private volatile long pauseTime;

    public StopWatchThread(StopWatchFrame stopWatchFrame) {
        super();
        this.stopWatchFrame = stopWatchFrame;
        running = false;
        pauseTime = 0;
        time = 0;
    }

    @Override
    public void run() {
        while(true) {
            if(running) {
                time = (System.currentTimeMillis() - startTime) + pauseTime;

                double seconds = ((time / 1000.0) % 60);
                long millis = (long) ((seconds - (long) seconds) * 1000);
                long minutes = ((time / (1000*60)) % 60);
                long hours   = ((time / (1000*60*60)) % 24);
                stopWatchFrame.setTime(hours, minutes, (long) seconds, millis);
            }
        }
    }

    public void startStopWatch() {
        try {
            start();
        } catch (Exception e){
        } finally {
            startTime = System.currentTimeMillis();
            running = true;
        }
    }

    public void stopStopWatch() {
        running = false;
        pauseTime = 0;
    }

    public void pauseStopWatch() {
        running = false;
        pauseTime = time;
    }

}
