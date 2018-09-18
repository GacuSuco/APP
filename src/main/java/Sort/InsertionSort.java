package Sort;

public class InsertionSort implements ISort{
    public <T extends Comparable<? super T>> void Sort(T[] a) {
        for (int p = 1; p < a.length; p++) {
            T tmp = a[p];
            int j = p;
            for (; j > 0 && tmp.compareTo(a[j-1]) < 0; j--) {
                a[j] = a[j-1];
            }
            a[j] = tmp;
        }
    }
}
