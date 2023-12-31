package indi.mofan;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class Sample_17_SyncIterations {

    private double src;

    /**
     * 为演示耗时而存在，没有特殊意义
     */
    @Benchmark
    public double test() {
        double s = src;
        for (int i = 0; i < 1000; i++) {
            s = Math.sin(s);
        }
        return s;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Sample_17_SyncIterations.class.getSimpleName())
                .warmupTime(TimeValue.seconds(1))
                .warmupIterations(1)
                .measurementTime(TimeValue.seconds(1))
                // 设置线程数为 CPU 核心数 * 16
                // I9-13980HX 32 核心 -> 32 * 16 = 512 线程
                .threads(Runtime.getRuntime().availableProcessors() * 16)
                .forks(1)
                /*
                 * 默认值为 true。
                 * true: 线程同步，所有先线程准备好之后再执行
                 * false: 线程逐步执行
                 */
                .syncIterations(true)
                .build();

        new Runner(opt).run();
    }

}

// true
// Benchmark                       Mode  Cnt     Score      Error   Units
// Sample_17_SyncIterations.test  thrpt    5  8628.210 ± 2454.625  ops/ms

// false
// Benchmark                       Mode  Cnt      Score       Error   Units
// Sample_17_SyncIterations.test  thrpt    5  47739.259 ± 98840.596  ops/ms