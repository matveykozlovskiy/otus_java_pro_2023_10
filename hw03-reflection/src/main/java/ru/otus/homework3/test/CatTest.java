package ru.otus.homework3.test;

import org.assertj.core.api.AssertionsForClassTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework3.animal.Cat;
import ru.otus.homework3.animal.Mammal;
import ru.otus.homework3.util.annotation.After;
import ru.otus.homework3.util.annotation.Before;
import ru.otus.homework3.util.annotation.Test;

public class CatTest {
    Logger logger = LoggerFactory.getLogger(CatTest.class.getName());

    private Mammal cat;

    @Before
    void initCat() {
        cat = new Cat("TEST_CAT_WITHOUT_NAME ");
        logger.info("We are starting testing cat " + cat);
    }

    @Test
    void testCatShouldReturnCorrectCountOfLegs() {
        logger.info("Failed getCountOfLegs test");
        AssertionsForClassTypes.assertThat(cat.getCountOfLegs()).isEqualTo(5);
    }

    @Test
    void testCatShouldSpeak() {
        logger.info("Successful speak test");
        AssertionsForClassTypes.assertThat(cat.speak()).isEqualTo("Meow");
    }

    @Test
    void testCatShouldSleep() {
        logger.info("Successful sleep test");
        AssertionsForClassTypes.assertThatNoException().isThrownBy(() -> cat.sleep());
    }

    @After
    void summarizeCatTestingResult() {
        logger.info("Cat {} was tested", cat);
    }
}
