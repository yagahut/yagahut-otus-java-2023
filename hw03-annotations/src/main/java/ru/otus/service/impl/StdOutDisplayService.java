package ru.otus.service.impl;

import ru.otus.model.statistics.SingleTestStatistics;
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

        testsStatistics.getSingleTestStatistics()
                .forEach(this::displayTestStatisticsRow);
    }

    private void displayTestStatisticsRow(SingleTestStatistics singleTestStatistics) {
        var sb = new StringBuilder("""
                ClassName: %s
                MethodName: %s,
                Status: %s""".formatted(singleTestStatistics.getClassName(), singleTestStatistics.getMethodName(), singleTestStatistics.getStatus()));

        if(singleTestStatistics.getErrMessage() != null) {
            sb.append("\nError message:\"%s\"".formatted(singleTestStatistics.getErrMessage()));
        }
        sb.append("\n");
        System.out.println(sb);
    }
}
