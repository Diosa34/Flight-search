package com.flightsearch.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String localDir = "";

    /**
     * Отделяет расширение файла и возвращает его, если в названии файла нет ".",
     * возвращает пустую строку. Расширение файла возвращается с точкой.
     * @return расширение файла
     * */
    public String getFileExtension() {
        if (filename.contains(".")) {
            return filename.substring(filename.lastIndexOf("."));
        } else {
            return "";
        }
    }

    /**
     * Формирует путь относительно установленной директории для файлов на сервере.
     * @return относительный путь
     * */
    public String getLocalPath() {
        return localDir + '/' + filename;
    }
}
