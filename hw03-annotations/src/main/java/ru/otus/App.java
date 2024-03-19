package ru.otus;

import ru.otus.service.StatisticsService;
import ru.otus.service.impl.StdOutDisplayService;

public class App {
    public static void main(String[] args) {
        new TestRunner(new StatisticsService(), new StdOutDisplayService())
                .run(TestExample.class, TestExample2.class);
    }
}
