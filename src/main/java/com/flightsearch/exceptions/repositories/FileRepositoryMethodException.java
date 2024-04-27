package com.flightsearch.exceptions.repositories;

/**
 * Клас ошибки, которая возникает при неккоректном использовании методов класса FileRepository.
 * Ошибка является необрабатываемой, следовательно для ее устарнения нужно изменить исходный код.
 * */
public class FileRepositoryMethodException extends RuntimeException {
    public FileRepositoryMethodException(String message) {
        super(message);
    }
}
