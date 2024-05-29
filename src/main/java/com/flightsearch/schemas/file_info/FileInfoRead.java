package com.flightsearch.schemas.file_info;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FileInfoRead extends FileInfoBase {
    private String id;
}
