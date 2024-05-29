package com.flightsearch.exceptions.repositories;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Клас ошибки, которая возникает при невозможности создать файл.
 * Причины возникновения:
 * <ul>
 *     <li>Недостаточно прав доступа к катологу/файлу;</li>
 *     <li>Файл с таким именем уже существует;</li>
 *     <li>Невозможно создать католог;</li>
 *     <li>Ошибка ввода вывода.</li>
 * </ul>
 * Более подробное описание причины возникновения можно получить вызвав метод getMessage().
 * Для получения полного необходимо вызвать метод getFullPath().
 */
@AllArgsConstructor
@Getter
public class FileRepositoryException extends Error {
    private final String message;
    private final String fullPath;
}
