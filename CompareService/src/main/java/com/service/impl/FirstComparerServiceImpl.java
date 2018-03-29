package com.service.impl;

import com.data.JSONTestObject;
import com.definition.FirstService;

import java.util.stream.IntStream;

public class FirstComparerServiceImpl implements FirstService {
    @Override
    public JSONTestObject getData(String key) {
        IntStream.range(0, 10).forEach(
                nbr -> {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                    }
                }
        );

        JSONTestObject dob = JSONTestObject.builder().cahcename("attractionsCache1").description("Caches for Attractions").name("Attraction2").uid("2").build();
        return dob;
    }
}
