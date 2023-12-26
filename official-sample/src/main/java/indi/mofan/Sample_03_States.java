package indi.mofan;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * {@code @State(Scope.xxx)} 描述了类对象的作用域
 */
@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 1, time = 1)
public class Sample_03_States {

    /**
     * 该静态内部类会在 Benchmark 启动时初始化，可以用作方法的入参
     * 所有测试线程共享一个实例，用于测试有状态实例在多线程共享下的性能
     * 一般用来测试多线程竞争下的性能
     */
    @State(Scope.Benchmark)
    public static class BenchmarkState {
        volatile double x = Math.PI;
    }

    /**
     * 该静态内部类会在 Benchmark 各个线程执行之前初始化，可以用作方法的入参
     * 所有测试线程各用各的
     */
    @State(Scope.Thread) // 不同线程，不同对象
    public static class ThreadState {
        volatile double x = Math.PI;
    }

    /**
     * 根据 main 方法的配置，会启动 4 个线程去一起执行
     * 每个线程的入参都是不同的
     */
    @Benchmark
    public void measureUnshared(ThreadState state) {
        state.x++;
    }

    /**
     * 启动 4 个线程，但入参都是一个实例，竞争会非常激烈
     */
    @Benchmark
    public void measureShared(BenchmarkState state) {
        state.x++;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Sample_03_States.class.getSimpleName())
                // 执行每个 Benchmark 时，创建 4 个线程去执行
                .threads(4)
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
