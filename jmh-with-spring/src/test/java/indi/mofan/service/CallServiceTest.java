package indi.mofan.service;

import indi.mofan.holder.BeanHolder;
import indi.mofan.indi.mofan.service.CallService;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

/**
 * @author mofan
 * @date 2023/12/31 17:47
 */
@SpringBootTest
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class CallServiceTest {

    @Autowired
    private void setCallService(CallService callService) {
        BeanHolder.setBean(callService);
    }

    private CallService callService;

    @Setup
    public void setup() {
        callService = BeanHolder.getBean();
    }

    @Benchmark
    public void call() {
        callService.call();
    }

    @Test
    public void executeBenchmark() throws RunnerException {
        Options options = new OptionsBuilder()
                .include("\\." + this.getClass().getSimpleName() + "\\.")
                /*
                 * forks 一定要设置成 0
                 * 让 Benchmark 和启动方法在同一个 JVM 中执行
                 */
                .forks(0)
                .warmupIterations(1)
                .warmupTime(TimeValue.seconds(1))
                .measurementIterations(1)
                .measurementTime(TimeValue.seconds(1))
                .build();
        new Runner(options).run();
    }
}