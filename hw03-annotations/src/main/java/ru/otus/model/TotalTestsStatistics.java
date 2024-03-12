package ru.otus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalTestsStatistics {
    private List<String> classesUnderTest;
    private long totalTests;
    private long totalPassed;
    private long totalFailed;
}
