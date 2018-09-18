package SortTesting;

import Sort.ISort;
import Sort.MergeSort;

public class MergeSortTest extends SortTest {
    protected ISort getNewSort() {
        return new MergeSort();
    }
}