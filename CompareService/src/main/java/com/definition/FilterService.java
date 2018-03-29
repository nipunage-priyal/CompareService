package com.definition;

import com.data.CompareResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface FilterService {
    CompletableFuture<List<CompareResponse>> filter(List<CompareResponse> responses);
}
