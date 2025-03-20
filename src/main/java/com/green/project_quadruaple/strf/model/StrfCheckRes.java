package com.green.project_quadruaple.strf.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrfCheckRes {
    private long menuId;
    private boolean check;
}
