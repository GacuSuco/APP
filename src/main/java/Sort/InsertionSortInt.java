package Sort;

public class InsertionSortInt implements ISortInt {
    public void Sort(int[] a) {
        for (int p = 1; p < a.length; p++) {
            int tmp = a[p];
            int j = p;
            for (; j > 0 && tmp < a[j-1]; j--) {
                a[j] = a[j-1];
            }
            a[j] = tmp;
        }
    }
}
