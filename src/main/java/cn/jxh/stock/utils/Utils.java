package cn.jxh.stock.utils;

import java.security.MessageDigest;
import java.util.UUID;

public class Utils {

    /**
     * 生成一个UUID
     *
     * @return UUID
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    /**
     * @param str      被转化字符串
     * @param defValue 转化失败后的默认值
     * @return
     */
    public static int parseInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * @param str      被转化字符串
     * @param defValue 转化失败后的默认值
     * @return
     */
    public static Double parseDouble(String str, Double defValue) {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * @param str      被转化字符串
     * @param defValue 转化失败后的默认值
     * @return
     */
    public static Long parseLong(String str, Long defValue) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean strIsNull(String str) {
        return ((str == null) || "".equals(str));
    }

    /**
     * 去空格，如为null则转化为空字符串
     *
     * @param str
     * @return
     */
    public static String trim(String str) {
        if (str == null) {
            return "";
        }
        return str.trim();
    }

    public static String trim(Object obj) {
        if (obj == null) {
            return "";
        } else {
            return trim(obj.toString());
        }
    }

    /**
     * MD5加密
     *
     * @param str
     * @return
     */
    public static String md5(String str) {
        StringBuffer sb = new StringBuffer(32);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(str.getBytes("utf-8"));

            for (int i = 0; i < array.length; i++) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).toUpperCase().substring(1, 3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void main(String[] args) {

    }

}
