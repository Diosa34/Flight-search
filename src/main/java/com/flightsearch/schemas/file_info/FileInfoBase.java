package com.flightsearch.schemas.file_info;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class FileInfoBase {
    @Pattern(regexp = "^\\w([\\w/]*\\w)?$")
    private String localDir = "";

    @Pattern(regexp = "^\\w([\\w.-]*\\w)?$")
    private String filename;
}
