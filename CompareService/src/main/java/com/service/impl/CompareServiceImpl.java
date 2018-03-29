package com.service.impl;

import com.data.CompareResponse;
import com.data.DIFF_TYPE;
import com.data.JSONTestObject;
import com.definition.CompareService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompareServiceImpl implements CompareService{
    public List<CompareResponse> compare(JSONTestObject benchmark, JSONTestObject proforma) {

        final Map<String, List<String>> attributes = new HashMap<String, List<String>>();

        if (!benchmark.getCahcename().equals(proforma.getCahcename())) {
            attributes.put("cacheName", Stream.of(benchmark.getCahcename(), proforma.getCahcename()).collect(Collectors.toList()));
        }
        if (!benchmark.getDescription().equals(proforma.getDescription())) {
            attributes.put("description", Stream.of(benchmark.getDescription(), proforma.getDescription()).collect(Collectors.toList()));
        }
        if (!benchmark.getName().equals(proforma.getName())) {
            attributes.put("name", Stream.of(benchmark.getName(), proforma.getName()).collect(Collectors.toList()));

        }
        if (!benchmark.getUid().equals(proforma.getUid())) {
            attributes.put("uid", Stream.of(benchmark.getUid(), proforma.getUid()).collect(Collectors.toList()));
        }
        List<CompareResponse> response = new ArrayList<>();
        attributes.keySet().stream().forEach(k -> {
            response.add(CompareResponse.builder().attribute(k).type(DIFF_TYPE.ADD).value(attributes.get(k).get(0)).build());
            response.add(CompareResponse.builder().attribute(k).type(DIFF_TYPE.DELETE).value(attributes.get(k).get(1)).build());
        });
        return response;
    }
    public void sendDataBack(List<CompareResponse> responses) {
        responses.stream().forEach(
                cr -> System.out.println(cr.toString())
        );
    }
}
