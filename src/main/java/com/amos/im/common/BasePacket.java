package com.amos.im.common;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/19
 */
public abstract class BasePacket {

    /**
     * 协议版本
     */
    private Byte version = 1;

    /**
     * 指令
     *
     * @return 命令
     */
    public abstract Byte getCommand();

    public Byte getVersion() {
        return version;
    }

    public void setVersion(Byte version) {
        this.version = version;
    }
}
