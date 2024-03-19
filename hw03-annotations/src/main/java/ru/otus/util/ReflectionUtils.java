package ru.otus.util;

import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtils {
    @SneakyThrows
    public static Object getNewInstance(Class<?> clazz, Object... args) {
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        return constructor.newInstance(args);
    }

    public static List<Method> getAnnotatedMethod(Class<?> clazz, Class<? extends Annotation> annotation) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(it -> it.isAnnotationPresent(annotation))
                .toList();
    }
}
