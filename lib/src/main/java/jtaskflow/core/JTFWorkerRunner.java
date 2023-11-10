package jtaskflow.core;

import java.util.NoSuchElementException;
import java.util.Queue;

public class JTFWorkerRunner implements Runnable {

    final private Queue<JTFNodeInterface> queue;

    private boolean stopped = false;
    private boolean paused = false;

    public JTFWorkerRunner(Queue<JTFNodeInterface> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!isStopped()) {

            if (!isPaused() && !queue.isEmpty()) {
                try {
                    var task = dequeueTask();
                    try {
                        task.setRunningState();
                        task.runNode();
                        task.setFinishedState();
                    } catch (Exception e) {
                        task.setFinishedState();
                        e.printStackTrace();
                    }
                } catch (NoSuchElementException e1) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }

                }

            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private JTFNodeInterface dequeueTask() {
        return queue.remove();
    }

    private synchronized boolean isStopped() {
        return stopped;
    }

    private synchronized boolean isPaused() {
        return paused;
    }

    public synchronized void stop() {
        stopped = true;
    }

    public synchronized void pause() {
        paused = true;
    }

    public synchronized void resume() {
        paused = false;
    }

}
