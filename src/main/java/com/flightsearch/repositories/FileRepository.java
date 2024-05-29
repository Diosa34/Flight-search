package com.flightsearch.repositories;

import com.flightsearch.config.properties.RepositoryProperties;
import com.flightsearch.exceptions.NotFoundException;
import com.flightsearch.exceptions.repositories.FileRepositoryException;
import com.flightsearch.exceptions.repositories.FileRepositoryMethodException;
import com.flightsearch.models.FileInfo;
import com.flightsearch.utils.Zipper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Repository
public class FileRepository {
    private final FileInfoRepository DBRepo;
    private final Path filesDir;
    private final Path tempDir;

    public FileRepository(FileInfoRepository DBRepo, RepositoryProperties props) {
        this.DBRepo = DBRepo;
        filesDir = props.getFilesDir();
        tempDir = props.getTempDir();
    }

    /**
     * Возвращает абсолютный путь до файла с учетом переименования.
     * Для корректонй работы необходим экземпляр FileInfo модели с id.
     */
    protected Path resolveFilePath(FileInfo fileInfo) {
        return filesDir
                .resolve(fileInfo.getLocalDir())
                .resolve(fileInfo.getId().toString() + fileInfo.getFileExtension())
                .normalize();
    }

    /**
     * Создает все несуществующие директории в пути.
     * Подробности возникшего исключения можно узнать из объекта ошибки.
     *
     * @throws FileRepositoryException невозможно создать директорию т.к. с требуемым именем существует файл,
     *                                 или некорректный путь, или недостаточно прав доступа, либо ошибка ввода/вывода.
     */
    protected void createDir(Path dirPath) {
        try {
            Files.createDirectories(dirPath);
        } catch (UnsupportedOperationException ignore) {
            throw new FileRepositoryException(
                    "Не удалось создать директории. Некорректный путь.",
                    dirPath.toString()
            );
        } catch (FileAlreadyExistsException ignore) {
            throw new FileRepositoryException(
                    "Не удалось создать директории. Так как с таким именем существует файл.",
                    dirPath.toString()
            );
        } catch (SecurityException ignore) {
            throw new FileRepositoryException(
                    "Не удалось создать директории. Недостаточно прав доступа.",
                    dirPath.toString()
            );
        } catch (IOException ignore) {
            throw new FileRepositoryException(
                    "Не удалось создать директории. Ошибка ввода/вывода.",
                    dirPath.toString()
            );
        }
    }

    /**
     * Создает несуществующий файл, если файл уже существует то, возникает исключение.
     * Подробности возникшего исключения можно узнать из объекта ошибки.
     *
     * @throws FileRepositoryException файл существует, или некорректный путь,
     *                                 или недостаточно прав доступа, либо ошибка ввода/вывода.
     * @see FileRepositoryException
     */
    protected void createFile(Path filePath) {
        try {
            Files.createFile(filePath);
        } catch (UnsupportedOperationException ignore) {
            throw new FileRepositoryException(
                    "Не удалось создать файл. Некорректный путь.",
                    filePath.toString()
            );
        } catch (FileAlreadyExistsException ignore) {
            throw new FileRepositoryException(
                    "Не удалось создать файл. Так как файл с таким именем уже существует.",
                    filePath.toString()
            );
        } catch (SecurityException ignore) {
            throw new FileRepositoryException(
                    "Не удалось создать файл. Недостаточно прав доступа.",
                    filePath.toString()
            );
        } catch (IOException ignore) {
            throw new FileRepositoryException(
                    "Не удалось создать файл. Ошибка ввода/вывода.",
                    filePath.toString()
            );
        }
    }


    protected void writeFile(FileInfo fileInfo, String content) throws IOException {
        Path filePath = resolveFilePath(fileInfo);
        createDir(filePath.getParent());
        createFile(filePath);
        Files.writeString(filePath, content);
    }

    protected void writeFile(FileInfo fileInfo, byte[] content) throws IOException {
        Path filePath = resolveFilePath(fileInfo);
        createDir(filePath.getParent());
        createFile(filePath);
        Files.write(filePath, content);
    }

