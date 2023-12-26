package indi.mofan.benchmark;

import indi.mofan.pojo.TestObj;
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
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author mofan
 * @date 2023/12/26 23:31
 */
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 2, time = 5)
@Measurement(iterations = 2, time = 5)
@State(Scope.Thread)
@Fork(1)
public class TestObjBenchmark {
    List<TestObj> list;
    Map<Integer, TestObj> map;

    @Setup(Level.Trial)
    public void prepareValue() {
        list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            TestObj obj = new TestObj();
            obj.setA(i);
            list.add(obj);
        }

        map = new HashMap<>();
        for (TestObj obj : list) {
            map.put(obj.getA(), obj);
        }
    }

    int a = -1;

    @Setup(Level.Invocation)
    public void prepareA() {
        a = new RandomDataGenerator().nextInt(1, 10);
    }

    @Benchmark
    public TestObj getByStream() {
        return list.stream()
                .filter(i -> i.getA().equals(a))
                .findFirst()
                .orElse(null);
    }

    @Benchmark
    public TestObj getByFor() {
        for (TestObj obj : list) {
            if (obj.getA().equals(a)) {
                return obj;
            }
        }
        return null;
    }

    @Benchmark
    public TestObj getByMap() {
        return map.get(a);
    }

    /**
     * 两个可视化网站：
     * <ul>
     *     <li><a href="https://jmh.morethan.io/">JMH Visualizer</a></li>
     *     <li><a href="https://deepoove.com/jmh-visual-chart/">JMH Visual Chart</a></li>
     * </ul>
     */
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(TestObjBenchmark.class.getSimpleName())
                // 报表可视化，输出 JSON 文件
                .resultFormat(ResultFormatType.JSON)
                // 输出到当前模块下的 target 目录下
                .result("./official-sample/target/result.json")
                .build();
        new Runner(options).run();
    }
}

// Benchmark                     Mode  Cnt   Score   Error  Units
// TestObjBenchmark.getByFor     avgt    2  37.708          ns/op
// TestObjBenchmark.getByMap     avgt    2  22.698          ns/op
// TestObjBenchmark.getByStream  avgt    2  59.334          ns/op