package jtaskflow.core;


import java.util.List;

import jtaskflow.core.JTFNodeInterface.NodeState;

public class JTFExecutor {

    private static JTFWorkerPool workerPool = null;

    public static void execute(JTaskFlow tf) {       
        workerPool = new JTFWorkerPool(1);
        workerPool.startWorkerPool();
        var nodes = tf.getNodes();
        while (!allTaskFinished(nodes)) {
            for (var node : nodes) {
                analyzeNode(node);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            printOutState(tf);
        }
        workerPool.stopWorkerPool();

    }

    private static boolean allTaskFinished(List<JTFNodeInterface> tasks) {
        for (var task : tasks) {
            if (task.getState() != NodeState.FINISHED) {
                return false;
            }
        }
        return true;
    }

    private static boolean atLeastOneTaskRunning(List<JTFNodeInterface> tasks){
        for (var task : tasks) {
            if (task.getState() == NodeState.RUNNING) {
                return true;
            }
        }
        return false;
    }

    private static void analyzeNode(JTFNodeInterface node) {
        if (node instanceof JTFTask) {
            if (node.getState() == NodeState.INITIALIZED) {
                if (node.isTaskReadyToRun()) {
                    node.setWaitingState();
                    workerPool.enqueueTask(node);
                }
            }
        } else if (node instanceof JTFSubFlow) {
            JTFSubFlow subFlow = (JTFSubFlow) node;
            if (subFlow.getState() == NodeState.INITIALIZED) {
                if (subFlow.isTaskReadyToRun()) {
                    subFlow.setWaitingState();
                    var subFlowNodes = subFlow.getNodes();
                     for (var subFlowNode : subFlowNodes) {
                        analyzeNode(subFlowNode);
                    }
                }
            }else if( subFlow.getState() == NodeState.WAITING){
                if(atLeastOneTaskRunning(subFlow.getNodes())){
                    subFlow.setRunningState();
                }
            }else if( subFlow.getState() == NodeState.RUNNING){
                if(allTaskFinished(subFlow.getNodes())){
                    subFlow.setFinishedState();
                }
            }
        }
    }

    private static void printOutState(JTaskFlow tf){
        for( var node : tf.getNodes()){
            System.out.println(node.getName() + " : " + node.getState().name());
        }
    }

    
}
