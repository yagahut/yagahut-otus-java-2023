package ru.otus.service;

import ru.otus.model.SingleTestResult;
import ru.otus.model.TestResultStatus;
import ru.otus.model.TestsResults;
import ru.otus.model.statistics.SingleTestStatistics;
import ru.otus.model.statistics.TestsStatistics;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticsService {
    public TestsStatistics getStatistics(List<TestsResults> testsResults) {
        List<String> classesUnderTest =
                testsResults.stream()
                        .map(TestsResults::getClassName)
                        .toList();

        Map<TestResultStatus, Long> totalResultsMap =
                testsResults.stream()
                        .map(TestsResults::getSingleTestResults)
                        .flatMap(Collection::stream)
                        .collect(Collectors.groupingBy(SingleTestResult::getStatus, Collectors.counting()));

        long totalTests = totalResultsMap.values().stream()
                .mapToLong(Long::longValue)
                .sum();

        List<SingleTestStatistics> singleTestStatistics = testsResults.stream()
                .map(TestsResults::getSingleTestResults)
                .flatMap(Collection::stream)
                .map(this::mapToTestStatistics)
                .toList();

        return new TestsStatistics(
                classesUnderTest,
                totalTests,
                totalResultsMap.getOrDefault(TestResultStatus.PASSED, 0L),
                totalResultsMap.getOrDefault(TestResultStatus.FAILED, 0L),
                singleTestStatistics
        );

    }

    private SingleTestStatistics mapToTestStatistics(SingleTestResult singleTestResult) {
        return new SingleTestStatistics(
                singleTestResult.getClassName(),
                singleTestResult.getMethodName(),
                singleTestResult.getStatus(),
                singleTestResult.getErrMessage()
        );
    }

}
