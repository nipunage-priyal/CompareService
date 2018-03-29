package com.service.impl;

import com.data.CompareResponse;
import com.data.DIFF_TYPE;
import com.definition.FilterService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class FilterServiceImpl implements FilterService {
     public CompletableFuture<List<CompareResponse>> filter(List<CompareResponse> responses) {
        try {
            return CompletableFuture.completedFuture(responses.stream().filter(r -> r.getType() == DIFF_TYPE.ADD).collect(Collectors.toList()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
