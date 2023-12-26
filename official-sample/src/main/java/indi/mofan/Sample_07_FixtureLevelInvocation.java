package indi.mofan;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 1, time = 1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
public class Sample_07_FixtureLevelInvocation {

    @State(Scope.Benchmark) // 全局公用这个实例
    public static class NormalState {
        ExecutorService service;

        @Setup(Level.Trial) // 全局只执行一次
        public void up() {
            service = Executors.newCachedThreadPool();
        }

        @TearDown(Level.Trial) // 全局只执行一次
        public void down() {
            service.shutdown();
        }

    }

    public static class LaggingState extends NormalState {
        public static final int SLEEP_TIME = Integer.getInteger("sleepTime", 10);

        // 模拟冷启动线程池
        @Setup(Level.Invocation) // 每次调用都会执行
        public void lag() throws InterruptedException {
            // 每次 Benchmark 方法被调用前，睡 10ms
            TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
        }
    }

    @Benchmark
    public double measureHot(NormalState e,
                             final Scratch s) throws ExecutionException, InterruptedException {
        // 在线程池中执行任务，阻塞地等待结果的响应
        return e.service.submit(new Task(s)).get();
    }

    @Benchmark
    public double measureCold(LaggingState e,
                              final Scratch s) throws ExecutionException, InterruptedException {
        return e.service.submit(new Task(s)).get();
    }


    @State(Scope.Thread) // 每个线程都会用各自的实例
    public static class Scratch {
        private double p;
        public double doWork() {
            p = Math.log(p);
            return p;
        }
    }

    public static class Task implements Callable<Double> {
        private Scratch s;

        public Task(Scratch s) {
            this.s = s;
        }

        @Override
        public Double call() {
            return s.doWork();
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Sample_07_FixtureLevelInvocation.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
