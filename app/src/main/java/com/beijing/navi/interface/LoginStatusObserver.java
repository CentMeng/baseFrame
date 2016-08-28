package com.luoteng.folk.interfaces;

/**
 * @author CentMeng csdn@vip.163.com on 15/12/15.
 * 登录状态观察者
 */
public interface LoginStatusObserver {

    public void refreshLoginStatus(boolean isLogin);
}
