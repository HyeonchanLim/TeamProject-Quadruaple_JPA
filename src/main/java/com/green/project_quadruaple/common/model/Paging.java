package com.green.project_quadruaple.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Paging {
    @Schema(example = "1")
    private int page;
    @Schema(example = "6")
    private int size;
    @JsonIgnore
    private int startIdx;

    public Paging(Integer page , Integer size){
        this.page = page==null || page<=0? 1 : page;
        this.size = (size == null || size <= 0) ? Constants.getDefault_page_size() : size;
        this.startIdx = ( this.page - 1 ) * this.size;
    }
}