    /**
     * Создает и сохраняет файл в памяти заданной директории, путь к файлу формируется из
     * 'директории файлов' + FileInfo.localDir + FileInfo.id.
     * Также выполняется сохранение сущности описывающий данный файл в БД.
     *
     * @param fileInfo несохраненная сущность описывающая файл.
     * @param content  строка которую нужно записать в файл.
     * @return сохраненная сущность
     * @throws FileRepositoryMethodException если передана сохраненная сущность описывающая файл.
     * @throws FileRepositoryException       если возникла ошибка при создании файла.
     * @see FileInfo
     * @see FileRepositoryMethodException
     * @see FileRepositoryException
     */
    @Transactional
    public FileInfo saveFile(FileInfo fileInfo, String content) {
        if (fileInfo.getId() != null) {
            throw new FileRepositoryMethodException(
                    "saveFile нельзя использовать для файлов сохраненных в бд."
            );
        }
        DBRepo.save(fileInfo);
        try {
            writeFile(fileInfo, content);
        } catch (IOException ignore) {
            throw new FileRepositoryException(
                    "Не удалось создать файл. Недостаточно прав доступа.",
                    fileInfo.getLocalPath()
            );
        }
        return fileInfo;
    }

    /**
     * Создает и сохраняет файл в памяти заданной директории, путь к файлу формируется из
     * 'директории файлов' + localDir + filename.
     * Также выполняется сохранение сущности описывающий данный файл в БД.
     *
     * @param localDir путь к внутрений директории
     * @param filename нового имя файла
     * @param content  строка которую нужно записать в файл.
     * @return сохраненная сущность
     */
    @Transactional
    public FileInfo saveFile(String localDir, String filename, String content) {
        if (filename.startsWith("/") || filename.startsWith("~")) {
            throw new FileRepositoryMethodException("Локальная директория не может начинатся с '/' или '~'");
        }
        if (localDir.startsWith("/") || localDir.startsWith("~")) {
            throw new FileRepositoryMethodException("Имя файла не может начинатся с '/' или '~'");
        }
        FileInfo fileInfo = DBRepo.save(
                FileInfo.builder()
                        .localDir(localDir)
                        .filename(filename)
                        .build()
        );
        DBRepo.save(fileInfo);
        try {
            writeFile(fileInfo, content);
        } catch (IOException ignore) {
            throw new FileRepositoryException(
                    "Не удалось создать файл. Недостаточно прав доступа.",
                    fileInfo.getLocalPath()
            );
        }
        return fileInfo;
    }

    /**
     * Создает и сохраняет файл в памяти заданной директории, путь к файлу формируется из
     * 'директории файлов' + FileInfo.localDir + FileInfo.id.
     * Также выполняется сохранение сущности описывающий данный файл в БД.
     *
     * @param fileInfo несохраненная сущность описывающая файл.
     * @param file     полученный файл.
     * @return сохраненная сущность
     * @throws FileRepositoryMethodException если передана сохраненная сущность описывающая файл.
     * @throws FileRepositoryException       если возникла ошибка при создании файла.
     * @see FileInfo
     * @see FileRepositoryMethodException
     * @see FileRepositoryException
     */
    @Transactional
    public FileInfo saveFile(FileInfo fileInfo, MultipartFile file) {
        if (fileInfo.getId() != null) {
            throw new FileRepositoryMethodException(
                    "saveFile нельзя использовать для файлов сохраненных в бд."
            );
        }
        if (fileInfo.getFilename() == null) {
            fileInfo.setFilename(file.getOriginalFilename());
        }
        DBRepo.save(fileInfo);
        try {
            writeFile(fileInfo, file.getBytes());
        } catch (IOException ignore) {
            throw new FileRepositoryException(
                    "Не удалось создать файл. Недостаточно прав доступа.",
                    fileInfo.getLocalPath()
            );
        }
        return fileInfo;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class FileResource {
        public Resource resource;
        public FileInfo fileInfo;
        public long length;

        public String getFilename() {
            if (fileInfo == null) {
                return Paths.get(resource.getFilename()).getFileName().toString();
            }
            return fileInfo.getFilename();
        }
    }

    public FileResource getFileResource(UUID fileId) {
        FileInfo fileInfo = DBRepo.findById(fileId).orElseThrow(
                () -> new NotFoundException(fileId, "FileInfo")
        );
        Path filePath = resolveFilePath(fileInfo);
        return new FileResource(
                new FileSystemResource(filePath),
                fileInfo,
                filePath.toFile().length()
        );
    }

    public FileResource getZipResource(String zipName, FileInfo... fileInfos) {
        try {
            createDir(tempDir);
            Path zipTempDir = Files.createTempDirectory(tempDir, "zip");
            Path zipFilePath = zipTempDir.resolve(zipName + ".zip");

            Zipper zipper = new Zipper(zipFilePath);
            for (FileInfo fileInfo : fileInfos) {
                zipper.addFile(fileInfo.getFilename(), resolveFilePath(fileInfo).toFile());
            }
            zipper.close();

            return new FileResource(
                    new FileSystemResource(zipFilePath),
                    null,
                    zipFilePath.toFile().length()
            );
        } catch (IOException ignore) {
            throw new FileRepositoryException("Не удалось собрать архив.", "");
        }
    }
}
