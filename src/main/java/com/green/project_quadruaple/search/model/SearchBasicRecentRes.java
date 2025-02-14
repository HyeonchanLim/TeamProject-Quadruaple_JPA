package com.green.project_quadruaple.search.model;

import com.green.project_quadruaple.search.model.strf_list.StrfSearchPaging;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Schema(title = "검색 정보")
@ToString
@EqualsAndHashCode
public class SearchBasicRecentRes {
    private Long strfId;
    private String strfName;
    private String inquiredAt;
    private String strfPic;
    private String category;
    private String locationTitle;
}


