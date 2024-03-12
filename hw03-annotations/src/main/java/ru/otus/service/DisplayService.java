package ru.otus.service;

import ru.otus.model.TestsResults;
import ru.otus.model.TotalTestsStatistics;

import java.util.List;

public interface DisplayService {
    void displayTotalTestsStatistics(TotalTestsStatistics totalTestsStatistics);
    void displayTestsResults(List<TestsResults> testsResults);
}
