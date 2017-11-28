package com.it2go.framework.entities;

import java.io.Serializable;

public interface IAbstractEntity<K> extends Serializable {

    K getId();

    void setId(K k);

    default boolean isNew(){
        return this.getId() == null;
    }
}
