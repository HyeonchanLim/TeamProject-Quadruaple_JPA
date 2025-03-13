package com.green.project_quadruaple.entity.view;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DepayIds implements Serializable {
    private Long deId;
    private Long tripId;
    private Long userId;
}
