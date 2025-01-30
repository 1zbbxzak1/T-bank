package backend.academy.benchmarks.reflections.invokers;

import backend.academy.benchmarks.records.Student;
import java.lang.reflect.Method;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ReflectionMethodInvoker {
    public static Method getName() throws NoSuchMethodException {
        return Student.class.getDeclaredMethod("name");
    }
}
