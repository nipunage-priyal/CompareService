package com.service.impl;

import com.data.JSONTestObject;
import com.definition.SecondService;

import java.util.stream.IntStream;

public class SecondComparerServiceImpl implements SecondService {
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
        JSONTestObject dob = JSONTestObject.builder().cahcename("attractionsCache").description("Cache for Attractions").name("Attraction1").uid("1").build();
        return dob;
    }

    public String getDataOneToDataTwoMap(String benchmark) {
        return "proforma";
    }
}
