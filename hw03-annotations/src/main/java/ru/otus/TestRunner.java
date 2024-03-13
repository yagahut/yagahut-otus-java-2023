package ru.otus;

import lombok.SneakyThrows;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.exception.TestRunningException;
import ru.otus.model.TestResult;
import ru.otus.model.TestResultStatus;
import ru.otus.model.TestsResults;
import ru.otus.model.statistics.TestsStatistics;
import ru.otus.service.DisplayService;
import ru.otus.service.StatisticsService;
import ru.otus.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

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

        TestsStatistics statistics = statisticsService.getStatistics(testsResults);
        displayService.displayStatistics(statistics);
    }

    private TestsResults run(Class<?> clazz) {
        try {
            List<Method> testMethods = ReflectionUtils.getAnnotatedMethod(clazz, Test.class);
            List<Method> beforeMethods = ReflectionUtils.getAnnotatedMethod(clazz, Before.class);
            List<Method> afterMethods = ReflectionUtils.getAnnotatedMethod(clazz, After.class);

            runBeforeMethods(beforeMethods, clazz);
            var testResults = runTestMethods(testMethods, clazz);
            runAfterMethods(afterMethods, clazz);

            return new TestsResults(
                    clazz.getName(),
                    testResults
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
    private void runBeforeMethod(Method beforeMethod, Object instance) {
        beforeMethod.invoke(instance);
    }

    public List<TestResult> runTestMethods(List<Method> testMethods, Class<?> clazz) {
        return testMethods
                .stream()
                .map(it -> runTestMethod(ReflectionUtils.getNewInstance(clazz), it))
                .toList();
    }

    private TestResult runTestMethod(Object instance, Method method) {
        TestResult result = new TestResult();
        result.setClassName(instance.getClass().getName());
        result.setMethodName(method.getName());
        try {
            method.invoke(instance);
            result.setStatus(TestResultStatus.PASSED);
        } catch (Exception e) {
            //TODO: add cause (failed message)
            result.setStatus(TestResultStatus.FAILED);
            result.setErrMessage(e.getCause().getMessage());
        }
        return result;
    }

    public void runAfterMethods(List<Method> afterMethods, Class<?> clazz) {
        afterMethods.forEach(it -> runAfterMethod(it, ReflectionUtils.getNewInstance(clazz)));
    }

    @SneakyThrows
    private void runAfterMethod(Method afterMethod, Object instance) {
        afterMethod.invoke(instance);
    }

}
