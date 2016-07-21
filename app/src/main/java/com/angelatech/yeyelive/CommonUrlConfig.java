package com.angelatech.yeyelive;


import com.angelatech.yeyelive.application.App;

/**
 * 接口地址配置文件
 */
public class CommonUrlConfig {
    private static boolean isDebug = App.isDebug;
    private static String TEST_HOST_URL =  "http://liveapi.maracuya.live/"; //"http://videotest.vvago.com/";
    private static String HOST_URL = "http://liveapi.iamyeye.com/";
    public static final String OUT_IP = isDebug ? "121.199.29.67" : "dtlogin.iamyeye.com";
    public static final int OUT_PORT = isDebug ? 15302 : 13302;

    public static String shareURL = "http://share.iamyeye.com/";

    public static String channel = "api";

    /**
     * 登陆注册接口
     */
    //获取短信验证码
    public static String GetPhoneCode = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/user/GetPhoneCode";
    //注册&登陆（手机）
    public static String LoginSm = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/user/LoginSm";
    //微信注册接口
    public static String weixinRegister = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/user/LoginWx";
    //QQ注册接口
    public static String qqRegister = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/user/LoginQq";

    public static String facebookLogin = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/user/LoginFb";


    /**
     * 账户绑定
     */
    //检查手机号是否注册
    public static String CheckPhoneRegister = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/user/CheckPhoneRegister";
    //检查绑定
    public static String UserBindingCheck = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/user/UserBindingCheck";
    //绑定手机号
    public static String UserBindingAccounts = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/user/UserBindingAccounts";


    /**
     * 用户信息
     */
    //上传头像
    public static String PicUpload = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/Picture/PicUpload";
    //查看个人（他人）用户信息
    public static String UserInformation = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/user/UserInformation";
    //修改个人用户信息
    public static String UserInformationEdit = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/user/UserInformationEdit";
    //我的关注和粉丝
    public static String FriendMyList = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/user/FriendMyList";

    //  用户的关注和粉丝
    public static String FriendHeList = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/user/FriendHeList";
    //用户搜索
    public static String UserSearch = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/user/UserSearch";

    /**
     * 充值
     */
    //下单
    public static String RechargeWeixinOrder = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/Recharge/RechargeWeixinOrder";
    //加币
    public static String RechargeDiamondAdd = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/Recharge/RechargeDiamondAdd";
    //充值列表
    public static String RechargeConfigList = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/Recharge/RechargeConfigList";

    public static String GetUserDiamond = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/Recharge/RechargeGetUserDiams";

    /**
     * 首页
     */
    //热门
    public static String LiveVideoHot = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/LiveVideo/LiveVideoHot";
    //关注
    public static String LiveVideoFollow = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/LiveVideo/LiveVideoFollow";
    //房间详细列表

    /**
     * 房间
     */
    //礼物
    public static String PropList = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/LiveVideo/PropList";
    //开播信息
    public static String LiveVideoBroadcast = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/LiveVideo/LiveVideoBroadcast";
    //关注
    public static String UserFollow = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/user/UserFollow";
    //查询关注状态
    public static String UserIsFollow = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/user/UserIsFollow";

    //统计
    public static String PlatformIntoLogIns = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + channel + "/user/PlatformIntoLogIns";
    //用户通知编辑
    public static String UserNoticeEdit = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/User/UserNoticeEdit";

    public static String UserFeedback = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/user/FeedbackAdd";

    // 黑名单
    public static String FriendBlacklist = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/user/FriendBlacklist";
    // 用户拉黑
    public static String UserPullBlack = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/user/UserPullBlack";
    //举报
    public static String BarReport = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/user/BarReport";

    public static String Agreement = (isDebug ? TEST_HOST_URL : HOST_URL) + "/Web/ServiceProvision.html";

    public static String ExtensionList = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/LiveVideo/ExtensionList";

    //七牛
    public static String GetQiniuUpToken = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/Picture/GetQiniuUpToken";
    public static String PicQiniu = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/Picture/PicQiniu";

    //支付接口
    //下单
    public static String RechargeOrder = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/Recharge/RechargeOrder";
    //充值接口
    public static String UserBill = (isDebug ? TEST_HOST_URL : HOST_URL) + channel + "/Recharge/TransactionDetails";
}