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
 * @date 2023/12/24 18:42
 */
@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 1, time = 1)
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class Sample_12_Forking_Default {
    @Benchmark
    @Fork // 不传具体的值，即为 -1，相当于显式传入 5
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
                .include(Sample_12_Forking_Default.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}
