package ru.otus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestsResults {
    private String className;
    private long total;
    private long passed;
    private long failed;
}
