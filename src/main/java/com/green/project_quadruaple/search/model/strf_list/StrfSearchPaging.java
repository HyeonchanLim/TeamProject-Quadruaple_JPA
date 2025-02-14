package com.green.project_quadruaple.search.model.strf_list;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.project_quadruaple.common.model.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;

@Getter
@ToString
@EqualsAndHashCode
public class StrfSearchPaging {
    @Schema(example = "1")
    private int page;
    @Schema(example = "3")
    private int size;
    @JsonIgnore
    private int startIdx;

    public StrfSearchPaging(Integer page, Integer size) {
        this.page = page==null || page<=0? 1 : page;
        this.size = (size == null || size <= 0) ? Constants.getDefault_search_size() : size;
        this.startIdx = ( this.page - 1 ) * this.size;
    }
}
