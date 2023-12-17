package indi.mofan;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author mofan
 * @date 2023/12/17 17:22
 */
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS) // 预热一轮，每次一秒
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS) // 测试一轮，每次一秒
public class Sample_01_2_Warmup {
    @Benchmark
    public void wellHelloThere() {

    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Sample_01_2_Warmup.class.getSimpleName())
                .forks(1) // 设置总共测几轮
                .build();

        new Runner(opt).run();
    }
}
