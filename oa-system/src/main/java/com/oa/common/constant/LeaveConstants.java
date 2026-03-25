package com.oa.common.constant;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-01-07  10:01
 */
public class LeaveConstants {
    /**
     * 请假类型
     */
    public static final int LEAVE_TYPE_PERSONAL = 1;    // 事假
    public static final int LEAVE_TYPE_SICK = 2;        // 病假
    public static final int LEAVE_TYPE_ANNUAL = 3;      // 年假
    public static final int LEAVE_TYPE_ADJUST = 4;      // 调休

    /**
     * 请假状态
     */
    public static final int LEAVE_STATUS_PENDING = 0;   // 待审批
    public static final int LEAVE_STATUS_APPROVING = 1; // 审批中
    public static final int LEAVE_STATUS_APPROVED = 2;  // 已通过
    public static final int LEAVE_STATUS_REJECTED = 3;  // 已驳回
    public static final int LEAVE_STATUS_CANCELLED = 4; // 已取消

    /**
     * 请假时长单位
     */
    public static final String LEAVE_UNIT_HOUR = "hour";
    public static final String LEAVE_UNIT_DAY = "day";
}
