package ru.otus.service;

import ru.otus.model.TestsResults;
import ru.otus.model.TotalTestsStatistics;

import java.util.List;

public class StatisticsService {
    public TotalTestsStatistics getTotalStatistics(List<TestsResults> testsResults) {
        List<String> classesUnderTest = testsResults.stream()
                .map(TestsResults::getClassName)
                .toList();

        long totalTests = testsResults.stream()
                .map(TestsResults::getTotal)
                .mapToLong(Long::longValue)
                .sum();

        long totalPassed = testsResults.stream()
                .map(TestsResults::getPassed)
                .mapToLong(Long::longValue)
                .sum();

        long totalFailed = testsResults.stream()
                .map(TestsResults::getFailed)
                .mapToLong(Long::longValue)
                .sum();

        return new TotalTestsStatistics(
                classesUnderTest,
                totalTests,
                totalPassed,
                totalFailed
        );
    }
}
