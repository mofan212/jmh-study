package indi.mofan;

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

import static indi.mofan.util.MyUtil.printRunningJvmName;

/**
 * @author mofan
 * @date 2023/12/24 18:33
 */
@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 1, time = 1)
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class Sample_12_Forking_n {
    @Benchmark
    @Fork(2)
    public int measure_1_c1() {
        return 1;
    }

    @Setup(Level.Trial)
    public void setup() {
        printRunningJvmName("setup");
    }

    public static void main(String[] args) throws RunnerException {
        printRunningJvmName("main");
        Options opt = new OptionsBuilder()
                .include(Sample_12_Forking_n.class.getSimpleName())
                .build();
        /*
         * @Fork(10) 表示在执行 JMH 的整个测试时，fork 出 10 个进程来执行 Benchmark
         * fork 进程的操作不是并行的，也就是说，先 fork 一个进程，创建一个 JVM，执行
         * Benchmark，之后销毁 JVM 然后再 fork 一个进程，重复 10 次
         */
        new Runner(opt).run();
    }
}
