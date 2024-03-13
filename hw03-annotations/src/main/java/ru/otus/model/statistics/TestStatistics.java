package ru.otus.model.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.model.TestResultStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestStatistics {
    private String className;
    private String methodName;
    private TestResultStatus status;
    private String errMessage;
}
