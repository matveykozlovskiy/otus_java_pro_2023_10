package ru.otus.homework3.util;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework3.util.annotation.After;
import ru.otus.homework3.util.annotation.Before;
import ru.otus.homework3.util.annotation.Test;
import ru.otus.homework3.util.exception.TestUtilException;

public class TestRunner {
    static Logger logger = LoggerFactory.getLogger(TestRunner.class.getName());

    public static void run(Class<?> clazz) {
        List<Method> beforeMethods = getBeforeMethods(clazz);
        List<Method> testMethods = getTestMethods(clazz);
        List<Method> afterMethods = getAfterMethods(clazz);

        int countOfSuccessfullyPassedTests = 0;
        int countOfFailedTests = 0;

        for (Method method : testMethods) {
            boolean isTestPassedSuccessfully = execute(clazz, method, beforeMethods, afterMethods);
            if (isTestPassedSuccessfully) {
                countOfSuccessfullyPassedTests++;
            } else countOfFailedTests++;
        }

        logger.info(
                "FINAL STATISTIC [Total tests ran: {} | successfully ran: {} | failed: {}]",
                testMethods.size(),
                countOfSuccessfullyPassedTests,
                countOfFailedTests);
    }

    private static <T> boolean execute(
            Class<T> clazz, Method testMethod, List<Method> beforeMethods, List<Method> afterMethods) {
        T instance = getNewInstance(clazz);

        try {
            beforeMethods.forEach((method) -> invokeMethod(method, instance));
            return invokeMethod(testMethod, instance);
        } finally {
            afterMethods.forEach((method -> invokeMethod(method, instance)));
        }
    }

    private static <T> T getNewInstance(Class<T> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new TestUtilException(
                    "It's impossible to create new instance of class " + clazz.getName() + " cause " + e.getMessage());
        }
    }

    private static <T> boolean invokeMethod(Method method, T instance) {
        try {
            method.setAccessible(true);
            method.invoke(instance);
            return true;
        } catch (Exception e) {
            logger.warn("Method {} failed: {}", method.getName(), e.getMessage());
            return false;
        }
    }

    private static List<Method> getBeforeMethods(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter((method) -> method.isAnnotationPresent(Before.class))
                .collect(Collectors.toList());
    }

    private static List<Method> getTestMethods(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter((method) -> method.isAnnotationPresent(Test.class))
                .collect(Collectors.toList());
    }

    private static List<Method> getAfterMethods(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter((method) -> method.isAnnotationPresent(After.class))
                .collect(Collectors.toList());
    }
}
