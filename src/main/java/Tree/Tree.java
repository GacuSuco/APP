package Tree;

public class Tree<T> {
    private T label;
    private Tree<T> parent;
    private Tree<T> nextSibling;
    private Tree<T> firstChild;

    public T getLabel() {
        return label;
    }

    public void setLabel(T v) {
        label = v;
    }

    public Tree<T> getParent() {
        return parent;
    }

    public Tree<T> getNextSibling() {
        return nextSibling;
    }

    public Tree<T> getFirstChild() {
        return firstChild;
    }

    public boolean Leaf() {
        return firstChild == null;
    }

    public void addChild(Tree<T> c) {
        c.parent = this;
        if (firstChild == null)
            firstChild = c;
        else {
            c.nextSibling = firstChild;
            firstChild = c;
        }
    }

    public void displayPreorder() {
        displayPreorder1(0);
    }

    public void displayPostorder() {
        displayPostorder1(0);
    }

    public void displayPreorder1(int Indent) {
        for (int i = 0; i < Indent; i++) {
            System.out.print(" ");
        }
        System.out.println(label.toString());
        Tree<T> N = firstChild;
        while (N != null) {
            N.displayPreorder1(Indent + 3);
            N = N.nextSibling;
        }
    }

    public void displayPostorder1(int Indent) {
        Tree<T> N = firstChild;
        while (N != null) {
            N.displayPostorder1(Indent + 3);
            N = N.nextSibling;
        }
        for (int I = 0; I < Indent; I++) {
            System.out.print(" ");
        }
        System.out.println(label.toString());
    }

    public String toString() {
        StringBuilder S = new StringBuilder("[ " + label.toString());
        Tree<T> N = firstChild;
        while (N != null) {
            S.append(" ").append(N.toString());
            N = N.nextSibling;
        }
        return S + " ]";
    }
}
