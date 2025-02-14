package com.green.project_quadruaple.search.model.strf_list;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class GetSearchStrfListBasicRes {

    private boolean isMore;
    private List<String> locationTitleList;
    private List<StrfShortInfoDto> list;
}
/*
{
  "code" : "200 성공",
  "data" : {
    "category" : "숙소" ,
    "isMore" : "true" ,
    "list" : [

      ...
    ]
  }
}
*/
