package indi.mofan;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * 控制 State 对象中的方法在什么时候被执行
 * {@code @Setup} state 中的方法如何被执行
 * {@code @TearDown} state 中的方法如何被执行
 * Level.Trial 默认的执行策略，整个基准测试只执行一次
 */
@State(Scope.Thread)
@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 1, time = 1)
public class Sample_05_StateFixtures {

    double x;

    /**
     * 启动 Benchmark 前的准备工作。
     * {@code @Setup} 注解必须在 {@code @State} 标记的类下才能使用，
     * 实际上也算是 {@code @State} 管理的对象的生命周期的一部分
     */
    @Setup
    public void prepare() {
        x = Math.PI;
    }

    /**
     * Benchmark 结束后的检查工作。
     * 与 {@code @Setup} 注解类似，也必须在 {@code @State} 标记的类下使用。
     */
    @TearDown
    public void check() {
        assert x > Math.PI : "Nothing changed?";
    }

    @Benchmark
    public void measureRight() {
        x++;
    }

    @Benchmark
    public void measureWrong() {
        double x = 0;
        x++;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Sample_05_StateFixtures.class.getSimpleName())
                .forks(1)
                .jvmArgs("-ea")
                .build();

        new Runner(opt).run();
    }

}
