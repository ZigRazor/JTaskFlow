package jtaskflow.core;

import java.util.ArrayList;
import java.util.List;

public class JTFSubFlow extends JTFNodeInterface{

    private List<JTFNodeInterface> nodes = null;

    public JTFSubFlow(String name) {
        super(name);
        this.predecessors = new ArrayList<>();
        this.successors = new ArrayList<>();
        this.nodes = new ArrayList<>();
    }

    @Override
    public void runNode() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'runNode'");
    }

    public List<JTFNodeInterface> getNodes() {
        return this.nodes;
    }

    public JTFSubFlow addNode(JTFNodeInterface node) {
        this.nodes.add(node);
        return this;
    }

    public JTFSubFlow addNodes(List<JTFNodeInterface> nodes) {
        this.nodes.addAll(nodes);
        return this;
    }
    
}
