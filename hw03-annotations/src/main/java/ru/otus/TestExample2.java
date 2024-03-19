package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.util.Asserts;

public class TestExample2 {

    @Before
    public void before3() {
        System.out.println("before3");
    }

    int expected = 2;

    @Test
    public void shouldAlsoPass() {
        Asserts.assertEquals(expected, 2);
        expected = 3;
    }

    @Test
    public void willFail() {
        Asserts.assertEquals(expected, 3);
    }

    @After
    public void after2() {
        System.out.println("after2");
    }

    @After
    public void after3() {
        System.out.println("after3");
    }

}
