package com.core.api.entity;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * @author CentMeng csdn@vip.163.com on 15/9/17.
 */
public abstract class BaseEntity extends DataSupport implements Serializable {

    static final long serialVersionUID = 20130514L;

    @Override
    public String toString() {
        return "";
    }
}
