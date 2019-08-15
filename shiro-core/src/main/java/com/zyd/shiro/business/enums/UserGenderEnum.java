/**
 * MIT License
 * <p>
 * Copyright (c) 2018 yadong.zhang
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.zyd.shiro.business.enums;

/**
 * @project: springboot-shiro
 * @description: 用户性别信息
 * @date: 2019-08-15 10:05 AM
 * @version: 1.0
 * @website: https://yubuntu0109.github.io/
 */
public enum UserGenderEnum {
    MALE(1, "男"), FEMALE(0, "女"), UNKNOW(-1, "");
    private int code;
    private String desc;

    UserGenderEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * @description: (never used)通过code码获取用户的性别信息
     * @param: code
     * @date: 2019-08-15 10:05 AM
     * @return: com.zyd.shiro.business.enums.UserGenderEnum
     */
    public static UserGenderEnum getUserSex(Integer code) {
        if (code == null) {
            return UNKNOW;
        }
        for (UserGenderEnum userSexEnum : UserGenderEnum.values()) {
            if (userSexEnum.getCode() == code) {
                return userSexEnum;
            }
        }
        return UNKNOW;
    }

    /**
     * @description: (never used)通过字符code获取用户性别信息
     * @param: code
     * @date: 2019-08-15 10:06 AM
     * @return: com.zyd.shiro.business.enums.UserGenderEnum
     */
    public static UserGenderEnum getUserSex(String code) {
        if (code == null) {
            return UNKNOW;
        }
        if ("m".equals(code)) {
            return MALE;
        }
        if ("f".equals(code)) {
            return FEMALE;
        }
        return UNKNOW;
    }

    public int getCode() {
        return code;
    }

    //the method is never used
    public String getDesc() {
        return desc;
    }
}
