package com.data;

import lombok.Builder;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Builder
@Data
public class Index {
    Map<Object, Object> indexAttributes = new LinkedHashMap<>();
    String tableName;
}
