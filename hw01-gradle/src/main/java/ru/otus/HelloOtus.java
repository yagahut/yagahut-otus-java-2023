package ru.otus;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class HelloOtus {
    public void hello() {
        System.out.println(Joiner.on(", ").join(Lists.newArrayList("Hello", "world!")));
    }
}
