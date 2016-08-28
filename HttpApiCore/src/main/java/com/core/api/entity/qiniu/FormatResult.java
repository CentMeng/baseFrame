package com.core.api.entity.qiniu;

/**
 * @author CentMeng csdn@vip.163.com on 16/4/18.
 */
public class FormatResult {

    private String persistentId;

    private String error;

    public String getPersistentId() {
        return persistentId;
    }

    public void setPersistentId(String persistentId) {
        this.persistentId = persistentId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
