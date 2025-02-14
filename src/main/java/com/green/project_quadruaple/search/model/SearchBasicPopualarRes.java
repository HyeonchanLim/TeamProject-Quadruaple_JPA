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
public class SearchBasicPopualarRes {
    private String strfName;
    private int popularCount;
    private Long strfId;
}

