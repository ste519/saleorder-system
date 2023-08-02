package com.benewake.saleordersystem.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.benewake.saleordersystem.annotation.AdminRequired;
import com.benewake.saleordersystem.entity.Notice;
import com.benewake.saleordersystem.service.NoticeService;
import com.benewake.saleordersystem.utils.HostHolder;
import com.benewake.saleordersystem.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Lcs
 * @since 2023年08月02 16:33
 * 描 述： TODO
 */
@Api(tags = "通知管理")
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private HostHolder hostHolder;

    @ApiOperation("查询通知接口")
    @PostMapping("/find")
    public Result findNotice(@RequestBody Notice notice){
        return Result.success(noticeService.getAllList(notice.getCreateUserId()));
    }
    @ApiOperation("保存通知接口")
    @PostMapping("/save")
    @AdminRequired
    public Result saveNotice(@RequestBody Notice notice){
        notice.setCreateUserId(hostHolder.getUser().getId());
        noticeService.save(notice);
        return Result.success();
    }
    @ApiOperation("删除通知接口")
    @PostMapping("/delete")
    @AdminRequired
    public Result removeNotice(@RequestBody List<Long> ids){
        noticeService.removeBatchByIds(ids);
        return Result.success();
    }
    @ApiOperation("修改通知接口")
    @PostMapping("/update")
    @AdminRequired
    public Result updateNotice(@RequestBody Notice notice){
        if(notice.getId()==null){
            return Result.fail().message("参数有误！");
        }
        noticeService.updateById(notice);
        return Result.success();
    }

}
