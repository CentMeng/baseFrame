/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.api.entity;

import android.text.TextUtils;

import com.core.api.entity.enums.Gender;

import java.net.URLDecoder;
import java.util.Collection;

/**
 * @author CentMeng csdn@vip.163.com
 */
public class User extends BaseEntity {

    private String id;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 真实姓名
     */
    private String name;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 性别
     */
    private Gender gender;

    /**
     * 手势密码
     */
    private String gesture;

    /**
     * 用户状态: true(启用) | false(禁用)
     */
    private boolean enabled;

    /**
     * 最后一次登录时间
     */
    private long lastLoginDate;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 行业细分
     */
    private String industrySegment;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 职位名称
     */
    private String position;

    /**
     * 是否是行家
     */
    private boolean expert;

    /**
     * 昵称
     */
    private String loginName;


    /**
     * 个人偏好
     */
    private Collection<String> preference;

    /**
     * 个人介绍
     */
    private String introduction;

    private String permanentCity;

    private String permanentArea;

    private boolean enableAnswer;


    public String getPermanentCity() {
        if (TextUtils.isEmpty(permanentCity)) {
            return "";
        }
        return permanentCity;
    }

    public void setPermanentCity(String permanentCity) {
        this.permanentCity = permanentCity;
    }

    public String getPermanentArea() {

        if (TextUtils.isEmpty(permanentArea)) {
            return "";
        }
        return permanentArea;
    }

    public void setPermanentArea(String permanentArea) {
        this.permanentArea = permanentArea;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getMobile() {

        if (mobile == null) {
            return "";
        } else {
            return mobile;
        }
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        if (name == null) {
            return "";
        } else {
            return name;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        if (idCard == null) {
            return "";
        } else {
            return idCard;
        }
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        if(gender == null){
            return Gender.SECRET;
        }
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getGesture() {
        return gesture;
    }

    public void setGesture(String gesture) {
        this.gesture = gesture;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(long lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getAvatar() {
        if (TextUtils.isEmpty(avatar)) {
            return "";
        } else {
            return avatar;
        }
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;

    }

    public String getIndustrySegment() {
        return industrySegment;
    }

    public void setIndustrySegment(String industrySegment) {
        this.industrySegment = industrySegment;
    }

    public String getOrgName() {
        if (orgName == null) {
            orgName = "";
        }
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isExpert() {
        return expert;
    }

    public void setExpert(boolean expert) {
        this.expert = expert;
    }

    public String getLoginName() {
        if (!TextUtils.isEmpty(loginName)) {
            String loginName_ = loginName;
            try {
                loginName = URLDecoder.decode(loginName_, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Collection<String> getPreference() {
        return preference;
    }

    public void setPreference(Collection<String> preference) {
        this.preference = preference;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
