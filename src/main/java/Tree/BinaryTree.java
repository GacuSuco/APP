package Tree;

public class BinaryTree<T> {
    private BinaryNode<T> root;

    public BinaryTree() {
        root = null;
    }

    public BinaryTree(T rootItem) {
        root = new BinaryNode<T>(rootItem, null, null);
    }
    private int numberOfLeafs(BinaryNode<T> root) {
        if (root == null) {
            return 0;
        }else{
            return root.numberOfLeafs(root);
        }
    }
    private int numberOfChilds(BinaryNode<T> root) {
        if (root == null) {
            return 0;
        } else{
            return root.numberOfChilds(root);
        }
    }
    private int numberOfDoubleChilds(BinaryNode<T> root) {
        if (root == null) {
            return 0;
        } else {
            return root.numberOfDoubleChilds(root);
        }
    }

    public BinaryNode<T> getRoot() {
        return root;
    }

    public int size() {
        return BinaryNode.size(root);
    }

    public int height() {
        return BinaryNode.height(root);
    }

    public void makeEmpty() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void printPreOrder() {
        if (root != null) {
            root.printPreOrder();
        }
    }

    public void printPostOrder() {
        if (root != null) {
            root.printPostOrder();
        }
    }

    public void printInOrder() {
        if (root != null) {
            root.printInOrder();
        }
    }
}

