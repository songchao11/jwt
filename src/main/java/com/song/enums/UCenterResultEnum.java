package com.song.enums;

/**
 * Created by song on 2018/8/13.
 */
public enum UCenterResultEnum {

    USER_NAME_EMPTY(10001, "用户名为空"),
    USER_PASSWORD_EMPTY(10002, "用户密码为空"),
    USERNAME_OR_PASSWORD_ERROR(10003, "用户名或密码错误"),
    ;

    /**
     * 值
     */
    private int value;

    /**
     * 注释
     */
    private String comment;

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return this.comment;
    }

    UCenterResultEnum(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    /**
     * 根据值获取对应的枚举
     *
     * @param value 枚举的数值
     * @return 成功返回相应的枚举，否则返回null。
     */
    public static UCenterResultEnum parse(Integer value) {
        if (value != null) {
            UCenterResultEnum[] array = values();
            for (UCenterResultEnum each : array) {
                if (value == each.value) {
                    return each;
                }
            }
        }
        return null;
    }

}
