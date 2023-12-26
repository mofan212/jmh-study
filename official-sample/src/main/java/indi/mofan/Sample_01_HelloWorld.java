package indi.mofan;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class Sample_01_HelloWorld {

    @Benchmark
    public void wellHelloThere() {

    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Sample_01_HelloWorld.class.getSimpleName())
                .forks(1) // 设置总共测几轮
                .build();

        new Runner(opt).run();
    }

}
