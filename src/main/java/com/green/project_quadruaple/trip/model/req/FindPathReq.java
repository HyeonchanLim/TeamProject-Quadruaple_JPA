package com.green.project_quadruaple.trip.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class FindPathReq {

    @Schema(title = "SX", type = "string", example = "128.523077", requiredMode = Schema.RequiredMode.REQUIRED)
    private String startLngSX;
    @Schema(title = "SY", type = "string", example = "35.858739", requiredMode = Schema.RequiredMode.REQUIRED)
    private String startLatSY;
    @Schema(title = "EX", type = "string", example = "128.060043", requiredMode = Schema.RequiredMode.REQUIRED)
    private String endLngEX;
    @Schema(title = "EY", type = "string", example = "35.173303", requiredMode = Schema.RequiredMode.REQUIRED)
    private String endLatEY;

}
