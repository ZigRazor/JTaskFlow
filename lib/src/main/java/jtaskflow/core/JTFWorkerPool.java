package jtaskflow.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class JTFWorkerPool {
    
    private List<JTFWorker> workerPool = null;

    private Queue<JTFNodeInterface> queue;

    public JTFWorkerPool(int numberOfWorkers){
        workerPool = new ArrayList<>();
        queue = new ConcurrentLinkedQueue<>();
        for ( int i = 0; i < numberOfWorkers; i++){
            workerPool.add(new JTFWorker(queue));
        }
    }

    public void startWorkerPool() {
        for( var worker: workerPool){
            worker.startWorker();
        }
    }

    public void stopWorkerPool() {
        for( var worker: workerPool){
            worker.stopWorker();
        }
    }

    public void reinitializeWorkerPool(){
        queue = new ConcurrentLinkedQueue<>();
        for( var worker: workerPool){
            worker.initialize(queue);
        }
    }

    public void pauseWorkerPool() {
        for( var worker: workerPool){
            worker.pauseWorker();
        }
    }

    public void resumeWorkerPool() {
        for( var worker: workerPool){
            worker.resumeWorker();
        }
    }

    public void enqueueTask(JTFNodeInterface task) {
        if (queue != null) {
            queue.add(task);
        }else{
            throw new RuntimeException("WorkerPool Queue is null");
        }
    }

    

}
