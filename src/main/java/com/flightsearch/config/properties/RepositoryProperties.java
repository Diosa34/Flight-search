package com.flightsearch.config.properties;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * RepositoryProperties класс описывающий основные свойства репозиториев проекта.
 * Доступные свойства:
 * <ul>
 *     <li>base-dir - корневая директория для всех репозиториев</li>
 *     <li>files-dir - имя директории для файлов (FileRepository)</li>
 *     <li>xml-dir - имя директории для файлов xml</li>
 *     <li>user-xml-filename - имя файла xml для дампа пользователей</li>
 * </ul>
 */
@Setter
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "application.repositories")
public class RepositoryProperties {
    private String baseDir = System.getProperty("user.dir") + "/application-repositories";
    private String tempDir = "temp";
    private String filesDir = "files";
    private String xmlDir = "xml";
    private String userXmlFilename = "users.xml";

    public Path getBaseDir() {
        return Paths.get(baseDir).toAbsolutePath().normalize();
    }

    public Path getTempDir() {
        return getBaseDir().resolve(tempDir).normalize();
    }

    public Path getFilesDir() {
        return getBaseDir().resolve(filesDir).normalize();
    }

    public Path getXmlDir() {
        return getBaseDir().resolve(xmlDir).normalize();
    }

    public Path getUserXmlFilename() {
        return getXmlDir().resolve(userXmlFilename).normalize();
    }
}
