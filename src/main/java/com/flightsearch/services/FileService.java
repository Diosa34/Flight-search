package com.flightsearch.services;

import com.flightsearch.models.FileInfo;
import com.flightsearch.repositories.FileInfoRepository;
import com.flightsearch.repositories.FileRepository;
import com.flightsearch.schemas.file_info.FileInfoCreate;
import com.flightsearch.schemas.file_info.FileInfoRead;
import com.flightsearch.services.mapping.FileInfoMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Profile({"prodMain", "devMain"})
@AllArgsConstructor
public class FileService {
    private final FileInfoRepository fileInfoRepository;
    private final FileRepository fileRepository;
    private final FileInfoMapper fileInfoMapper;

    public FileInfoRead saveFile(MultipartFile file, FileInfoCreate schema) {
        FileInfo fileInfo = fileInfoMapper.mapSignCreateToEntity(schema);
        fileRepository.saveFile(fileInfo, file);
        return fileInfoMapper.mapEntityToFileInfoRead(fileInfo);
    }

    public List<FileInfoRead> getFileInfos() {
        return fileInfoRepository.findAll().stream()
                .map(fileInfoMapper::mapEntityToFileInfoRead)
                .collect(Collectors.toList());
    }

    public FileRepository.FileResource loadFile(UUID fileId) {
        return fileRepository.getFileResource(fileId);
    }
}
