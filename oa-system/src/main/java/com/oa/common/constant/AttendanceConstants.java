package com.oa.common.constant;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-01-07  10:00
 */
public class AttendanceConstants {
    /**
     * 考勤状态
     */
    public static final int ATT_STATUS_NORMAL = 0;    // 正常
    public static final int ATT_STATUS_LATE = 1;      // 迟到
    public static final int ATT_STATUS_LEAVE_EARLY = 2; // 早退
    public static final int ATT_STATUS_ABSENT = 3;    // 旷工
    public static final int ATT_STATUS_LEAVE = 4;     // 请假

    /**
     * 考勤时间设置（单位：小时）
     */
    public static final double WORK_START_HOUR = 9.0;   // 上班时间 9:00
    public static final double WORK_END_HOUR = 18.0;    // 下班时间 18:00
    public static final double LATE_THRESHOLD = 0.5;    // 迟到阈值 0.5小时
    public static final double LEAVE_EARLY_THRESHOLD = 0.5; // 早退阈值 0.5小时

    /**
     * 工作日设置（1-7对应周一到周日）
     */
    public static final int[] WORK_DAYS = {1, 2, 3, 4, 5}; // 周一到周五
}
