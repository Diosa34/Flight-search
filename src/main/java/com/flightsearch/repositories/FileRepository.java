package com.flightsearch.repositories;

import com.flightsearch.config.properties.RepositoryProperties;
import com.flightsearch.exceptions.repositories.FileRepositoryException;
import com.flightsearch.exceptions.repositories.FileRepositoryMethodException;
import com.flightsearch.models.FileInfo;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Repository
public class FileRepository {
    final private FileInfoRepository DBRepo;
    final private Path filesDir;

    public FileRepository(FileInfoRepository DBRepo, RepositoryProperties props) {
        this.DBRepo = DBRepo;
        filesDir = props.getFilesDir();
    }

    protected Path resolveFilePath(FileInfo fileInfo) {
        return filesDir
                .resolve(fileInfo.getLocalDir())
                .resolve(fileInfo.getId().toString() + fileInfo.getFileExtension())
                .normalize();
    }

    /**
     * Создает все несуществующие директории в пути.
     * Подробности возникшего исключения можно узнать из объекта ошибки.
     * @throws FileRepositoryException невозможно создать директорию т.к. с требуемым именем существует файл,
     * или некорректный путь, или недостаточно прав доступа, либо ошибка ввода/вывода.
     * */
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
     * @throws FileRepositoryException файл существует, или некорректный путь,
     * или недостаточно прав доступа, либо ошибка ввода/вывода.
     * @see FileRepositoryException
     * */
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
     * @param fileInfo несохраненная сущность описывающая файл.
     * @param content строка которую нужно записать в файл.
     * @return сохраненная сущность
     * @throws FileRepositoryMethodException если передана сохраненная сущность описывающая файл.
     * @throws FileRepositoryException если возникла ошибка при создании файла.
     * @see FileInfo
     * @see FileRepositoryMethodException
     * @see FileRepositoryException
     * */
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
     * 'директории файлов' + FileInfo.localDir + FileInfo.id.
     * Также выполняется сохранение сущности описывающий данный файл в БД.
     * @param fileInfo несохраненная сущность описывающая файл.
     * @param file полученный файл.
     * @return сохраненная сущность
     * @throws FileRepositoryMethodException если передана сохраненная сущность описывающая файл.
     * @throws FileRepositoryException если возникла ошибка при создании файла.
     * @see FileInfo
     * @see FileRepositoryMethodException
     * @see FileRepositoryException
     * */
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
}
