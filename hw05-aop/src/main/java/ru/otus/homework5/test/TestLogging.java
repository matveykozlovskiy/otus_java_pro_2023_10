package ru.otus.homework5.test;

import lombok.extern.slf4j.Slf4j;
import ru.otus.homework5.proxy.annotation.Log;

@Slf4j
public class TestLogging implements TestLoggingInterface {

    @Override
    @Log
    public void calculate(int param) {
        for (int i = 0; i < 100; i++) {
            int result = param ++;
        }
    }

    @Log
    public void calculate(int param1, int param2) {
        for (int i = 0; i < 100; i++) {
            int result = param1 * param2;
        }
    }

    @Log
    public void calculate(int param1, int param2, int param3) {
        for (int i = 0; i < 100; i++) {
            int result = param1 * param2 / param3;
        }
    }

}
