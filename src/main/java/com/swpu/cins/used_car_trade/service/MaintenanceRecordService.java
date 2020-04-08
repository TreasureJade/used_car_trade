package com.swpu.cins.used_car_trade.service;

import com.swpu.cins.used_car_trade.form.CheckBrandForm;
import com.swpu.cins.used_car_trade.form.GetAllInsuranceOrderNoForm;
import com.swpu.cins.used_car_trade.form.GetInsuranceReportForm;
import com.swpu.cins.used_car_trade.form.PurchaseReportForm;
import com.swpu.cins.used_car_trade.vo.ResultVO;
import com.swpu.cins.used_car_trade.vo.ResultVO2;
import com.swpu.cins.used_car_trade.vo.ResultVO3;

import javax.servlet.http.HttpServletRequest;

/**
 * 维保记录查询service
 *
 * @author hobo
 */
public interface MaintenanceRecordService {

    ResultVO checkBrand(CheckBrandForm form);

    ResultVO purchaseReport(PurchaseReportForm form);

    ResultVO accessToReport(GetInsuranceReportForm form);

    ResultVO3 callBack(HttpServletRequest request);

    ResultVO getAllOrderNo(GetAllInsuranceOrderNoForm form);
}
