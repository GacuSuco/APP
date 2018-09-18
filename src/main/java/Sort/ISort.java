package Sort;

public interface ISort{
    <T extends Comparable<? super T>> void Sort(T[] a);
}
