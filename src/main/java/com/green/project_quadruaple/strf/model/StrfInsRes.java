package com.green.project_quadruaple.strf.model;

import com.green.project_quadruaple.entity.model.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class StrfInsRes {
    private List<StayTourRestaurFest> strf;
    private List<StrfPic> strfPics;
    private List<RestDate> restdates;
    private List<Amenipoint> amenipoints;
    private List<Amenity> amenities;
    private List<StrfMenu> menus;
    private List<Parlor> parlors;
    private List<Room> rooms;

}
