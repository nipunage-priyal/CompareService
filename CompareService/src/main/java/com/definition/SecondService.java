package com.definition;

import com.data.JSONTestObject;

public interface SecondService {
    JSONTestObject getData(String proformaBenchmark);

    String getDataOneToDataTwoMap(String benchmark);
}
