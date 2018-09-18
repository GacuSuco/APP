package Tree;

public class FCNSNode<T> {
    public T val;

    public FCNSNode<T> firstChild;
    public FCNSNode<T> nextSibling;
    public int height, size;

    public FCNSNode(T val) {
        this.val = val;
        height = 0;
        size = 0;
    }


    public void addChild(FCNSNode<T> child) {
        if (firstChild == null) firstChild = child;
        else firstChild.addSibling(child);
    }

    private void addSibling(FCNSNode<T> sibling) {
        if (nextSibling == null) nextSibling = sibling;
        else nextSibling.addSibling(sibling);
    }

    public void addChild(T child) {
        if (firstChild == null) firstChild = new FCNSNode<T>(child);
        else firstChild.addSibling(child);
    }

    private void addSibling(T sibling) {
        if (nextSibling == null) nextSibling = new FCNSNode<T>(sibling);
        else nextSibling.addSibling(sibling);
    }

    public int determineSize() {
        size = 1;
        if (firstChild != null) size += firstChild.determineSize();

        int returnSize = size;

        if (nextSibling != null) {
            FCNSNode sibling = nextSibling;
            while (sibling != null) {
                returnSize += sibling.determineSize();
                sibling = sibling.nextSibling;
            }
        }

        return returnSize;
    }

    public int determineHeight() {
        if (firstChild != null) {
            height = 1 + firstChild.determineHeight();
        }

        int returnHeight = height;
        if (nextSibling != null) {
            FCNSNode sibling = nextSibling;
            while (sibling != null) {
                returnHeight = Math.max(Math.max(returnHeight, height), sibling.determineHeight());
                sibling = sibling.nextSibling;
            }
        }
        return returnHeight;
    }
}
