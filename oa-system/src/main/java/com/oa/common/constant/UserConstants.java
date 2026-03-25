package com.oa.common.constant;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-01-07  9:58
 */
public class UserConstants {
    /**
     * 用户名长度限制
     */
    public static final int USERNAME_MIN_LENGTH = 2;
    public static final int USERNAME_MAX_LENGTH = 20;

    /**
     * 密码长度限制
     */
    public static final int PASSWORD_MIN_LENGTH = 5;
    public static final int PASSWORD_MAX_LENGTH = 20;

    /**
     * 用户类型
     */
    public static final String USER_TYPE_ADMIN = "00";
    public static final String USER_TYPE_NORMAL = "01";

    /**
     * 性别
     */
    public static final int GENDER_UNKNOWN = 0;
    public static final int GENDER_MALE = 1;
    public static final int GENDER_FEMALE = 2;

    /**
     * 用户状态
     */
    public static final int USER_STATUS_ENABLED = 1;
    public static final int USER_STATUS_DISABLED = 0;
}
