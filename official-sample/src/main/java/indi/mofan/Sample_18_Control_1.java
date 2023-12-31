package indi.mofan;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author mofan
 * @date 2023/12/31 15:22
 */
@State(Scope.Group)
public class Sample_18_Control_1 {

    public final AtomicBoolean flag = new AtomicBoolean(true);

    @Benchmark
    @Group("pingpong")
    public void pingWithoutControl() {
        while (true) {
            // flag 期望是 false 时，才设置为 true，否则不设置
            boolean setSuccess = flag.compareAndSet(true, false);
            if (setSuccess) {
                // 设置成功才结束循环，否则一直循环
                return;
            }
        }
    }

    @Benchmark
    @Group("pingpong")
    public void pongWithoutControl() {
        while (true) {
            boolean setSuccess = flag.compareAndSet(false, true);
            if (setSuccess) {
                // 设置成功才结束循环，否则一直循环
                return;
            }
        }
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Sample_18_Control_1.class.getSimpleName())
                // 多运行几次，只跑一次可能不会导致死循环
                .warmupTime(TimeValue.seconds(1))
                .measurementTime(TimeValue.seconds(1))
                .warmupIterations(1)
                .measurementIterations(1)
                .threads(2)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
