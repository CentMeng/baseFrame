package com.core.api.entity;


/**
 * @author CentMeng csdn@vip.163.com on 16/3/4.
 */
public class MobileScreen extends BaseEntity {

    private String url;

    private boolean showSplashScreen;

    private String imageUrl;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public boolean isShowSplashScreen() {
        return showSplashScreen;
    }

    public void setShowSplashScreen(boolean showSplashScreen) {
        this.showSplashScreen = showSplashScreen;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
