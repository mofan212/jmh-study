package indi.mofan;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 1, time = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class Sample_09_Blackholes {

    double x1 = Math.PI;
    double x2 = Math.PI * 2;

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
        return compute(x1);
    }

    /**
     * 错误示例
     */
    @Benchmark
    public double measureWrong() {
        // 编译器会自动识别，在 JIT 的时候直接不执行，使得 JMH 的测试结果不准确
        compute(x1);
        // 真正有用的计算
        return compute(x2);
    }

    /**
     * 正确示例 1
     */
    @Benchmark
    public double measureRight_1() {
        // 所有的计算结果都使用了
        return compute(x1) + compute(x2);
    }

    /**
     * 正确示例 2
     */
    @Benchmark
    public void measureRight_2(Blackhole bh) {
        // 为了防止编译器“自作主张”，使用 JMH 提供的 Blackhole 对象对执行结果进行消费
        bh.consume(compute(x1));
        bh.consume(compute(x2));
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Sample_09_Blackholes.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
