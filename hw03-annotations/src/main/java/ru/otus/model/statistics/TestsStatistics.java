package ru.otus.model.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestsStatistics {
    private List<String> classesUnderTest;
    private long totalTests;
    private long totalPassed;
    private long totalFailed;
    private List<TestStatistics> testStatistics;
}
