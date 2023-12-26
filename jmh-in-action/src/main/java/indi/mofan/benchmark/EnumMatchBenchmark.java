package indi.mofan.benchmark;

import indi.mofan.enums.EnumMatch;
import org.apache.commons.math3.random.RandomDataGenerator;
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

/**
 * @author mofan
 * @date 2023/12/26 23:19
 */
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 1, time = 2)
@Measurement(iterations = 1, time = 2)
@State(Scope.Thread)
@Fork(1)
public class EnumMatchBenchmark {
    int value = -1;

    @Setup(Level.Invocation)
    public void prepareValue() {
        // 修改元素个数时，修改随机数的范围
        value = new RandomDataGenerator().nextInt(1, 10);
    }

    @Benchmark
    public EnumMatch matchByFor() {
        return EnumMatch.matchWithFor(value);
    }

    @Benchmark
    public EnumMatch matchByMap() {
        return EnumMatch.matchWithMap(value);
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(EnumMatchBenchmark.class.getSimpleName())
                .build();
        new Runner(options).run();
    }
}

// 枚举中有十个元素
// Benchmark                      Mode  Cnt   Score   Error  Units
// EnumMatchBenchmark.matchByFor  avgt       27.367          ns/op
// EnumMatchBenchmark.matchByMap  avgt       19.456          ns/op

// 如果枚举中只有三个元素
// Benchmark                      Mode  Cnt   Score   Error  Units
// EnumMatchBenchmark.matchByFor  avgt       21.513          ns/op
// EnumMatchBenchmark.matchByMap  avgt       19.134          ns/op
