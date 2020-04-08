package com.swpu.cins.used_car_trade.service.impl;

import com.swpu.cins.used_car_trade.enums.ResultEnum;
import com.swpu.cins.used_car_trade.service.WxMaterialService;
import com.swpu.cins.used_car_trade.utils.FileConvertUtil;
import com.swpu.cins.used_car_trade.utils.ResultVOUtil;
import com.swpu.cins.used_car_trade.vo.ResultVO;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.WxMpMaterial;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class WxMaterialServiceImpl implements WxMaterialService {

    @Autowired
    private WxMpService wxMpService;

    @Override
    public ResultVO mediaUpload(MultipartFile file) {
        File file1 = null;
        try {
            file1 = FileConvertUtil.convert(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        WxMpMaterial mpMaterial = new WxMpMaterial();

        mpMaterial.setFile(file1);
        mpMaterial.setName("微信二维码");
        try {
            WxMpMaterialUploadResult res = wxMpService.getMaterialService().materialFileUpload("image", mpMaterial);
            return ResultVOUtil.success(res);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
    }
}
