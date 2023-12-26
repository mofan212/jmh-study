package indi.mofan.sort;

import java.util.Arrays;

/**
 * @author mofan
 * @date 2023/12/26 22:34
 */
public class BucketSort {

    private static final InsertSort insertSort = new InsertSort();

    public static int[] sort(int[] arr) throws Exception {
        return bucketSort(arr, 5);
    }

    private static int[] bucketSort(int[] arr, int bucketSize) throws Exception {
        if (arr.length == 0) {
            return arr;
        }

        int minValue = arr[0];
        int maxValue = arr[0];
        for (int value : arr) {
            if (value < minValue) {
                minValue = value;
            } else if (value > maxValue) {
                maxValue = value;
            }
        }

        int bucketCount = (int) Math.floor((double) (maxValue - minValue) / bucketSize) + 1;
        int[][] buckets = new int[bucketCount][0];

        // 利用映射函数将数据分配到各个桶中
        for (int j : arr) {
            int index = (int) Math.floor((double) (j - minValue) / bucketSize);
            buckets[index] = arrAppend(buckets[index], j);
        }

        int arrIndex = 0;
        for (int[] bucket : buckets) {
            if (bucket.length == 0) {
                continue;
            }
            // 对每个桶进行排序，这里使用了插入排序
            bucket = insertSort.sort(bucket);
            for (int value : bucket) {
                arr[arrIndex++] = value;
            }
        }

        return arr;
    }

    /**
     * 自动扩容，并保存数据
     */
    private static int[] arrAppend(int[] arr, int value) {
        arr = Arrays.copyOf(arr, arr.length + 1);
        arr[arr.length - 1] = value;
        return arr;
    }
}
