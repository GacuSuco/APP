package Sort;

public class QuickSortInt implements ISortInt {
    private static final int CUTOFF = 10;

    public void Sort(int[] a) {
        quicksort(a, 0, a.length - 1);
    }

    private static void insertionSort(int[] a, int low, int high) {
        for (int p = low + 1; p <= high; p++) {
            int tmp = a[p];
            int j;

            for (j = p; j > low && tmp < a[j - 1]; j--)
                a[j] = a[j - 1];
            a[j] = tmp;
        }
    }
    private static void swapReferences(int[] a, int index1, int index2) {
        int tmp = a[index1];
        a[index1] = a[index2];
        a[index2] = tmp;
    }
    private static void quicksort(int[] a, int low, int high) {
        if (low + CUTOFF > high)
            insertionSort(a, low, high);
        else {
            // Sort low, middle, high
            int middle = (low + high) / 2;
            if (a[middle] < a[low])
                swapReferences(a, low, middle);
            if (a[high] < a[low])
                swapReferences(a, low, high);
            if (a[high] < a[middle])
                swapReferences(a, middle, high);

            // Place pivot at position high - 1
            swapReferences(a, middle, high - 1);
            int pivot = a[high - 1];

            // Begin partitioning
            int i, j;
            for (i = low, j = high - 1; ; ) {
                while (a[++i] < pivot)
                    ;
                while (pivot < a[--j])
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
