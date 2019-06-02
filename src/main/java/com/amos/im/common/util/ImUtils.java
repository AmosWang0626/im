package com.amos.im.common.util;

import com.amos.im.core.constant.ImConstant;

/**
 * PROJECT: Sales
 * DESCRIPTION: Im工具类
 *
 * @author amos
 * @date 2019/6/2
 */
public class ImUtils {

    /**
     * 手机号脱敏
     *
     * @param phoneNo 手机号
     * @return 脱敏手机号
     */
    private static String dstPhoneNo(String phoneNo) {
        if (phoneNo.length() == ImConstant.PHONE_NO_LENGTH) {
            return phoneNo.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }
        return phoneNo;
    }

}
