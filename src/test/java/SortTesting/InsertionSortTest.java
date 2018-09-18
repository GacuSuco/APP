package SortTesting;

import Sort.ISort;
import Sort.InsertionSort;

public class InsertionSortTest extends SortTest {
    protected ISort getNewSort() {
        return new InsertionSort();
    }
}
