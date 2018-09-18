package Tree;

public interface ApplyBinaryTree<T, U> {
    U apply(BinaryTree<U> binaryTree);
    U apply(BinaryNode<U> binaryNode);
}
