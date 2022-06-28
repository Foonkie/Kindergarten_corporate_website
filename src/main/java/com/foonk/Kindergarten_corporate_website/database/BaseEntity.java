package com.foonk.Kindergarten_corporate_website.database;

import java.io.Serializable;

public interface BaseEntity <T extends Serializable>{
    T getId();
    void setId(T id);
}
