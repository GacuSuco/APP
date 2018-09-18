package SearchTree;

import Sort.ISortInt;
import Sort.QuickSortInt;

public class BinarySearchTree {
    private BinarySearchTreeNode rootNode;

    public BinarySearchTree(){
        this.rootNode = null;
    }

    public BinarySearchTree(int rootValue){
        rootNode = new BinarySearchTreeNode(rootValue);
    }

    public void addChild(int value) {
        if(rootNode == null) {
            rootNode = new BinarySearchTreeNode(value);
        } else {
            rootNode.addChild(value);
        }
    }

    public void removeChild(int value) {
        BinarySearchTreeNode removeNode = this.find(value);
        BinarySearchTreeNode lowestnode = removeNode.rightChild.getMinChild();

        removeNode.setValue(lowestnode.getValue());
        if(removeNode == removeNode.rightChild){
            removeNode.rightChild = removeNode.rightChild.rightChild;
        }
    }

    public BinarySearchTreeNode findMin() {
        if(rootNode != null){
            return rootNode.getMinChild();
        }
        return null;
    }

    public BinarySearchTreeNode findMax() {
        if(rootNode != null) {
            return rootNode.getMaxChild();
        }
        return null;
    }

    public BinarySearchTreeNode find(int value){
        if (rootNode != null){
            return rootNode.findChild(value);
        }
        return null;
    }

    public void fromArray(int[] values){
        ISortInt quickSorter = new QuickSortInt();
        quickSorter.Sort(values);
        balanceAndInsertArray(values,0,values.length-1);
    }

    public void fromOrderedArray(int[] values){
        balanceAndInsertArray(values,0,values.length-1);
    }

    private void balanceAndInsertArray(int[] values, int start, int end){
        if(start == end){
            addChild(values[start]);
            return;
        }
        int median =(end+start)/2;
        addChild(values[median]);
        balanceAndInsertArray(values,start,median-1);
        balanceAndInsertArray(values,median+1,end);
    }

    public int getValueFromNode(BinarySearchTreeNode node) {
        return node.getValue();
    }

    public String toString() {
        return rootNode.getSubtreeString();
    }

    public void PrintPreOrder(String depth){
        if(rootNode != null){
            rootNode.PrintPreOrder(depth + " ");
        }
    }
    public void PrintPostOrder(String depth){
        if(rootNode != null){
            rootNode.PrintPostOrder(depth + " ");
        }
    }
    public void PrintInnerOrder(String depth){
        if(rootNode != null){
            rootNode.PrintInnerOrder(depth + " ");
        }
    }
}
