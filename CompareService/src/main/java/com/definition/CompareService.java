package com.definition;

import com.data.CompareResponse;
import com.data.JSONTestObject;

import java.util.List;

public interface CompareService {
    List<CompareResponse> compare(JSONTestObject obejct1, JSONTestObject obejct2);
    void sendDataBack(List<CompareResponse> responses);
}
