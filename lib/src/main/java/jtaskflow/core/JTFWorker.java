package jtaskflow.core;

import java.util.Queue;

public class JTFWorker {

    private Queue<JTFNodeInterface> queue = null;

    private JTFWorkerRunner runner = null;

    private Thread thread = null;

    public void initialize(Queue<JTFNodeInterface> queue) {
        if (this.queue == null && this.runner == null && this.thread == null) {
            this.queue = queue;
            this.runner = new JTFWorkerRunner(queue);
            this.thread = new Thread(runner);
        } else {
            throw new RuntimeException("JTFWorker already Initialized");
        }
    }

    public JTFWorker(Queue<JTFNodeInterface> queue) {
        initialize(queue);
    }

    public void startWorker() {
        if(thread != null){
            thread.start();
        }else{
            throw new RuntimeException("Worker Thread is null");
        }
    }
    /*
    public void enqueueTask(JTFTaskInterface task) {
        if (queue != null) {
            queue.add(task);
        }else{
            throw new RuntimeException("Worker Queue is null");
        }
    }
     */
    public int getQueueSize(){
        if (queue != null) {
            return queue.size();
        }else{
            throw new RuntimeException("Worker Queue is null");
        }
    }

    public void stopWorker() {
        if (runner != null) {
            runner.stop();
            if (thread != null) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
            throw new RuntimeException("Worker Thread is null");
        }
        }else{
            throw new RuntimeException("Worker Runner is null");
        }
        queue = null;
        runner = null;
        thread = null;
    }

    public void pauseWorker() {
        if (runner != null) {
            runner.pause();
        }else{
            throw new RuntimeException("Worker Runner is null");
        }
    }

    public void resumeWorker() {
        if (runner != null) {
            runner.resume();
        }else{
            throw new RuntimeException("Worker Runner is null");
        }
    }

}
