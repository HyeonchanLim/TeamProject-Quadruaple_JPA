package com.green.project_quadruaple.strf.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class StrfUpdAddress {
    private long strfId;
    private String busiNum;
    private String category;

    private String address;
    private String locationDetailTitle;
    private double lat;
    private double lng;
    private String post;
}
