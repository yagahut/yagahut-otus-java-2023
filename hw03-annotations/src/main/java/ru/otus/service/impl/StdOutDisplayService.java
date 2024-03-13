package ru.otus.service.impl;

import ru.otus.model.statistics.TestStatistics;
import ru.otus.model.statistics.TestsStatistics;
import ru.otus.service.DisplayService;

public class StdOutDisplayService implements DisplayService {

    @Override
    public void displayStatistics(TestsStatistics testsStatistics) {
        System.out.println("----------------------------------------");
        System.out.println("Classes under test:\n%s\n".formatted(testsStatistics.getClassesUnderTest()));
        System.out.println("total tests: %d".formatted(testsStatistics.getTotalTests()));
        System.out.println("total tests passed: %d".formatted(testsStatistics.getTotalPassed()));
        System.out.println("total tests failed: %d\n".formatted(testsStatistics.getTotalFailed()));

        testsStatistics.getTestStatistics()
                .forEach(this::displayTestStatisticsRow);
    }

    private void displayTestStatisticsRow(TestStatistics testStatistics) {
        var sb = new StringBuilder("""
                ClassName: %s
                MethodName: %s,
                Status: %s
                """.formatted(testStatistics.getClassName(), testStatistics.getMethodName(), testStatistics.getStatus()));

        if(testStatistics.getErrMessage() != null) {
            sb.append("Error message:\"%s\"".formatted(testStatistics.getErrMessage()));
        }
        sb.append("\n");
        System.out.println(sb);
    }
}
