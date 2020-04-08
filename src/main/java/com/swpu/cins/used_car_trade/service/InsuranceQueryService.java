package com.swpu.cins.used_car_trade.service;

import com.swpu.cins.used_car_trade.form.GetAllInsuranceOrderNoForm;
import com.swpu.cins.used_car_trade.form.GetInsuranceReportForm;
import com.swpu.cins.used_car_trade.form.PurchaseReportForm;
import com.swpu.cins.used_car_trade.vo.ResultVO;
import com.swpu.cins.used_car_trade.vo.ResultVO4;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hobo
 * 车险报告service
 */
public interface InsuranceQueryService {

    /**
     * 购买报告
     *
     * @return
     */
    ResultVO purchaseReport(PurchaseReportForm form);

    /**
     * 获取报告
     *
     * @return 获取的报告
     */
    ResultVO accessToReport(GetInsuranceReportForm form);


    /**
     * 查询用户所属下的所有账单号
     *
     * @param form 用户openid
     * @return 账单列表
     */
    ResultVO getAllOrderNo(GetAllInsuranceOrderNoForm form);

    /**
     * 识别行车本
     * @param file 行车本图片
     * @return 行驶证号码
     */
    ResultVO identificationCarCode(MultipartFile file);

    /**
     * 回调地址
     * @param request
     * @return
     */
    String callBack(HttpServletRequest request);
}
