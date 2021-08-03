package cn.stylefeng.guns.sys.core.auth.filter;

/**
 * 不需要身份验证的资源
 *
 * @author fengshuonan
 * @Date 2020/3/1 16:19
 */
public class NoneAuthedResources {

    /**
     * 前端接口资源
     */
    public static final String[] FRONTEND_RESOURCES = {
            "/assets/**",
            "/favicon.ico",
            "/activiti-editor/**"
    };


    /**
     * 不要权限校验的后端接口资源
     * <p>
     * ANT风格的接口正则表达式：
     * <p>
     * ? 匹配任何单字符<br/>
     * * 匹配0或者任意数量的 字符<br/>
     * ** 匹配0或者更多的 目录<br/>
     */
    public static final String[] BACKEND_RESOURCES = {

            //主页
            "/",

            // 锁屏
            "/system/lock",

            //获取验证码
            "/kaptcha",

            //rest方式获取token入口
            "/rest/login",

            //oauth登录的接口
            "/oauth/render/*",
            "/oauth/callback/*",

            //单点登录接口
            "/ssoLogin",
            "/sysTokenLogin",

            // 登录接口放开过滤
            "/login",

            // 卡类价格接口放开过滤
            "/cardInfo/addPriceEdit",
            "/codeCardType/editItem",
            "/codeCardType/detail",

            // 代理注册接口放开过滤
            "/agentRegister/**",

            // session登录失效之后的跳转
            "/global/sessionError",

            // 图片预览 头像
            "/system/preview/*",

            // 错误页面的接口
            "/error",
            "/global/error",

            // 测试多数据源的接口，可以去掉
            "/tran/**",

            //获取租户列表的接口
            "/tenantInfo/listTenants",

            //获取应用信息
            "/getAppInfo/*",

            //取在线人数
            "/getOnlineNum/*",

            //检查版本更新
            "/checkUpdate/*",

            //单码登录
            "/cardLogin/*",

            //检测单码用户状态
            "/checkCardStatus/*",

            //获取单码用户信息
            "/getCardInfo/*",

            //设置单码数据
            "/setCardData/*",

            //获取单码数据
            "/getCardData/*",

            //卡密解绑
            "/cardUnbind/*",

            //快捷页面
            "/quick/**",

            //获取远程数据
            "/getRemoteData/**",
            "/getTrial/*",
            "/urule/frame",
            "/api/**",
            "/new/api/**",
            "/shenqi/**",
            "/web/api/**",
            "/dict/*",
            "/dic*",
            "/dictType/**",
            "/dictType/*",
            "/dictType",
            "/sysConfig/**",
            "/sysConfig/*",
            "/sysConfig",
            "/appPower/**",
            "/appPower/*",
            "/appPower",

            //用户登录
            "/userLogin/*"

    };

}
