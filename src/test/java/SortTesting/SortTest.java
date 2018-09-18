package SortTesting;

import Sort.ISort;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class SortTest {
    private ISort sort;

    private Integer[] EqualKeysArray = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    private Integer[] EmptyArray = {};
    private Integer[] OrderdArray = {2, 4, 6, 8, 10, 12, 14};
    private Integer[] ReversedOrderdArray = {8, 7, 6, 5, 4, 3, 2, 1};
    private Integer[] UnorderdArray = {0, 1, 4, 9, 0, 3, 5, 2, 7, 0};

    protected abstract ISort getNewSort();

    @Before
    public void setUp() throws Exception {
        sort = getNewSort();
    }

    @Test
    public void SortEqualKeysArrayTest() {
        Integer[] tmp = EqualKeysArray;
        sort.Sort(tmp);
        Assert.assertArrayEquals(tmp, new Integer[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
    }

    @Test
    public void SortEmptyArrayTest() {
        Integer[] tmp = EmptyArray;
        sort.Sort(tmp);
        Assert.assertArrayEquals(tmp, new Integer[]{});
    }

    @Test
    public void SortOrderdArrayTest() {
        Integer[] tmp = OrderdArray;
        sort.Sort(tmp);
        Assert.assertArrayEquals(tmp, new Integer[]{2, 4, 6, 8, 10, 12, 14});
    }

    @Test
    public void SortReversedOrderdArrayTest() {
        Integer[] tmp = ReversedOrderdArray;
        sort.Sort(tmp);
        Assert.assertArrayEquals(tmp, new Integer[]{1, 2, 3, 4, 5, 6, 7, 8});
    }

    @Test
    public void SortUnorderdArrayTest() {
        Integer[] tmp = UnorderdArray;
        sort.Sort(tmp);
        Assert.assertArrayEquals(tmp, new Integer[]{0, 0, 0, 1, 2, 3, 4, 5, 7, 9});
    }
}
