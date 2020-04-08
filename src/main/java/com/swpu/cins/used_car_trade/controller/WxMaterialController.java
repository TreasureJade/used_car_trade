package com.swpu.cins.used_car_trade.controller;

import com.swpu.cins.used_car_trade.service.WxMaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/wx/material")
@Api(tags = "微信素材管理")
public class WxMaterialController {

    @Autowired
    private WxMaterialService materialService;

    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public Object upload(MultipartFile file) {
        return materialService.mediaUpload(file);
    }

}
