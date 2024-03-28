package ru.otus.homework5.proxy;


import lombok.extern.slf4j.Slf4j;
import ru.otus.homework5.proxy.annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class LoggingProxy {

    private static final Set<Method> methodsWithLogAnnotations = new HashSet<>();

    public static <T> T createLoggingClass(T classToCreate) {
        InvocationHandler handler = new LoggingInvocationHandler<>(classToCreate);
        return (T) Proxy.newProxyInstance(LoggingProxy.class.getClassLoader(), classToCreate.getClass().getInterfaces(), handler);
    }

    private static class LoggingInvocationHandler<T> implements InvocationHandler {
        private final T realClass;

        LoggingInvocationHandler(T realClass) {
            this.realClass = realClass;

            Set<Method> methodsWithLogAnnotation = Arrays.stream(realClass.getClass().getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(Log.class))
                    .collect(Collectors.toSet());

            methodsWithLogAnnotations.addAll(methodsWithLogAnnotation);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            Optional<Method> calledMethodWithAnnotationMaybe = methodsWithLogAnnotations
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
