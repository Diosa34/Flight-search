package com.flightsearch.utils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper {
    final private ZipOutputStream zipOut;
    final private List<String> filesInZip;

    public Zipper(Path zipPath) throws IOException {
        zipOut = new ZipOutputStream(Files.newOutputStream(zipPath));
        filesInZip = new ArrayList<>();
    }

    private static @NotNull String normalizeFilename(@NotNull String fileNameInZip) {
        String normalizedFilename = fileNameInZip.replaceAll("^[/\\\\]*", "");
        normalizedFilename = Paths.get(normalizedFilename).normalize().toString();
        return normalizedFilename.replaceAll("\\\\", "/");
    }

    private void createDir(String fileNameInZip) throws IOException {
        String dirPath = normalizeFilename(fileNameInZip) + "/";
        Path parent = Paths.get(dirPath).getParent();
        if (filesInZip.contains(dirPath)) {
            return;
        }
        if (parent != null) {
            createDir(parent.toString());
        }
        zipOut.putNextEntry(new ZipEntry(dirPath));
        zipOut.closeEntry();
        filesInZip.add(dirPath);
    }

    private void copyFile(String fileNameInZip, File fileToZip) throws IOException {
        fileNameInZip = normalizeFilename(fileNameInZip);
        Path parent = Paths.get(fileNameInZip).getParent();
        if (filesInZip.contains(fileNameInZip)) {
            throw new ZipperException("Файл с таким именем уже был добавлен. " + fileNameInZip);
        }
        if (parent != null) {
            createDir(parent.toString());
        }
        filesInZip.add(fileNameInZip);
        zipOut.putNextEntry(new ZipEntry(fileNameInZip));
        FileInputStream fis = new FileInputStream(fileToZip);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }

    public void addFile(String fileNameInZip, @NotNull File fileToZip) throws IOException {
        fileNameInZip = "root/" + fileNameInZip;
        if (fileToZip.isHidden()) return;
        if (fileToZip.isDirectory()) {
            createDir(fileNameInZip);
            File[] files = fileToZip.listFiles();
            for (File childFile : files) {
                addFile(fileNameInZip + "/" + childFile.getName(), childFile);
            }
        } else {
            copyFile(fileNameInZip, fileToZip);
        }
    }

    public void addFile(@NotNull File fileToZip) throws IOException {
        addFile(fileToZip.getName(), fileToZip);
    }

    public void close() throws IOException {
        zipOut.close();
    }
}
