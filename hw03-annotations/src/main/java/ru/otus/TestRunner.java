package ru.otus;

import lombok.SneakyThrows;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.exception.TestRunningException;
import ru.otus.model.SingleTestResult;
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

            var testResults = runTestMethods(testMethods, clazz, beforeMethods, afterMethods);

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

    private void runBeforeMethods(List<Method> beforeMethods, Object instance) {
        beforeMethods.forEach(it -> runBeforeMethod(it, instance));
    }

    @SneakyThrows
    private void runBeforeMethod(Method beforeMethod, Object instance) {
        beforeMethod.invoke(instance);
    }

    public List<SingleTestResult> runTestMethods(List<Method> testMethods,
                                                 Class<?> clazz,
                                                 List<Method> beforeMethods,
                                                 List<Method> afterMethods) {
        return testMethods
                .stream()
                .map(it -> {
                    Object instance = ReflectionUtils.getNewInstance(clazz);
                    runBeforeMethods(beforeMethods, instance);
                    var result = runTestMethod(instance, it);
                    runAfterMethods(afterMethods, instance);
                    return result;
                })
                .toList();
    }

    private SingleTestResult runTestMethod(Object instance, Method method) {
        SingleTestResult result = new SingleTestResult();
        result.setClassName(instance.getClass().getName());
        result.setMethodName(method.getName());
        try {
            method.invoke(instance);
            result.setStatus(TestResultStatus.PASSED);
        } catch (Exception e) {
            result.setStatus(TestResultStatus.FAILED);
            result.setErrMessage(e.getCause().getMessage());
        }
        return result;
    }

    public void runAfterMethods(List<Method> afterMethods, Object instance) {
        afterMethods.forEach(it -> runAfterMethod(it, instance));
    }

    @SneakyThrows
    private void runAfterMethod(Method afterMethod, Object instance) {
        afterMethod.invoke(instance);
    }

}
