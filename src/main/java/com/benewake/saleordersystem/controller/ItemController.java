package com.benewake.saleordersystem.controller;

import com.benewake.saleordersystem.entity.Item;
import com.benewake.saleordersystem.service.ItemService;
import com.benewake.saleordersystem.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author Lcs
 * @since 2023年07月12 11:21
 * 描 述： TODO
 */
@Controller
@RequestMapping("/item")
@ResponseBody
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/likeList")
    public Result<List<Item>> getlikeItemList(@RequestBody Map<String,Object> param){
        String itemCode = (String) param.get("itemCode");
        return Result.success(itemService.itemCodeLikeList(itemCode));
    }

}
