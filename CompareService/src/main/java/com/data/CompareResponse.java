package com.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class CompareResponse {
    private DIFF_TYPE type;
    private String attribute;
    private String value;

    public CompareResponse(@JsonProperty("type") DIFF_TYPE type,
                      @JsonProperty("attribute") String attribute,
                      @JsonProperty("value") String value) {
        this.type = type;
        this.attribute = attribute;
        this.value = value;
    }
}
