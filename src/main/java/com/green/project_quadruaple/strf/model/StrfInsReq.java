package com.green.project_quadruaple.strf.model;

import com.green.project_quadruaple.entity.model.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class StrfInsReq {
    private long strfId;
    private long locationDetailId;
    private String category;
    private String cid;
    private String title;
    private double lat;
    private double lng;
    private String address;
    private String post;
    private String tell;
    private LocalDate startAt;
    private LocalDate endAt;
    private LocalDateTime openCheckIn;
    private LocalDateTime closeCheckOut;
    private String detail;
    private String busiNum;
    private int state;
    private List<StrfPic> strfPics;
    private List<RestDate> restdates;
    private List<Amenipoint> amenipoints;
    private List<Amenity> amenities;
    private List<Menu> menus;
    private List<Parlor> parlors;
    private List<Room> rooms;
}