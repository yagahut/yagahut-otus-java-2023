package ru.otus.service.impl;

import ru.otus.model.TestsResults;
import ru.otus.model.TotalTestsStatistics;
import ru.otus.service.DisplayService;

import java.util.List;

public class StdOutDisplayService implements DisplayService {

    @Override
    public void displayTotalTestsStatistics(TotalTestsStatistics totalTestsStatistics) {
        System.out.println("----------------------------------------");
        System.out.println("Classes under test:\n%s\n".formatted(totalTestsStatistics.getClassesUnderTest()));
        System.out.println("total tests: %d".formatted(totalTestsStatistics.getTotalTests()));
        System.out.println("total tests passed: %d".formatted(totalTestsStatistics.getTotalPassed()));
        System.out.println("total tests failed: %d\n".formatted(totalTestsStatistics.getTotalFailed()));
    }

    @Override
    public void displayTestsResults(List<TestsResults> testsResults) {
        testsResults.forEach(this::displayTestsResultsRow);
    }

    private void displayTestsResultsRow(TestsResults result) {
        System.out.println("""
                %s
                total: %d
                success: %d
                failed: %d
                """.formatted(result.getClassName(), result.getTotal(), result.getPassed(), result.getFailed()));
    }
}
