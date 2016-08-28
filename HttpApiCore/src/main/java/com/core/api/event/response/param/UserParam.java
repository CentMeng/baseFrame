package com.core.api.event.response.param;

import com.core.api.entity.BaseEntity;
import com.core.api.entity.User;

/**
 * Created by suetming on 15-9-16.
 */
public class UserParam extends BaseEntity {

    public String token;

    public User user;

    public String newSocialId;

    public boolean certMobile;

}
