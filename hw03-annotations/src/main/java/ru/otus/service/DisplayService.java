package ru.otus.service;

import ru.otus.model.TestsResults;
import ru.otus.model.statistics.TestsStatistics;

import java.util.List;

public interface DisplayService {
    void displayStatistics(TestsStatistics totalTestsStatistics);
}
