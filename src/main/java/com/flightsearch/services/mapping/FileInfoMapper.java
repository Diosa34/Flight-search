package com.flightsearch.services.mapping;

import com.flightsearch.models.FileInfo;
import com.flightsearch.schemas.file_info.FileInfoBase;
import com.flightsearch.schemas.file_info.FileInfoCreate;
import com.flightsearch.schemas.file_info.FileInfoRead;
import org.springframework.stereotype.Service;

@Service
public class FileInfoMapper {
    protected FileInfo mapFileInfoBaseToEntity(FileInfoBase schema, FileInfo entity) {
        entity.setFilename(schema.getFilename());
        entity.setLocalDir(schema.getLocalDir());
        return entity;
    }

    protected FileInfo mapFileInfoBaseToEntity(FileInfoBase schema) {
        return mapFileInfoBaseToEntity(schema, new FileInfo());
    }

    public FileInfo mapSignCreateToEntity(FileInfoCreate schema) {
        return mapFileInfoBaseToEntity(schema);
    }

    public FileInfoRead mapEntityToFileInfoRead(FileInfo entity) {
        FileInfoRead schema = new FileInfoRead();
        schema.setId(entity.getId().toString());
        schema.setFilename(entity.getFilename());
        schema.setLocalDir(entity.getLocalDir());
        return schema;
    }
}
