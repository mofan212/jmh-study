package indi.mofan;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@Warmup(iterations = 1, time = 1) // 默认单位就是秒，省略不写
@Measurement(iterations = 1, time = 1)
public class Sample_02_BenchmarkModes {

    @Benchmark
    @BenchmarkMode(Mode.Throughput) // 吞吐量测试，输出报告：每单位时间该方法会执行多少次
    @OutputTimeUnit(TimeUnit.DAYS) // 输出报告的时间单位
    public void measureThroughput() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime) // 平均耗时时间，输出报告：每次操作耗时，采用相同的单位时，与 Mode.Throughput 互为倒数
    @OutputTimeUnit(TimeUnit.SECONDS) // 耗时的单位
    public void measureAvgTime() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Benchmark
    // 可以使用抽样测试对算法的稳定性进行测试
    @BenchmarkMode(Mode.SampleTime) // 抽样测试，输出报告：在执行过程中采样，最快的、50% 快的、90%、95%、99%、99.9%、99.99%、100%
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void measureSamples() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime) // 冷启动测试，此方法在测试中只会运行一次，用于测试冷启动的性能
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void measureSingleShot() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
    }


    @Benchmark
    // 也可以四种都测试
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SampleTime, Mode.SingleShotTime})
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void measureMultiple() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Benchmark
    // 也可以使用 All 测试所有模式
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void measureAll() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Sample_02_BenchmarkModes.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
