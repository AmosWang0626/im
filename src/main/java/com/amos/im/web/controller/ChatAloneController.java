package com.amos.im.web.controller;

import com.amos.im.common.GeneralResponse;
import com.amos.im.core.business.AloneBusiness;
import com.amos.im.core.pojo.form.MessageRecordRequest;
import com.amos.im.core.command.request.MessageRequest;
import com.amos.im.dao.entity.ChatRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * PROJECT: Sales
 * DESCRIPTION: 单聊
 *
 * @author amos
 * @date 2019/6/2
 */
@Api(tags = {"单聊相关"})
@RestController
@RequestMapping("alone")
public class ChatAloneController {

    @Resource
    private AloneBusiness aloneBusiness;

    @PostMapping
    @ApiOperation("单聊")
    public GeneralResponse<?> alone(@RequestBody MessageRequest messageRequest) {

        return aloneBusiness.alone(messageRequest);
    }

    @PostMapping("list")
    @ApiOperation("单聊聊天记录")
    public GeneralResponse<List<ChatRecord>> list(@RequestBody MessageRecordRequest request) {

        return aloneBusiness.list(request);
    }


}
