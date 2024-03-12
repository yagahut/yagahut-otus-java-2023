package ru.otus;

import lombok.SneakyThrows;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.exception.TestRunningException;
import ru.otus.model.TestResultStatus;
import ru.otus.model.TestsResults;
import ru.otus.service.DisplayService;
import ru.otus.service.StatisticsService;
import ru.otus.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TestRunner {

    private final StatisticsService statisticsService;
    private final DisplayService displayService;

    public TestRunner(StatisticsService statisticsService, DisplayService displayService) {
        this.statisticsService = statisticsService;
        this.displayService = displayService;
    }

    public void run(Class<?>... classes) {
        List<TestsResults> testsResults =
                Arrays.stream(classes)
                        .map(this::run)
                        .toList();

        displayService.displayTotalTestsStatistics(statisticsService.getTotalStatistics(testsResults));
        displayService.displayTestsResults(testsResults);
    }

    private TestsResults run(Class<?> clazz) {
        try {
            List<Method> testMethods = ReflectionUtils.getAnnotatedMethod(clazz, Test.class);
            List<Method> beforeMethods = ReflectionUtils.getAnnotatedMethod(clazz, Before.class);
            List<Method> afterMethods = ReflectionUtils.getAnnotatedMethod(clazz, After.class);

            runBeforeMethods(beforeMethods, clazz);
            var testResultMap = runTestMethods(testMethods, clazz);
            runAfterMethods(afterMethods, clazz);

            return new TestsResults(
                    clazz.getName(),
                    testMethods.size(),
                    testResultMap.getOrDefault(TestResultStatus.PASSED, 0L),
                    testResultMap.getOrDefault(TestResultStatus.FAILED, 0L)
            );
        } catch (Exception e) {
            throw new TestRunningException(
                    "Exception while running tests on class: %s".formatted(clazz.getName()),
                    e.getCause()
            );
        }
    }

    private void runBeforeMethods(List<Method> beforeMethods, Class<?> clazz) {
        beforeMethods.forEach(it -> runBeforeMethod(it, ReflectionUtils.getNewInstance(clazz)));
    }

    @SneakyThrows
    private void runBeforeMethod(Method before, Object instance) {
        before.invoke(instance);
    }

    public Map<TestResultStatus, Long> runTestMethods(List<Method> testMethods, Class<?> clazz) {
        return testMethods
                .stream()
                .map(it -> runTestMethod(ReflectionUtils.getNewInstance(clazz), it))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private TestResultStatus runTestMethod(Object instance, Method method) {
        try {
            method.invoke(instance);
            return TestResultStatus.PASSED;
        } catch (Exception e) {
            //TODO: add cause (failed message)
            return TestResultStatus.FAILED;
        }
    }

    public void runAfterMethods(List<Method> afterMethods, Class<?> clazz) {
        afterMethods.forEach(it -> runBeforeMethod(it, ReflectionUtils.getNewInstance(clazz)));
    }

    @SneakyThrows
    private void runAfterMethod(Method afterMethod, Object instance) {
        afterMethod.invoke(instance);
    }

}
