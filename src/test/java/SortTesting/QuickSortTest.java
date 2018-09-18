package SortTesting;

import Sort.ISort;
import Sort.QuickSort;

public class QuickSortTest extends SortTest {
    protected ISort getNewSort() {
        return new QuickSort();
    }
}