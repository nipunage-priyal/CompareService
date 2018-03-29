package com.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Builder
@Data

public class JSONTestObject extends BaseDataObject implements Serializable {

    @Column(nullable = false)
    private final String name;
    @Column(nullable = false)
    private final String description;
    @Column(nullable = false)
    private final String uid;
    @Column(nullable = false)
    private final String cahcename;


    public JSONTestObject(@JsonProperty("name") String name,
                          @JsonProperty("description") String description,
                          @JsonProperty("uid") String uid, @JsonProperty("cahcename") String cahcename) {
        this.cahcename = cahcename;
        this.description = description;
        this.name = name;
        this.uid = uid;
    }
}
