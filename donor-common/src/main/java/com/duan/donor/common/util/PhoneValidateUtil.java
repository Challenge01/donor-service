package com.duan.donor.common.util;

import java.util.regex.Pattern;

/**
 *手机号的验证
 */
public class PhoneValidateUtil {

    /**
     * 字母开头
     */
    public static final String REGEX_LETTER_START = "^[a-zA-z].*";

    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z][a-zA-Z0-9]{5,11}$";

    /**
     * 正则表达式：验证密码
     * Minimum eight characters, at least one letter and one number:
     * "^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$"
     * Minimum eight characters, at least one letter, one number and one special character:
     * "^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$"
     * Minimum eight characters, at least one uppercase letter, one lowercase letter and one number:
     * "^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$"
     * Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character:
     * "^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{8,}"
     * Minimum eight and maximum 10 characters, at least one uppercase letter, one lowercase letter, one number and one special character:
     * "^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{8,10}"
     */
    public static final String REGEX_PASSWORD = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{6,16}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^1\\d{10}$";
    /**
     * 正则表达式：验证港澳通行证 或者 台湾通行证
     */
    public static final String REGEX_PASS = "^[a-zA-Z][0-9]{6,10}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

    /**
     * 正则表达式：验证姓名的正则
     */
    public static final String REGEX_CHINESE = "^(([\u4000-\u9fa5a-zA-Z0-9]{2,16}))$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";


    /**
     * 正则表达式:验证空格
     */
    public static final String REGEX_BLANK = "^[^\\s]*＄";

    /**
     * 正则表达式:是否全为数字
     */
    public static final String REGEX_MATH = "[0-9]{1,}";


    /**
     * 正则表达式:验证是否是输入的1-2
     */
    public static final String REGEX_MATH_PHRATYPE = "^[1-2]$";

    /**
     * 正则表达式:验证是否是输入的合法年份
     */
    public static final String REGEX_YEAR = "^(200|201|202)\\d{1}$";

    /**
     * 正则表达式:验证是否是输入的合法年级
     */
    public static final String REGEX_GRADE = "^[1-9]$";

    public static boolean isLetterStart(String str) {
        return Pattern.matches(REGEX_LETTER_START, str);
    }

    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }
    /**
     * 校验手机号
     *
     * @param card
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isSpecialRegionIdCard(String card) {
        return Pattern.matches(REGEX_PASS, card);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }


    /**
     * 校验是否存在空格
     *
     * @param nick
     * @return
     */
    public static boolean hasBlank(String nick) {
        return Pattern.matches(REGEX_BLANK, nick);
    }

    /**
     * 校验是否全为数字
     */
    public static boolean isNum(String num) {
        return Pattern.matches(REGEX_MATH, num);
    }

    /**
     * 校验是否输入的是学段
     */
    public static boolean isPharType(String num) {
        return Pattern.matches(REGEX_MATH_PHRATYPE, num);
    }

    /**
     * 校验是否输入的是合法年份
     */
    public static boolean isYear(String num) {
        return Pattern.matches(REGEX_YEAR, num);
    }

    /**
     * 验证输入的姓名是否合法
     */
    public static boolean isName(String name) {
        return Pattern.matches(REGEX_CHINESE, name);
    }

    public static void main(String[] args) {
        System.out.println(isName("王䜣宜"));
    }

    /**
     * 验证输入的姓名是否合法
     */
    public static boolean isGrade(String grade) {
        return Pattern.matches(REGEX_GRADE, grade);
    }

}