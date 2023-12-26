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
 * @date 2023/12/24 18:20
 */
@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 1, time = 1)
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class Sample_12_Forking_1 {
    @Benchmark
    @Fork(1)
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
                .include(Sample_12_Forking_1.class.getSimpleName())
                .build();
        /*
         * 修改为 @Fork(1) 之后，执行 main 方法与执行 Benchmark 不在同一个 JVM 中
         * 也就是在执行 JMH 的整个测试时，会 fork 出一个进程，创建出一个崭新的 JVM，
         * 再来执行 Benchmark
         */
        new Runner(opt).run();
    }
}
