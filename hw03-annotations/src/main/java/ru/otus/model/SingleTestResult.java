package ru.otus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleTestResult {
    private String className;
    private String methodName;
    private TestResultStatus status;
    private String errMessage;
}