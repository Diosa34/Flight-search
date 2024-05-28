package com.flightsearch.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ZipperTest {
    @DisplayName("Проверка Zipper.normalizeFilename")
    @ParameterizedTest
    @CsvFileSource(resources = "/com/flightsearch/utils/ZipperTest/testNormalizeFilename.csv", numLinesToSkip = 1)
    public void testNormalizeFilename(String rawPath, String normalizedPath)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = Zipper.class.getDeclaredMethod("normalizeFilename", String.class);
        method.setAccessible(true);
        assertEquals(normalizedPath, method.invoke(null, rawPath));
    }
}
