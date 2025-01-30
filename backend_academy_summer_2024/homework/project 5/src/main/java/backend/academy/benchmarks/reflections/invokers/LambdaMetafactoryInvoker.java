package backend.academy.benchmarks.reflections.invokers;

import backend.academy.benchmarks.records.Student;
import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.function.Function;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LambdaMetafactoryInvoker {
    public static Function<Student, String> getName() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType mtFunc = MethodType.methodType(Function.class);
        MethodType methodType = MethodType.methodType(String.class, Student.class);
        MethodHandle nameHandle = lookup.findVirtual(Student.class, "name", MethodType.methodType(String.class));

        CallSite site = LambdaMetafactory.metafactory(
            lookup,
            "apply",
            mtFunc,
            methodType.erase(),
            nameHandle,
            methodType
        );

        return (Function<Student, String>) site.getTarget().invokeExact();
    }
}
