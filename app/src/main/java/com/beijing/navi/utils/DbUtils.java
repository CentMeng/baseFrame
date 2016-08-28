package com.beijing.navi.utils;

/**
 * @author CentMeng csdn@vip.163.com on 16/1/7.
 * SQLPal 操作
 */
public class DbUtils {

//    public static void saveEaseAccount(User user, String appointId) {
//        if (user != null && user.getEasemobAccount() != null) {
//            VoipAccount account = user.getEasemobAccount();
//            account.setAvatar(user.getAvatar());
//            account.setNickName(TransUtils.getName(user));
//            account.setAppointId(appointId);
//            account.saveFast();
//            LogUtils.e(LogConstant.DB_OPERA, "easeAccount添加");
//        }
//    }
//
//    public static void updateEaseAccount(User user, String appointId) {
//        if (user != null && user.getEasemobAccount() != null) {
//            VoipAccount account = user.getEasemobAccount();
//            account.setAvatar(user.getAvatar());
//            account.setNickName(TransUtils.getName(user));
//            if (!TextUtils.isEmpty(appointId)) {
//                account.setAppointId(appointId);
//            }
//            if (account.updateAll("account = ?", account.getAccount()) <= 0) {
//                account.saveFast();
//                LogUtils.e(LogConstant.DB_OPERA, "easeAccount更新不存在,添加");
//            } else {
//                LogUtils.e(LogConstant.DB_OPERA, "easeAccount更新存在,更新");
//            }
//        }
//    }
//
//    public static void deleteEaseAccount(User user) {
//        if (user != null && user.getEasemobAccount() != null) {
//            int delete = DataSupport.deleteAll(VoipAccount.class, "account = ?", user.getEasemobAccount().getAccount());
//            LogUtils.e(LogConstant.DB_OPERA, "easeAccount删除" + delete);
//        }
//    }
//
//    public static void deleteEaseAccount(String userName) {
//        int delete = DataSupport.deleteAll(VoipAccount.class, "account = ?", userName);
//        LogUtils.e(LogConstant.DB_OPERA, "easeAccount删除" + delete);
//    }
//
//    public static void deleteEaseAccountByAppoint(String appointId) {
//        int delete = DataSupport.deleteAll(VoipAccount.class, "appointId = ?", appointId);
//        LogUtils.e(LogConstant.DB_OPERA, "easeAccount删除通过appoint" + delete);
//    }
//
//
//    public static VoipAccount findAccount(String account) {
//        List<VoipAccount> list = DataSupport.where("account = ?", account).limit(1).find(VoipAccount.class);
//        if (list.size() > 0) {
//            LogUtils.e(LogConstant.DB_OPERA, "easeAccount已查询到");
//            return list.get(0);
//        }
//        LogUtils.e(LogConstant.DB_OPERA, "easeAccount未查询到");
//        return null;
//    }
//
//    public static VoipAccount findAccountByAppoint(String appointId) {
//        List<VoipAccount> list = DataSupport.where("appointId = ?", appointId).limit(1).find(VoipAccount.class);
//        if (list.size() > 0) {
//            LogUtils.e(LogConstant.DB_OPERA, "easeAccount已查询到通过appoint");
//            return list.get(0);
//        }
//        LogUtils.e(LogConstant.DB_OPERA, "easeAccount未查询到");
//        return null;
//    }
//
//    public static void updateDiscloseAccount(String avatar, String nickName, String commentId) {
//        DiscloseAccount account = new DiscloseAccount();
//        account.setAvatar(avatar);
//        account.setNickName(nickName);
//        account.setCommentId(commentId);
//        if (account.updateAll("commentId = ?", commentId) <= 0) {
//            account.saveFast();
//            LogUtils.e(LogConstant.DB_OPERA, "Disclose更新不存在,添加");
//        } else {
//            LogUtils.e(LogConstant.DB_OPERA, "Disclose更新存在,更新");
//        }
//    }
//
//    public static DiscloseAccount findDiscloseAccount(String commentId) {
//        List<DiscloseAccount> list = DataSupport.where("commentId = ?", commentId).limit(1).find(DiscloseAccount.class);
//        if (list.size() > 0) {
//            LogUtils.e(LogConstant.DB_OPERA, "DiscloseAccount已查询到");
//            return list.get(0);
//        }
//        LogUtils.e(LogConstant.DB_OPERA, "DiscloseAccount未查询到");
//        return null;
//    }
//
//
//    public static void saveCollege(List<CollegeDb> colleges) {
//
//        if (!ObjectUtils.listIsNull(colleges)) {
//            DataSupport.saveAll(colleges);
//            LogUtils.e(LogConstant.DB_OPERA, "学校添加完毕");
//        }
//    }
//
//    /**
//     * 根据国家名获取大学
//     *
//     * @return
//     */
//    public static List<CollegeDb> getCollegeByCountry(String country) {
//        LogUtils.e(LogConstant.OPERA_TIME, "国家查找开始时间" + System.currentTimeMillis());
//        List<CollegeDb> list = DataSupport.where("country = ?", country).find(CollegeDb.class);
//        if (!ObjectUtils.listIsNull(list)) {
//            LogUtils.e(LogConstant.DB_OPERA, "DiscloseAccount已查询到");
//            LogUtils.e(LogConstant.OPERA_TIME, "国家查找结束时间" + System.currentTimeMillis());
//            return list;
//        }
//        return null;
//    }
//
//    /**
//     * 根据国家名和首字母获取大学
//     *
//     * @return
//     */
//    public static List<CollegeDb> getCollegeByCountryAndFirst(String country, String firstAlpha) {
//        LogUtils.e(LogConstant.OPERA_TIME, "通过国家查找学校开始时间" + System.currentTimeMillis());
//        List<CollegeDb> list = DataSupport.where("country = ? and pinyinName like ?", country, firstAlpha + "%").find(CollegeDb.class);
//        if (!ObjectUtils.listIsNull(list)) {
//            LogUtils.e(LogConstant.DB_OPERA, "DiscloseAccount已查询到");
//            LogUtils.e(LogConstant.OPERA_TIME, "通过国家查找学校结束时间" + System.currentTimeMillis());
//            return list;
//        }
//        return null;
//    }
//
//    /**
//     * 搜索返回，根据拼音或者汉子
//     *
//     * @param key
//     * @return
//     */
//    public static List<CollegeDb> searchCollege(String key) {
//        LogUtils.e(LogConstant.OPERA_TIME, "搜索学校开始时间" + System.currentTimeMillis());
//        List<CollegeDb> list = DataSupport.where("name like ? or pinyinName like ? or englishName  like ?", "%" + key + "%", "%" + key + "%", "%" + key + "%").find(CollegeDb.class);
//        if (!ObjectUtils.listIsNull(list)) {
//            LogUtils.e(LogConstant.DB_OPERA, "DiscloseAccount已查询到");
//            LogUtils.e(LogConstant.OPERA_TIME, "搜索学校结束时间" + System.currentTimeMillis());
//            return list;
//        }
//        return null;
//    }
//
//    public static int getCollegeSize() {
//        return DataSupport.count(CollegeDb.class);
//    }
//
//    public static void deleteCollege() {
//        DataSupport.deleteAll(CollegeDb.class, "");
//    }
//
//    /**
//     * 添加或更新Article数据
//     *
//     * @param article
//     * @param userId
//     */
//    public static void updateArticle(Jpush article, String userId) {
//        if (article != null && !StringUtils.isBlank(article.getArticleId()) && !StringUtils.isBlank(userId)) {
//            article.setUserId(userId);
//            if (article.updateAll("articleId = ? and userId = ?", article.getAppointment(), article.getUserId()) <= 0) {
//                article.saveFast();
//                LogUtils.e(LogConstant.DB_OPERA, "article更新不存在,添加" + userId + " " + article.getArticleId());
//            } else {
//                LogUtils.e(LogConstant.DB_OPERA, "article更新存在,更新" + userId + " " + article.getArticleId());
//            }
//        }
//    }
//
//    /**
//     * 返回消息数据
//     *
//     * @param userId
//     * @param page
//     * @param pageSize
//     * @return
//     */
//    public static List<Jpush> getArticles(String userId, int page, int pageSize) {
//        List<Jpush> list = DataSupport.where("userId = ?", userId).limit(pageSize).offset(page * pageSize).order("id desc").find(Jpush.class);
//        if (!ObjectUtils.listIsNull(list)) {
//            LogUtils.e(LogConstant.DB_OPERA, "Article已查询到" + userId);
//            return list;
//        }
//        return null;
//    }
//
//    /**
//     * 返回消息未读数
//     *
//     * @param userId
//     * @return
//     */
//    public static int getArticleUnreadCount(String userId) {
//        return DataSupport.where("userId = ? and read = 0", userId).count(Jpush.class);
//    }
//
//    /**
//     * 获取最新一条消息
//     *
//     * @param userId
//     * @return
//     */
//    public static Jpush getLastArticle(String userId) {
//        List<Jpush> list = getArticles(userId, 0, 1);
//        if (ObjectUtils.listIsNull(list)) {
//            return null;
//        }
//        return list.get(0);
//    }
//
//    /**
//     * 消息全部标记已读
//     *
//     * @param userId
//     * @return
//     */
//    public static int setReaded(String userId) {
//        ContentValues values = new ContentValues();
//        values.put("read", true);
//        int count = DataSupport.updateAll(Jpush.class, values, "userId = ? and read = 0", userId);
//        if (count <= 0) {
//            LogUtils.e(LogConstant.DB_OPERA, "article没有未读" + userId);
//        } else {
//            LogUtils.e(LogConstant.DB_OPERA, "article未读更新完毕" + count + " " + userId);
//        }
//        return count;
//    }
//
//    /**
//     * 消息部分标记已读
//     *
//     * @param userId
//     * @return
//     */
//    public static int setReaded(String userId, String articleId) {
//        ContentValues values = new ContentValues();
//        values.put("read", true);
//        int count = DataSupport.updateAll(Jpush.class, values, "userId = ? and articleId = ? and read = 0", userId, articleId);
//        if (count <= 0) {
//            LogUtils.e(LogConstant.DB_OPERA, "article单个没有未读" + userId);
//        } else {
//            LogUtils.e(LogConstant.DB_OPERA, "article单个未读更新完毕" + count + " " + userId);
//        }
//        return count;
//    }
//
//
//    /**
//     * 增加催单次数
//     *
//     * @param appointId
//     */
//    public static void addCount(String appointId) {
//        if (!TextUtils.isEmpty(appointId)) {
//            QuickUp quickUp = getQuickUp(appointId);
//            if (quickUp != null) {
//                quickUp.setCount(quickUp.getCount() + 1);
//                quickUp.setLasttime(System.currentTimeMillis());
//                quickUp.updateAll("appointId = ?", appointId);
//                LogUtils.e(LogConstant.DB_OPERA, "催单信息,次数" + quickUp.getCount());
//            } else {
//                quickUp = new QuickUp();
//                quickUp.setAppointId(appointId);
//                quickUp.setCount(1);
//                quickUp.setLasttime(System.currentTimeMillis());
//                quickUp.saveFast();
//                LogUtils.e(LogConstant.DB_OPERA, "新生成催单信息,次数" + quickUp.getCount());
//            }
//        }
//    }
//
//    /**
//     * 获取催单信息
//     *
//     * @param appointId
//     * @return
//     */
//    public static QuickUp getQuickUp(String appointId) {
//        List<QuickUp> quickUps = DataSupport.where("appointId = ?", appointId).find(QuickUp.class);
//        if (!ObjectUtils.listIsNull(quickUps)) {
//            LogUtils.e(LogConstant.DB_OPERA, "获取催单信息");
//            return quickUps.get(0);
//        }
//        LogUtils.e(LogConstant.DB_OPERA, "获取催单信息为空");
//        return null;
//    }
//
//    /**
//     * 删除催单信息
//     *
//     * @param appointId
//     */
//    public static void deleteQuickUp(String appointId) {
//        if (TextUtils.isEmpty(appointId)) {
//            int delete = DataSupport.deleteAll(QuickUp.class, "appointId = ?", appointId);
//            LogUtils.e(LogConstant.DB_OPERA, "催单删除" + delete);
//        }
//    }
//
//    /**
//     * 保存语音信息
//     *
//     * @param entity
//     */
//    public static void updateMediaEntity(MediaEntity entity) {
//        if (entity != null) {
//            if (TextUtils.isEmpty(entity.getUrl()) || entity.updateAll("url = ?", entity.getUrl()) <= 0) {
//                entity.saveFast();
//                LogUtils.e(LogConstant.DB_OPERA, "语音信息更新不存在,添加");
//            } else {
//                LogUtils.e(LogConstant.DB_OPERA, "语音信息更新存在,更新");
//            }
//        }
//    }
//
//
//    /**
//     * 获取语音信息
//     *
//     * @param url 网络路径
//     * @return
//     */
//    public static MediaEntity getMediaEntity(String url) {
//        if(!TextUtils.isEmpty(url)){
//            List<MediaEntity> medias = DataSupport.where("url = ?", url).find(MediaEntity.class);
//            if (!ObjectUtils.listIsNull(medias)) {
//                LogUtils.e(LogConstant.DB_OPERA, "获取语音信息");
//                return medias.get(0);
//            }
//        }
//        LogUtils.e(LogConstant.DB_OPERA, "获取语音信息为空");
//        return null;
//    }
//
//
//    /**
//     * 获取语音信息
//     *
//     * @param url 网络路径
//     * @return
//     */
//    public static String getLocalPath(String url) {
//        MediaEntity media = getMediaEntity(url);
//        if (media != null) {
//            return media.getPath();
//        }
//        return null;
//    }
//
//    /**
//     * 删除全部语音信息
//     */
//    public static void deleteQuickUp() {
//        int delete = DataSupport.deleteAll(MediaEntity.class,"downloadStatus = ?", DownloadStatus.LOADED.name());
//        LogUtils.e(LogConstant.DB_OPERA, "语音信息删除" + delete);
//    }

}
