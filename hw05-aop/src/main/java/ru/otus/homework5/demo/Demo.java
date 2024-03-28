package ru.otus.homework5.demo;

import ru.otus.homework5.proxy.LoggingProxy;
import ru.otus.homework5.test.TestLogging;
import ru.otus.homework5.test.TestLoggingInterface;

public class Demo {

    public void run() {
        TestLoggingInterface testLoggingInterface = LoggingProxy.createLoggingClass(new TestLogging());

        testLoggingInterface.calculate(6);
        testLoggingInterface.calculate(6, 9);
        testLoggingInterface.calculate(4, 8 ,10);
    }

}
