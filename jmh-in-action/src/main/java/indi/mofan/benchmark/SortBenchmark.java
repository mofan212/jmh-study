package indi.mofan.benchmark;

import indi.mofan.sort.BubbleSort;
import indi.mofan.sort.BucketSort;
import indi.mofan.sort.CountingSort;
import indi.mofan.sort.HeapSort;
import indi.mofan.sort.InsertSort;
import indi.mofan.sort.MergeSort;
import indi.mofan.sort.QuickSort;
import indi.mofan.sort.RadixSort;
import indi.mofan.sort.SelectionSort;
import indi.mofan.sort.ShellSort;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author mofan
 * @date 2023/12/26 22:44
 */
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 1, time = 1)
@Fork(1)
@State(Scope.Thread)
public class SortBenchmark {

    int[] arr;

    @Setup(Level.Invocation)
    public void prepareArray() {
        arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            RandomDataGenerator generator = new RandomDataGenerator();
            arr[i] = generator.nextInt(0, 100);
        }
    }

    @Benchmark
    public int[] bubbleSort() throws Exception {
        return BubbleSort.sort(arr);
    }

    @Benchmark
    public int[] bucketSort() throws Exception {
        return BucketSort.sort(arr);
    }

    @Benchmark
    public int[] countingSort() throws Exception {
        return CountingSort.sort(arr);
    }

    @Benchmark
    public int[] heapSort() throws Exception {
        return HeapSort.sort(arr);
    }

    @Benchmark
    public int[] insertSort() throws Exception { // n^2
        return InsertSort.sort(arr);
    }

    @Benchmark
    public int[] mergeSort() throws Exception { // nlog(n)
        return MergeSort.sort(arr);
    }

    @Benchmark
    public int[] quickSort() throws Exception {
        return QuickSort.sort(arr);
    }

    @Benchmark
    public int[] radixSort() throws Exception {
        return RadixSort.sort(arr);
    }

    @Benchmark
    public int[] selectionSort() throws Exception {
        return SelectionSort.sort(arr);
    }

    @Benchmark
    public int[] shellSort() {
        return ShellSort.sort(arr);
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(SortBenchmark.class.getSimpleName())
                .build();
        new Runner(options).run();
    }
}

// 数组长度 10，取值 0 ~ 100
// Benchmark                    Mode  Cnt     Score   Error  Units
// SortBenchmark.bubbleSort     avgt        148.637          ns/op
// SortBenchmark.bucketSort     avgt        614.930          ns/op
// SortBenchmark.countingSort   avgt        229.073          ns/op
// SortBenchmark.heapSort       avgt        175.842          ns/op
// SortBenchmark.insertSort     avgt        118.565          ns/op
// SortBenchmark.mergeSort      avgt        665.085          ns/op
// SortBenchmark.quickSort      avgt        187.735          ns/op
// SortBenchmark.radixSort      avgt       5351.363          ns/op
// SortBenchmark.selectionSort  avgt        122.999          ns/op
// SortBenchmark.shellSort      avgt        149.182          ns/op

// 数组长度 100，取值 0 ~ 100
// Benchmark                    Mode  Cnt      Score   Error  Units
// SortBenchmark.bubbleSort     avgt       10199.883          ns/op
// SortBenchmark.bucketSort     avgt        3765.742          ns/op
// SortBenchmark.countingSort   avgt        2896.813          ns/op
// SortBenchmark.heapSort       avgt        3061.878          ns/op
// SortBenchmark.insertSort     avgt        1335.316          ns/op
// SortBenchmark.mergeSort      avgt       12285.984          ns/op
// SortBenchmark.quickSort      avgt        2555.055          ns/op
// SortBenchmark.radixSort      avgt       23673.644          ns/op
// SortBenchmark.selectionSort  avgt        4337.397          ns/op
// SortBenchmark.shellSort      avgt        2487.184          ns/op

// 数组长度 10000，取值 0 ~ 100
// Benchmark                    Mode  Cnt         Score   Error  Units
// SortBenchmark.bubbleSort     avgt       61518470.000          ns/op
// SortBenchmark.bucketSort     avgt        1275125.000          ns/op
// SortBenchmark.countingSort   avgt          25470.588          ns/op
// SortBenchmark.heapSort       avgt         538462.500          ns/op
// SortBenchmark.insertSort     avgt        2728411.765          ns/op
// SortBenchmark.mergeSort      avgt       55002733.333          ns/op
// SortBenchmark.quickSort      avgt         362605.556          ns/op
// SortBenchmark.radixSort      avgt       43962510.000          ns/op
// SortBenchmark.selectionSort  avgt       14798706.667          ns/op
// SortBenchmark.shellSort      avgt         440547.059          ns/op