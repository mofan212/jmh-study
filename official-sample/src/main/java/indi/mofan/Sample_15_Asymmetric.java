package indi.mofan;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.GroupThreads;
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
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@code @Group}: 将相同指定名称的方法放到同一个 Benchmark 中执行，创造一些竞争关系
 * {@code @GroupThreads}: 指定参与特定组的 Benchmark 方法的线程数
 */
@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 1, time = 1)
@State(Scope.Group) // 指定类对象的作用域是组
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class Sample_15_Asymmetric {

    private AtomicInteger counter;

    @Setup
    public void up() {
        counter = new AtomicInteger();
    }

    @Benchmark
    @Group("g1")
    @GroupThreads(100)
    public int inc() {
        return counter.incrementAndGet();
    }

    @Benchmark
    @Group("g1")
    @GroupThreads(30)
    public int get() {
        return counter.get();
    }

    @Benchmark
    @Group("g2")
    public int inc1() {
        // 只有一个线程在自增，效率很高
        return counter.incrementAndGet();
    }

    @Benchmark
    @Group("g3")
    public int get1() {
        // 只有一个线程在 get，效率拉满
        return counter.get();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Sample_15_Asymmetric.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
