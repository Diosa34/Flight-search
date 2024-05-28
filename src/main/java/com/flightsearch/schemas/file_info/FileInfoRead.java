package com.flightsearch.schemas.file_info;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class FileInfoRead extends FileInfoBase {
    private String id;
}
