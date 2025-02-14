package com.green.project_quadruaple.tripreview.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CopyInsertScheMemoDto {
    private long tripId;
    private long copyTripId;
    private long scheduleMemoId;
}
