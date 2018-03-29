package com.guice.providers;

import com.google.inject.Provider;
import com.service.impl.JedisServiceImpl;

public class PersistanceServiceProvider implements Provider<JedisServiceImpl> {
    public JedisServiceImpl get() {
        return new JedisServiceImpl();
    }
}
