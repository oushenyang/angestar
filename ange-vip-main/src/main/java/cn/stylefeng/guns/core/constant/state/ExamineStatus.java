package cn.stylefeng.guns.core.constant.state;

import lombok.Getter;

/**
 * 审核状态(代理审核记录表)
 *
 * @author shenyang.ou
 * @Date 2017年1月22日 下午12:14:59
 */
@Getter
public enum ExamineStatus {

    WAITING_DEVELOPER_REVIEW(1, "等待开发者审核"),
    WAITING_AGENT_REVIEW(2, "等待代理审核"),
    DEVELOPER_REFUSE(3, "开发者拒绝"),
    AGENT_REFUSE(4, "代理拒绝"),
    AGENT_SUCCESS(5, "代理成功");

    int code;
    String message;

    ExamineStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
