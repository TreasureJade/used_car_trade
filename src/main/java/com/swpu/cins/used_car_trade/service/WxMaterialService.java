package com.swpu.cins.used_car_trade.service;

import com.swpu.cins.used_car_trade.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

public interface WxMaterialService {

    ResultVO mediaUpload(MultipartFile file);

}
