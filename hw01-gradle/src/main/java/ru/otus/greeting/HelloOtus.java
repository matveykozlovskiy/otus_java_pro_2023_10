package ru.otus.greeting;

import com.google.common.primitives.Chars;
import com.google.common.primitives.Ints;
import java.util.Objects;
import java.util.stream.Collectors;

public class HelloOtus {
    char[] lettersName = {'M', 'a', 't', 'v', 'e', 'y'};
    int[] integersBirthDay = {2, 3};
    int[] integersBirthMonth = {0, 2};
    int[] integersBirthYear = {1, 9, 9, 8};

    public void sayHello() {
        String nameAndBirthDate = getNameAndBirthDate();

        System.out.println("Hello " + nameAndBirthDate);
    }

    private String getNameAndBirthDate() {
        String name = Chars.asList(lettersName).stream().map(Objects::toString).collect(Collectors.joining(""));

        String birthDate = Ints.join("", Ints.concat(integersBirthDay, integersBirthMonth, integersBirthYear));

        return name + " " + birthDate;
    }
}
