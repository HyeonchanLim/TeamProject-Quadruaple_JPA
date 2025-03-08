package com.green.project_quadruaple.point.model.res;

import com.green.project_quadruaple.entity.model.PointCard;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class PointCardProductRes {
    private Integer remainPoints;
    private List<PointCard> pointCards;
}
