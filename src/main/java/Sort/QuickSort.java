package Sort;

public class QuickSort implements ISort {
    private static final int CUTOFF = 10;

    public <T extends Comparable<? super T>> void Sort(T[] a) {
        quicksort(a, 0, a.length - 1);
    }
    private static <T extends Comparable<? super T>> void insertionSort(T[] a, int low, int high) {
        for (int p = low + 1; p <= high; p++) {
            T tmp = a[p];
            int j;

            for (j = p; j > low && tmp.compareTo(a[j - 1]) < 0; j--)
                a[j] = a[j - 1];
            a[j] = tmp;
        }
    }
    private static <T> void swapReferences(T[] a, int index1, int index2) {
        T tmp = a[index1];
        a[index1] = a[index2];
        a[index2] = tmp;
    }
    private static <T extends Comparable<? super T>> void quicksort(T[] a, int low, int high) {
        if (low + CUTOFF > high)
            insertionSort(a, low, high);
        else {
            // Sort low, middle, high
            int middle = (low + high) / 2;
            if (a[middle].compareTo(a[low]) < 0)
                swapReferences(a, low, middle);
            if (a[high].compareTo(a[low]) < 0)
                swapReferences(a, low, high);
            if (a[high].compareTo(a[middle]) < 0)
                swapReferences(a, middle, high);

            // Place pivot at position high - 1
            swapReferences(a, middle, high - 1);
            T pivot = a[high - 1];

            // Begin partitioning
            int i, j;
            for (i = low, j = high - 1; ; ) {
                while (a[++i].compareTo(pivot) < 0)
                    ;
                while (pivot.compareTo(a[--j]) < 0)
                    ;
                if (i >= j)
                    break;
                swapReferences(a, i, j);
            }

            // Restore pivot
            swapReferences(a, i, high - 1);

            quicksort(a, low, i - 1);    // Sort small elements
            quicksort(a, i + 1, high);   // Sort large elements
        }
    }


}
