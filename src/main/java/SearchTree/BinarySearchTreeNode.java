package SearchTree;

public class BinarySearchTreeNode {
    private int value;
    public BinarySearchTreeNode leftChild = null;
    public BinarySearchTreeNode rightChild = null;

	public BinarySearchTreeNode(int value) {
            this.value = value;
        }

    public void addChild(int value) {
        if (this.value == value) {
            return;
        } else if (value < this.value) {
            if (leftChild == null) {
                leftChild = new BinarySearchTreeNode(value);
            } else {
                leftChild.addChild(value);
            }
        } else {
            if (rightChild == null) {
                rightChild = new BinarySearchTreeNode(value);
            } else {
                rightChild.addChild(value);
            }
        }
    }

    public BinarySearchTreeNode getMaxChild() {
        if (this.rightChild != null) {
            return this.rightChild.getMaxChild();
        }
        return this;
    }

    public BinarySearchTreeNode getMinChild() {
        if (this.leftChild != null) {
            return this.leftChild.getMinChild();
        }
        return this;
    }

    public BinarySearchTreeNode findChild(int value){
        if (this.value == value){
            return this;
        }
        else if (this.value < value && leftChild != null){
            return leftChild.findChild(value);
        }
        else if (this.value > value && leftChild != null){
            return rightChild.findChild(value);
        }
        return null;
    }

    public String getSubtreeString() {
        String returnString = "" + this.value;
        if (this.leftChild != null) {
            returnString += this.leftChild.getSubtreeString();
        }
        if (this.rightChild != null) {
            returnString += this.rightChild.getSubtreeString();
        }
        return returnString;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void PrintPreOrder(String depth){
        System.out.println(depth + value);
        if(leftChild != null) leftChild.PrintPreOrder(depth + " ");
        if(rightChild != null) rightChild.PrintPreOrder(depth + " ");
    }
    public void PrintPostOrder(String depth){
        if(leftChild != null) leftChild.PrintPostOrder(depth + " ");
        if(rightChild != null) rightChild.PrintPostOrder(depth + " ");
        System.out.println(depth + value);
    }
    public void PrintInnerOrder(String depth){
        if(leftChild != null) leftChild.PrintInnerOrder(depth + " ");
        System.out.println(depth + value);
        if(rightChild != null) rightChild.PrintInnerOrder(depth + " ");
    }
}
