package com.flightsearch.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipTools {
    private static ZipOutputStream newZipOutputStream(Path zipFilename) throws IOException {
        Files.createDirectories(zipFilename.getParent());
        Files.createFile(zipFilename);
        OutputStream fileOutputStream = Files.newOutputStream(zipFilename);
        return new ZipOutputStream(fileOutputStream);
    }

    public static void zip(Path sourceDir, Path zipFilename) throws IOException {
        ZipOutputStream zipOutputStream = newZipOutputStream(zipFilename);
        ZipEntry zipEntry = new ZipEntry(sourceDir.getFileName().toString());
        zipOutputStream.putNextEntry(zipEntry);
        zipOutputStream.close();
    }
}
