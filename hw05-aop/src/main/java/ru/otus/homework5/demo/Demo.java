package ru.otus.homework5.demo;

import ru.otus.homework5.proxy.TestLoggingInterfaceProxy;
import ru.otus.homework5.test.TestLoggingInterface;

public class Demo {

    public void run() {
        TestLoggingInterface testLoggingInterface = TestLoggingInterfaceProxy.createTestLoggingClass();

        testLoggingInterface.calculate(6);
        testLoggingInterface.calculate(6, 9);
        testLoggingInterface.calculate(4, 8 ,10);
    }

}
