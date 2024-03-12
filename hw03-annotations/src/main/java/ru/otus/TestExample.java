package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.util.Asserts;

public class TestExample {

    @Before
    public void before1() {
        System.out.println("before1");
    }

    @Before
    public void before2() {
        System.out.println("before2");
    }

    @Test
    public void shouldPass() {
        System.out.println("in test1");
        Asserts.assertEquals(1, 1);
    }

    @After
    public void after1() {
        System.out.println("after1");
    }
}
