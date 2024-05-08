package com.flightsearch.schemas.file_info;

import lombok.Data;
import jakarta.validation.constraints.Pattern;

@Data
public class FileInfoBase {
    @Pattern(regexp = "^\\w([\\w/]*\\w)?$")
    private String localDir = "";

    @Pattern(regexp = "^\\w([\\w.-]*\\w)?$")
    private String filename;
}
