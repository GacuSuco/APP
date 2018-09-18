package Tree;

public class BinaryNode<T> {
    private T value;
    private BinaryNode<T> left;
    private BinaryNode<T> right;

    public int numberOfLeafs(BinaryNode<T> node){
        if(node.getLeft() == null && node.getRight() == null){
            return 1;
        }
        if(node.getLeft() != null && node.getRight() == null) {
            return numberOfLeafs(node.getLeft());
        }
        if (node.getLeft() == null && node.getRight() != null){
            return numberOfLeafs(node.getRight());
        }
        return numberOfLeafs(node.getLeft()) + numberOfLeafs(node.getRight());
    }
    public int numberOfChilds(BinaryNode<T> node) {
        if(node.getLeft() == null && node.getRight() == null){
            return 0;
        }
        if(node.getLeft() != null && node.getRight() == null) {
            return 1 + numberOfChilds(node.getLeft());
        }
        if (node.getLeft() == null && node.getRight() != null){
            return 1 + numberOfChilds(node.getRight());
        }
        return numberOfChilds(node.getLeft()) + numberOfChilds(node.getRight());
    }
    public int numberOfDoubleChilds(BinaryNode<T> node) {
        if(node.getLeft() == null && node.getRight() == null){
            return 0;
        }
        if(node.getLeft() != null && node.getRight() == null) {
            return numberOfDoubleChilds(node.getLeft());
        }
        if (node.getLeft() == null && node.getRight() != null){
            return numberOfDoubleChilds(node.getRight());
        }
        return 1 + numberOfDoubleChilds(node.getLeft()) + numberOfDoubleChilds(node.getRight());
    }

//    public static int Calc(BinaryNode<String> node){
//        if (node.value.equals("/")){
//            return calc(node.left) / calc(node.right);
//        }
//        if (node.value.equals("*")){
//            return calc(node.left) * calc(node.right);
//        }
//        if (node.value.equals("+")){
//            return calc(node.left) + calc(node.right);
//        }
//        if (node.value.equals("-")){
//            return calc(node.left) - calc(node.right);
//        }
//        return new Integer(node.value);
//    }

    public BinaryNode() {
        value = null;
        left = null;
        right = null;
    }
    public BinaryNode(T value, BinaryNode<T> left, BinaryNode<T> right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public static <T> int size(BinaryNode<T> t) {
        if (t == null) {
            return 0;
        } else {
            return 1 + size(t.left) + size(t.right);
        }
    }
    public static <T> int height(BinaryNode<T> t) {
        if (t == null) {
            return -1;
        } else {
            return 1 + Math.max(height(t.left), height(t.right));
        }
    }

    public void printPreOrder() {
        System.out.println(value);
        if (left != null) {
            left.printPreOrder();
        }
        if (right != null) {
            right.printPreOrder();
        }
    }
    public void printPostOrder() {
        if (left != null) {
            left.printPreOrder();
        }
        if (right != null) {
            right.printPreOrder();
        }
        System.out.println(value);
    }
    public void printInOrder() {
        if (left != null) {
            left.printPreOrder();
        }
        System.out.println(value);
        if (right != null) {
            right.printPreOrder();
        }
    }

    public BinaryNode<T> duplicate() {
        BinaryNode<T> root = new BinaryNode<T>(value, null, null);
        if (left != null) {
            root.left = left.duplicate();
        }
        if (right != null) {
            root.right = right.duplicate();
        }
        return root;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public BinaryNode<T> getLeft() {
        return left;
    }

    public void setLeft(BinaryNode<T> left) {
        this.left = left;
    }

    public BinaryNode<T> getRight() {
        return right;
    }

    public void setRight(BinaryNode<T> right) {
        this.right = right;
    }

    public String toString() {
        return (right == null && left == null) ? value.toString() : "(" + left.toString() + value + right.toString() + ")";
    }


}
