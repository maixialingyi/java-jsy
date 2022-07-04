package com.jsy.learn.gc.MetaSpaceTest;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {
    private String id;
    private String name;
}
