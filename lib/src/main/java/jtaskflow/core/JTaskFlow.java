package jtaskflow.core;

import java.util.ArrayList;
import java.util.List;

public class JTaskFlow {

    private List<JTFNodeInterface> nodes = null;

    public JTaskFlow() {
        nodes = new ArrayList<>();
    }

    public List<JTFNodeInterface> getNodes() {
        return this.nodes;
    }

    public JTaskFlow addNode(JTFNodeInterface node) {
        this.nodes.add(node);
        return this;
    }

    public JTaskFlow addNodes(List<JTFNodeInterface> nodes) {
        this.nodes.addAll(nodes);
        return this;
    }

}
