package com.green.project_quadruaple.businessmypage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
public class BusinessMyPagePointList {
    private long totalAmount;
    private List<BusinessMyPagePointDetail> pointDetails;
}
