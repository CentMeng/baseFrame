package com.core.api;

/**
 * @author mengxc ( csdn@vip.163.com )
 *         <p/>
 *         2014-12-22 上午10:51:16
 */

public abstract class ApiSettings {

    public final static String AGREE_URL ="http://7xl9qr.com1.z0.glb.clouddn.com/zhidian_agreement.html";

    public final static String HUANXIN_APPOINTID = "appointId";

    public final static String HUANXIN_AVATAR = "sendAvatar";

    public final static String HUANXIN_NAME = "sendName";

    public final static String VERSION_CODE = ""+BuildConfig.VERSION_CODE;

    public final static String OSTYPE = "ANDROID";

    /**
     * 基本url
     */
    public static String URL_BASE = BuildConfig.URL_BASE;

//    public static String URL_BASE = "http://zhid58.com:8080/api";

    /**
     * 分享点师和话题
     */
    public final static String URL_SHARE = BuildConfig.URL_SHARE;



//    public final static String URL_SHARE = "http://zhid58.com";

    /**
     * 分享红包链接
     */
    public final static String URL_SHARE_COUPON = BuildConfig.URL_SHARE_COUPON;

//    public final static String URL_SHARE_COUPON = "http://zhid58.com/coupon/hongbao/?pid=";

    /**
     * 分享爆料链接
     */
    public final static String URL_SHARE_DISCLOSE = BuildConfig.URL_SHARE_DISCLOSE;

    /**
     * 分享动态链接
     */
    public final static String URL_SHARE_ARTICLE = BuildConfig.URL_SHARE_ARTICLE;

    /**
     * 分享活动
     */
    public final static String URL_SHARE_ACTIVITY = BuildConfig.URL_SHARE_ACTIVITY;

    /**
     * 分享学校
     */
    public final static String URL_SHARE_COLLEGE = BuildConfig.URL_SHARE_SCHOOL;


    /**
     * 分享app
     */

    public final static String URL_SHARE_APP = BuildConfig.URL_SHARE_APP;


    public static boolean debug = BuildConfig.DEBUG;

    public final static String ZHIDIAN_URL = "http://www.zhid58.com";


    /**
     * 上传图片
     */
    public final static String URL_IMAGE_BASE = "http://statics.zhid58.com/";

    /**
     * 上传视频
     */
    public final static String URL_VIDEO_BASE = "http://video.zhid58.com/";

    /**
     * 优惠券使用说明
     */
    public final static String URL_COUPOU_INTRODUCTION = "http://zhid58.com/html/coupon_introduction.html";

    /**
     * 益答服务协议
     */
    public final static String URL_ANSWER_PROTOCOL = "http://www.zhid58.com/html/yd_protocol.html";

    /**
     * 七牛图片截取
     */
    public final static String QINIU_CUTPHOTO = "?vframe/png/offset/1/w/500/h/333";

    /**
     * 七牛获取视频元信息
     */
    public final static String QINNIU_VIDEOINFO = "?avinfo";

    /**
     * 七牛添加视频水印  aHR0cDovLzd4bmllaC5jb20xLnowLmdsYi5jbG91ZGRuLmNvbS93YXRlcm1hcmsucG5n  为水印图片Base64加密地址
     * aHR0cDovLzd4bmllaC5jb20xLnowLmdsYi5jbG91ZGRuLmNvbS9zbWFsbF93YXRlcm1hcmsucG5n  小图片
     * NorthWest     |     North      |     NorthEast
     * |                |
     * |                |
     * --------------+----------------+--------------
     * |                |
     * West          |     Center     |          East
     * |                |
     * --------------+----------------+--------------
     * |                |
     * |                |
     * SouthWest     |     South      |     SouthEast
     */
    public final static String QINIU_WATERMARK = "?avthumb/1/wmImage/aHR0cDovLzd4bmllaC5jb20xLnowLmdsYi5jbG91ZGRuLmNvbS9zbWFsbF93YXRlcm1hcmsucG5n/wmGravity/SouthEast";

    /**
     * 七牛图片水印  dissolve 透明度
     */
    public final static String QINIU_PICTUREWATERMARK = "?imageView/2/w/500/333|watermark/2/text/5oyH54K5/font/5a6L5L2T/fontsize/640/fill/d2hpdGU=/gravity/SouthWest/dissolve/100/dx/1";

    public final static String DISCLOSE_IMAGEURL = "http://7xqxpm.com1.z0.glb.clouddn.com/headline_128.png";

    /**
     * 七牛图片截取
     */
    public final static String QINIU_SMALL_CUTPHOTO = "?imageView/2/w/100/h/100";

    /**
     * 七牛图片截取
     */
    public final static String QINIU_CUTPHOTO_BYSIZE = "%1$s?imageView/2/w/%2$d/h/%3$d";

    public final static String QINIU_CUTPHOTO_BYSIZE_W = "%1$s?imageView/2/w/%2$d";

    /**
     * 七牛按质量缩小
     */
    public final static String QINIU_CUTPHOTO_BYQUALITY = "%1$s?imageView2/2/q/%2$d";

}
