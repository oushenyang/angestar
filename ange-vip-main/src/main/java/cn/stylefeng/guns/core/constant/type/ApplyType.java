package cn.stylefeng.guns.core.constant.type;

import lombok.Getter;

/**
 * 申请类型(代理审核记录表)
 *
 * @author shenyang.ou
 * @Date 2017年1月22日 下午12:14:59
 */
@Getter
public enum ApplyType {

    APPLY_AGENT(1, "申请代理"),
    INVITE_AGENT(2, "邀请代理");

    int code;
    String message;

    ApplyType(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
