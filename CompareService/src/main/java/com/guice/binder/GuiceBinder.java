package com.guice.binder;

import com.definition.*;
import com.google.inject.AbstractModule;
import com.guice.providers.PersistanceServiceProvider;
import com.service.impl.*;

public class GuiceBinder extends AbstractModule {
    @Override
    protected void configure() {
        bind(PersistanceDao.class).toProvider(PersistanceServiceProvider.class);
        bind(FirstService.class).to(FirstComparerServiceImpl.class);
        bind(SecondService.class).to(SecondComparerServiceImpl.class);
        bind(FilterService.class).to(FilterServiceImpl.class);
        bind(CompareService.class).to(CompareServiceImpl.class);
    }
}
