package com.flightsearch.schemas.document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Payrolls {
    @Schema(description = "Заинтересованная сторона")
    private List<Long> counterpartIds = new ArrayList<>();
}
