package backend.academy.benchmarks.reflections.invokers;

import backend.academy.benchmarks.records.Student;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MethodHandleInvoker {
    public static MethodHandle getName() throws NoSuchMethodException, IllegalAccessException {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType mt = MethodType.methodType(String.class);

        return lookup.findVirtual(Student.class, "name", mt);
    }
}
