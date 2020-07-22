package com.amos.im.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

/**
 * PROJECT: im
 * DESCRIPTION: request/response基类
 *
 * @author Daoyuan
 * @date 2019/3/19
 */
@Getter
@Setter
@Accessors(chain = true)
public abstract class BasePacket {

    private static final String SUCCESS_CORE = GeneralCode.SUCCESS.getCode();

    @ApiModelProperty(value = "是否成功", hidden = true)
    private Boolean success = true;

    @ApiModelProperty(value = "状态码", hidden = true)
    private String resCode;
    @ApiModelProperty(value = "提示信息", hidden = true)
    private String resMsg;

    @ApiModelProperty(value = "发送人token")
    private String sender;

    @ApiModelProperty(value = "创建时间", hidden = true)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "协议版本", hidden = true)
    private Byte version = 1;

    /**
     * 设置返回消息
     *
     * @param generalCode GeneralCode
     */
    public void setGeneralCode(GeneralCode generalCode) {
        this.setSuccess(SUCCESS_CORE.equals(generalCode.getCode()));

        this.setResCode(generalCode.getCode());
        this.setResMsg(generalCode.getMsg());
    }

    /**
     * 指令(由实现类指定,指令数量有限[-128, 127])
     *
     * @return byte
     */
    @ApiModelProperty(value = "指令", hidden = true)
    public abstract Byte getCommand();

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
