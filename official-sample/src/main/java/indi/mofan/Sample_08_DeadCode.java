package indi.mofan;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * 这个例子展示了一种场景：有些代码会被 JVM 优化掉，导致基准测试结果不准确
 * ---
 * {@code baseline()}: 空方法
 * {@code measureWrong()}: 由于计算结果并没有返回，JVM 会自动优化，使其耗时测得与 {@code baseline()} 一样
 * {@code measureRight()}: 计算结果正常返回，JVM 不会自动优化，测出真正的执行效率
 */
@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 1, time = 1)
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class Sample_08_DeadCode {

    private double x = Math.PI;

    private double compute(double d) {
        for (int c = 0; c < 10; c++) {
            d = d * d / Math.PI;
        }
        return d;
    }

    @Benchmark
    public void baseline() {
        // do nothing, this is a baseline
    }

    @Benchmark
    public void measureWrong() {
        // 没有使用计算结果，JVM 将优化这段代码，相当于在测试空方法
        compute(x);
    }

    @Benchmark
    public double measureRight() {
        // 正确的做法，将结果返回，让 JVM 认为计算结果不能省略
        return compute(x);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Sample_08_DeadCode.class.getSimpleName())
                .forks(1)
                // JDK8 下尽量设置为 server 模式，充分利用 JIT
                .jvmArgs("-server")
                .build();

        new Runner(opt).run();
    }

}
