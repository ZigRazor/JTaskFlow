package jtaskflow.core;

import java.util.List;
import java.util.Objects;

public abstract class JTFNodeInterface {

    public enum NodeState{
        INITIALIZED,
        WAITING,
        RUNNING,
        PAUSED,
        STOPPED,
        FINISHED
    }
    final private String name;
    protected List<JTFNodeInterface> predecessors = null;
    protected List<JTFNodeInterface> successors = null;
    private NodeState state = NodeState.INITIALIZED;

    public JTFNodeInterface(String name){
        this.name = name;
    }

    public List<JTFNodeInterface> getPredecessors() {
        return this.predecessors;
    }

    public List<JTFNodeInterface> getSuccessors() {
        return this.successors;
    }

    public JTFNodeInterface precede(JTFNodeInterface node) {
        this.successors.add(node);
        node.predecessors.add(this);
        return this;
    }

    public JTFNodeInterface succeed(JTFNodeInterface node) {
        this.predecessors.add(node);
        node.successors.add(this);
        return this;
    }

    public JTFNodeInterface precede(List<JTFNodeInterface> nodes) {
        this.successors.addAll(nodes);
        for (var node : nodes) {
            node.predecessors.add(this);
        }
        return this;
    }

    public JTFNodeInterface succeed(List<JTFNodeInterface> nodes) {
        this.predecessors.addAll(nodes);
        for (var node : nodes) {
            node.successors.add(this);
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof JTFNodeInterface)) {
            return false;
        }
        JTFNodeInterface jFTTaskInterface = (JTFNodeInterface) o;
        return Objects.equals(predecessors, jFTTaskInterface.predecessors)
                && Objects.equals(successors, jFTTaskInterface.successors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(predecessors, successors);
    }

    @Override
    public String toString() {
        return "{" +
                " predecessor='" + getPredecessors() + "'" +
                ", successor='" + getSuccessors() + "'" +
                "}";
    }


    /**
     * @return TaskState return the state
     */
    public synchronized NodeState getState() {
        return state;
    }

    public synchronized void setWaitingState(){
        this.state = NodeState.WAITING;
    }

    public synchronized void setRunningState(){
        this.state = NodeState.RUNNING;
    }

    public synchronized void setPausedState(){
        this.state = NodeState.PAUSED;
    }

    public synchronized void setStoppedState(){
        this.state = NodeState.STOPPED;
    }

    public synchronized void setFinishedState(){
        this.state = NodeState.FINISHED;
    }

    public boolean isTaskReadyToRun(){
        if(predecessors == null || predecessors.isEmpty()){
            return true;
        }else{
            for(var predecessor: predecessors){
                if(predecessor.getState() != NodeState.FINISHED){
                    return false;
                }
            }
            return true;
        }
        
    }

    public String getName() {
        return this.name;
    }

    public abstract void runNode() throws Exception;
    

}
