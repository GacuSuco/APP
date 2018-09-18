package Tree;

public class FCNSTree<T> {
    private FCNSNode<T> root;

    public FCNSTree(T root){
        this.root = new FCNSNode<T>(root);
    }

    public void setRoot(FCNSNode<T> root){
        this.root = root;
    }

    public int computeSize(){
        return root.determineSize();
    }

    public int computeHeight(){
        return root.determineHeight();
    }
}

