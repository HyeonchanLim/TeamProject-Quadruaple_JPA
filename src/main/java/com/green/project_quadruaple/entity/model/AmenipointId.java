package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AmenipointId implements Serializable {
    private Long amenityId;
    private String strfId;
}
