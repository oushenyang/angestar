package cn.stylefeng.guns.core.constant.type;

import lombok.Getter;

/**
 * 购卡类型
 *
 * @author shenyang.ou
 * @Date 2017年1月22日 下午12:14:59
 */
@Getter
public enum BuyCardType {
    PRIMARY_AGENT_RECHARGE(1, "一级代理充值", "一级代理充值金额{}"),
    PRIMARY_AGENT_CARD_PURCHASING(2, "一级代理购卡", "一级代理生成{}张{}，单价{}，扣除金额{}"),
    SUBORDINATE_AGENT_RECHARGE(3, "下级代理充值", "{}级代理充值金额{}，上级代理扣款{}"),
    SUBORDINATE_AGENT_CARD_REBATE(4, "下级代理购卡返差价", "{}级代理生成{}张{}，单价{}，上级代理单价{}，返给上级代理差价金额{}"),
    PRIMARY_AGENT_DEDUCT(5, "一级代理扣除", "一级代理扣除金额{}"),
    SUBORDINATE_AGENT_DEDUCT(6, "下级代理扣除", "{}级代理扣除金额{}，上级代理充值{}");

    int code;
    String message;
    String detailed;

    BuyCardType(int code, String message, String detailed) {
        this.code = code;
        this.message = message;
        this.detailed = detailed;
    }
}
