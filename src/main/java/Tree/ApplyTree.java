package Tree;

public interface ApplyTree<T,U> {
    U apply(Tree<T> tree);
    U apply(TreeNode<T> treeNode);
}
