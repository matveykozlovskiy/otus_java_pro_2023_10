package ru.otus.homework5.proxy;


import lombok.extern.slf4j.Slf4j;
import ru.otus.homework5.proxy.annotation.Log;
import ru.otus.homework5.test.TestLogging;
import ru.otus.homework5.test.TestLoggingInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class TestLoggingInterfaceProxy {

    private static final Map<Class<?>, Set<Method>> methodsWithLogAnnotationByClass = new HashMap<>();

    public static TestLoggingInterface createTestLoggingClass() {
        InvocationHandler handler = new TestLoggingInvocationHandler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(TestLoggingInterfaceProxy.class.getClassLoader(), new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    private static class TestLoggingInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface realClass;

        TestLoggingInvocationHandler(TestLoggingInterface testLogging) {
            this.realClass = testLogging;

            if (!methodsWithLogAnnotationByClass.containsKey(testLogging.getClass())) {
                Set<Method> methodsWithLogAnnotation = Arrays.stream(realClass.getClass().getDeclaredMethods())
                        .filter(method -> method.isAnnotationPresent(Log.class))
                        .collect(Collectors.toSet());

                methodsWithLogAnnotationByClass.put(realClass.getClass(), methodsWithLogAnnotation);
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            Optional<Method> calledMethodWithAnnotationMaybe = methodsWithLogAnnotationByClass.get(realClass.getClass())
                    .stream()
                    .filter(m -> isMethodEquals(m, method))
                    .findFirst();


            calledMethodWithAnnotationMaybe.ifPresent(value -> log.info("executed method: {}, param: {}", value.getName(), args));
            return method.invoke(realClass, args);
        }
    }

    private static boolean isMethodEquals(Method firstMethod, Method secondMethod) {
        if (firstMethod.getParameterCount() != firstMethod.getParameterCount()) {
            return false;
        }

        List<Type> firstMethodTypes = Arrays.stream(firstMethod.getParameters())
                .map(Parameter::getParameterizedType)
                .toList();

        List<Type> secondMethodTypes = Arrays.stream(secondMethod.getParameters())
                .map(Parameter::getParameterizedType)
                .toList();

        return firstMethodTypes.equals(secondMethodTypes);
    }

}
