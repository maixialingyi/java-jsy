package com.jsy.learn.gc.oom.MetaSpaceTest;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 *
 */
public class MetaSpaceOOM {

    public static void main(String[] args) {
        for(;;){
            User user = new User().setId("123").setName("1231");

            MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
            // 此处会一直创建新代理类,会导致元空间溢出
            mapperFactory.classMap(User.class, UserVo.class)
                    .field("id", "userId")
                    .field("name", "userName")
                    .byDefault().register();
            UserVo userVo = mapperFactory.getMapperFacade().map(user, UserVo.class);
        }
    }
}
