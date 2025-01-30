package backend.academy.benchmarks.reflections;

import backend.academy.benchmarks.records.Student;
import backend.academy.benchmarks.reflections.invokers.LambdaMetafactoryInvoker;
import backend.academy.benchmarks.reflections.invokers.MethodHandleInvoker;
import backend.academy.benchmarks.reflections.invokers.ReflectionMethodInvoker;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Thread)
public class ReflectionBenchmark {
    private Student student;
    private Method reflectionMethod;
    private MethodHandle reflectionMethodHandle;
    private Function<Student, String> lambdaMethod;

    @Setup
    public void setup() throws Throwable {
        student = new Student("Julia", "Polyakova");

        reflectionMethod = ReflectionMethodInvoker.getName();

        reflectionMethodHandle = MethodHandleInvoker.getName();

        lambdaMethod = LambdaMetafactoryInvoker.getName();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @Fork(value = 2, warmups = 1)
    @Threads(2)
    public void directAccess(Blackhole bh) {
        String name = student.name();
        bh.consume(name);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @Fork(value = 2, warmups = 1)
    @Threads(2)
    public void setReflectionMethod(Blackhole bh) throws InvocationTargetException, IllegalAccessException {
        String name = (String) reflectionMethod.invoke(student);
        bh.consume(name);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @Fork(value = 2, warmups = 1)
    @Threads(2)
    public void setReflectionMethodHandle(Blackhole bh) throws Throwable {
        String name = (String) reflectionMethodHandle.invokeExact(student);
        bh.consume(name);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @Fork(value = 2, warmups = 1)
    @Threads(2)
    public void setLambdaMethod(Blackhole bh) {
        String name = lambdaMethod.apply(student);
        bh.consume(name);
    }
}
