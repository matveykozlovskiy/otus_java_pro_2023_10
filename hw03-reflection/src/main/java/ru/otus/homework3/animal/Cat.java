package ru.otus.homework3.animal;

import ru.otus.homework3.animal.exception.AnimalException;

public class Cat implements Mammal {
    private final String name;

    public Cat(String name) {
        this.name = name;
    }

    @Override
    public String speak() {
        return "Meow";
    }

    @Override
    public void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new AnimalException("Cat's sleep is interrupted");
        }
    }

    @Override
    public int getCountOfLegs() {
        return 4;
    }
}
