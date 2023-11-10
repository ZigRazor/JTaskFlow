package jtaskflow.core;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class JTFTask<V> extends JTFNodeInterface {    

    
    final private Callable<V> function;    

    public JTFTask(Callable<V> function) {
        super("Anonimous");
        this.predecessors = new ArrayList<>();
        this.successors = new ArrayList<>();
        this.function = function;
    }

    public JTFTask(String name, Callable<V> function) {
        super(name);
        this.predecessors = new ArrayList<>();
        this.successors = new ArrayList<>();
        this.function = function;
    }

    public Callable<V> getFunction() {
        return this.function;
    }

    @Override
    public String toString() {
        return "{" +
                " name='" + getName() + "'" +
                ", function='" + getFunction() + "'" +
                "}";
    }

    @Override
    public void runNode() throws Exception {
        if(function != null){
            function.call();
        }else{
            throw new RuntimeException("Function does not Exist");
        }
    }

}
