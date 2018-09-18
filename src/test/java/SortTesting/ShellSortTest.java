package SortTesting;

import Sort.ISort;
import Sort.ShellSort;

public class ShellSortTest extends SortTest {
    protected ISort getNewSort() {
        return new ShellSort();
    }
}