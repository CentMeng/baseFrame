package com.core.api.entity;

import android.media.Image;

import java.util.List;

/**
 * @author CentMeng csdn@vip.163.com on 15/12/30.
 */
public class Article extends BaseEntity {

    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;


    /**
     * url
     */
    private String url;

    /**
     * 发布日期
     */
    private long publishDate;

    /**
     * 作者
     */
    private String author;

    /**
     * 展示顺序
     */
    private Integer ordinal;

    /**
     * 封面URI
     */
    private String cover;

    /**
     * 额外信息
     *
     * @return
     */
    private String priv;



    /**
     * 初始url
     */
    private String initialURL;

    /**
     * 最终生成url
     */
    private String generateUrl;

    private String generateContent;

    private List<String> tag;

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public String getGenerateContent() {
        return generateContent;
    }

    public void setGenerateContent(String generateContent) {
        this.generateContent = generateContent;
    }

    /**
     * 是否已向用户推送通知
     */
    private boolean push;

    private String topCover;

    private String sharedCover;

    public String getSharedCover() {
        return sharedCover;
    }

    public void setSharedCover(String sharedCover) {
        this.sharedCover = sharedCover;
    }

    /**
     * 频道
     */
    private String channel;

    private String summary;

    private List<Image> images;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTopCover() {
        return topCover;
    }

    public void setTopCover(String topCover) {
        this.topCover = topCover;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(long publishDate) {
        this.publishDate = publishDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPriv() {
        return priv;
    }

    public void setPriv(String priv) {
        this.priv = priv;
    }

    public String getInitialURL() {
        return initialURL;
    }

    public void setInitialURL(String initialURL) {
        this.initialURL = initialURL;
    }

    public String getGenerateUrl() {
        return generateUrl;
    }

    public void setGenerateUrl(String generateUrl) {
        this.generateUrl = generateUrl;
    }

    public boolean isPush() {
        return push;
    }

    public void setPush(boolean push) {
        this.push = push;
    }
}
