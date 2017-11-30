package com.it2go.framework.entities;

import java.beans.Transient;
import java.io.Serializable;

public interface IAbstractEntity<K> extends Serializable {

    K getId();

    void setId(K k);

    @Transient // json should ignore this method
    default boolean isNew(){
        return this.getId() == null;
    }
}
