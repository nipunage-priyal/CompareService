package com.data;

public enum CacheTableMapping {
    FIRST("FIRST"),
    SECOND("SECOND"),
    PROCESS_QUEUE("processQ"),
    PROCESSED("Done");
    private String cacheName;

    CacheTableMapping(String cacheName) {
        this.cacheName = cacheName;
    }

    public String cacheName() {
        return cacheName;
    }
}