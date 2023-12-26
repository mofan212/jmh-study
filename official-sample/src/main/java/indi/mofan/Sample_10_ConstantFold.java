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

@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 1, time = 1)
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class Sample_10_ConstantFold {

    private double x = Math.PI;

    private final double wrongX = Math.PI;

    private double compute(double d) {
        for (int c = 0; c < 10; c++) {
            d = d * d / Math.PI;
        }
        return d;
    }

    /**
     * 基准
     */
    @Benchmark
    public double baseline() {
        return Math.PI;
    }

    /**
     * 错误示例：结果是可预测的，因此计算过程会被省略，直接返回计算结果
     */
    @Benchmark
    public double measureWrong_1() {
        return compute(Math.PI);
    }

    /**
     * 错误示例：传入的 wrongX 被 final 修饰，错误原因与 {@code measureWrong_1()} 一样
     */
    @Benchmark
    public double measureWrong_2() {
        return compute(wrongX);
    }

    /**
     * 正确示例：传入的 x 未被 final 修饰，计算结果不可预测
     */
    @Benchmark
    public double measureRight() {
        return compute(x);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Sample_10_ConstantFold.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
