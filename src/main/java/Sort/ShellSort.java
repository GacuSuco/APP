package Sort;

public class ShellSort implements ISort{
    public <T extends Comparable<? super T>> void Sort(T[] a) {
        for (int gap = a.length / 2; gap > 0; gap = gap == 2 ? 1 : (int)(gap / 2.2))
            for (int i = gap; i < a.length; i++) {
                T tmp = a[i];
                int j = i;

                for (; j >= gap && tmp.compareTo(a[j - gap]) < 0; j -= gap)
                    a[j] = a[j - gap];
                a[j] = tmp;
            }
    }
}
