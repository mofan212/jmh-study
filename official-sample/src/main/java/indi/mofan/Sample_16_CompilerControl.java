package indi.mofan;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * 使用 {@code @CompilerControl} 注解可以使 JMH 告知 JVM
 * 如何对代码进行优化（或者不优化？），测试代码在经过方法内联优化后
 * 的性能
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class Sample_16_CompilerControl {

    public void target_blank() {
    }

    /**
     * DONT_INLINE: 不进行方法内联优化
     */
    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    public void target_dontInline() {
    }

    /**
     * INLINE: 强制进行方法内联优化
     */
    @CompilerControl(CompilerControl.Mode.INLINE)
    public void target_inline() {
    }

    /**
     * EXCLUDE: 禁止编译，始终使用解释执行
     */
    @CompilerControl(CompilerControl.Mode.EXCLUDE)
    public void target_exclude() {
    }

    @Benchmark
    public void baseline() {
    }

    @Benchmark
    public void blank() {
        // 自动优化
        target_blank();
    }

    @Benchmark
    public void dontinline() {
        // 较慢
        target_dontInline();
    }

    @Benchmark
    public void inline() {
        // 最快
        target_inline();
    }

    @Benchmark
    public void exclude() {
        // 最慢
        target_exclude();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Sample_16_CompilerControl.class.getSimpleName())
                .warmupIterations(0)
                .measurementIterations(3)
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    private void a() {
        for (int i = 0; i < 10; i++) {
            // 调用私有方法，执行 invokespecial 指令
            // 方法入栈、分配栈上内存
            b();
        }
    }

    private void b() {
        int i = 0;
        // do something
        i++;
        System.out.println(i);
    }
}

// Benchmark                             Mode  Cnt  Score   Error  Units
// Sample_16_CompilerControl.baseline    avgt    3  0.196 ± 0.002  ns/op
// Sample_16_CompilerControl.blank       avgt    3  0.196 ± 0.006  ns/op
// Sample_16_CompilerControl.dontinline  avgt    3  0.811 ± 2.552  ns/op
// Sample_16_CompilerControl.exclude     avgt    3  8.508 ± 1.443  ns/op
// Sample_16_CompilerControl.inline      avgt    3  0.222 ± 0.792  ns/op