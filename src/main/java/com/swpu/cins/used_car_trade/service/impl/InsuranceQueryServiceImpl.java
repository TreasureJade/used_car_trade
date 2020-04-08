package com.swpu.cins.used_car_trade.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.swpu.cins.used_car_trade.dao.InsuranceOrderDao;
import com.swpu.cins.used_car_trade.dto.InsuranceDTO;
import com.swpu.cins.used_car_trade.entity.InsuranceOrder;
import com.swpu.cins.used_car_trade.entity.User;
import com.swpu.cins.used_car_trade.enums.OrderEnum;
import com.swpu.cins.used_car_trade.enums.PayStatuEnum;
import com.swpu.cins.used_car_trade.enums.ResultEnum;
import com.swpu.cins.used_car_trade.form.GetAllInsuranceOrderNoForm;
import com.swpu.cins.used_car_trade.form.GetInsuranceReportForm;
import com.swpu.cins.used_car_trade.form.PurchaseReportForm;
import com.swpu.cins.used_car_trade.service.InsuranceQueryService;
import com.swpu.cins.used_car_trade.service.SendTemMsgService;
import com.swpu.cins.used_car_trade.service.UserService;
import com.swpu.cins.used_car_trade.utils.*;
import com.swpu.cins.used_car_trade.vo.ResultVO;
import com.swpu.cins.used_car_trade.vo.ResultVO2;
import com.swpu.cins.used_car_trade.vo.ResultVO4;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import static com.swpu.cins.used_car_trade.enums.ChargeEnum.INSURANCE_ENQUIRY_FEE;

/**
 * @author hobo
 */

@Service
@Slf4j
public class InsuranceQueryServiceImpl implements InsuranceQueryService {

    @Value("${car.partner_id}")
    private String partnerId;

    @Value("${car.secret_key}")
    private String secretKey;

    @Value("${car.url.insurance_url}")
    private String insuranceUrl;

    @Value("${car.url.insurance_get_report_url}")
    private String insuranceGetReportUrl;

    @Value("${car.url.call_back_url2}")
    private String callBackUrl2;


    @Autowired
    private InsuranceOrderDao insuranceOrderDao;

    @Autowired
    private UserService userService;

    @Autowired
    private SendTemMsgService msgService;

    @Override
    public ResultVO purchaseReport(PurchaseReportForm form) {
        InsuranceOrder res = insuranceOrderDao.queryByOpenIdAndVin(form.getOpenId(), form.getVin());
        if (res != null) {
            return ResultVOUtil.success(res.getOrderNo());
        }

        User user = userService.queryById(form.getOpenId());

        // 判断账户余额是否充足
        double charge = user.getBalanceNum() - INSURANCE_ENQUIRY_FEE.getFee();
        if (charge < 0) {
            return ResultVOUtil.error(ResultEnum.LACK_OF_BALANCE);
        }

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        String ts = Md5Util.getNowTime();
        String str = Md5Util.assemblyBuyReportString(partnerId, form.getVin(), secretKey, ts, callBackUrl2);
        String sn = Md5Util.encryption(str);
        System.out.println(str);

        params.add("partner_id", partnerId);
        params.add("ts", ts);
        params.add("sign", sn);
        params.add("vin", form.getVin());
        params.add("callbackUrl", callBackUrl2);


        System.out.println(callBackUrl2);
        ResultVO resultVO = SendPostRequestUtil.sendPostRequest(insuranceUrl, params);

        log.info("返回的状态码为：{}，MSG为：{}", resultVO.getCode(), resultVO.getMsg());

        if (resultVO.getCode() != 1000) {
            if (resultVO.getCode() == 1100) {
                return ResultVOUtil.error(ResultEnum.IN_THE_QUERY);
            }
            return ReturnCodeUtil.switchCode(resultVO.getCode());
        }

        LinkedHashMap infoMap = (LinkedHashMap) resultVO.getData();
        // 获取返回的订单编号
        String orderNo = (String) infoMap.get("orderNo");
        System.out.println(orderNo);

        //订单存库，默认订单状态为查询中
        InsuranceOrder order = new InsuranceOrder();
        order.setOpenId(form.getOpenId());
        order.setOrderNo(orderNo);
        order.setVin(form.getVin());
        order.setCreateDate(new Date());
        order.setPayStatu(PayStatuEnum.PAY_SUCCESS.getPayStatu());
        order.setOrderStatus(OrderEnum.DID_NOT_SEE.getStatus());
        insuranceOrderDao.insert(order);
        System.out.println(order);

        // 扣费
        user.setBalanceNum(charge);
        if (userService.update(user)) {
            return ResultVOUtil.success(orderNo);
        }
        return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO accessToReport(GetInsuranceReportForm form) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        // 组装 sn
        String ts = Md5Util.getNowTime();
        String str = Md5Util.assmBlyBuyReportString(form.getOrderNo(), partnerId, ts, secretKey);
        String sn = Md5Util.encryption(str);

        params.add("partner_id", partnerId);
        params.add("ts", ts);
        params.add("sign", sn);
        params.add("orderNo", form.getOrderNo());

        ResultVO resultVO = SendPostRequestUtil.sendPostRequest(insuranceGetReportUrl, params);
        log.info("返回的状态码为：{}，MSG为：{}", resultVO.getCode(), resultVO.getMsg());

        if (resultVO.getCode() != 1000) {
            InsuranceOrder order = insuranceOrderDao.queryById(form.getOrderNo());
            if (resultVO.getCode() == 1100) {
                order.setOrderStatus(OrderEnum.QUERYING.getStatus());
                return ResultVOUtil.error(ResultEnum.IN_THE_QUERY);
            }

            order.setOrderStatus(OrderEnum.Filed.getStatus());
            insuranceOrderDao.update(order);
            return ReturnCodeUtil.switchCode(resultVO.getCode());
        }
        ArrayList infoMap = (ArrayList) resultVO.getData();

        InsuranceOrder order = insuranceOrderDao.queryById(form.getOrderNo());
        order.setOrderStatus(OrderEnum.FINISHED.getStatus());
        insuranceOrderDao.update(order);
        return ResultVOUtil.success(infoMap);
    }

    @Override
    public ResultVO getAllOrderNo(GetAllInsuranceOrderNoForm form) {
        return ResultVOUtil.success(insuranceOrderDao.queryAllOrderNo(form.getOpenId()));
    }

    @Override
    public ResultVO identificationCarCode(MultipartFile file) {
        String result = BaiduUtil.vehicleLicense(file);
        return ResultVOUtil.success(result);
    }

    @Override
    public String callBack(HttpServletRequest request) {
        //获取到JSONObject
        String jsonParam = JsonUtils.getJSONParam(request);
        if (!"".equals(jsonParam)) {
            InsuranceDTO dto = JSONObject.parseObject(jsonParam, InsuranceDTO.class);
            return judgeCode(dto);
        }
        return null;
    }

    private String judgeCode(InsuranceDTO dto) {
        if (dto != null) {
            InsuranceOrder order = insuranceOrderDao.queryById(dto.getOrderNo());
            if (dto.getCode() == 1000) {
                log.info("回调成功，结果成功，订单号为:{}", dto.getOrderNo());

                order.setOrderStatus(OrderEnum.FINISHED.getStatus());
                String date = SendPostRequestUtil.getCnTime(order.getCreateDate());
                insuranceOrderDao.update(order);
                msgService.sendQueryMsg(order.getOpenId(), dto.getOrderNo(), "暂不支持查看品牌", date, "订单查询成功");

                return "success";
            }
            log.info("回调成功，结果失败，订单号为:{}", dto.getOrderNo());
            System.out.println(order.getCreateDate());
            String date = SendPostRequestUtil.getCnTime(order.getCreateDate());
            msgService.sendQueryMsg(order.getOpenId(), dto.getOrderNo(), "暂不支持查看品牌", date, "订单查询失败");

            if (order.getPayStatu() == PayStatuEnum.PAY_SUCCESS.getPayStatu()) {
                User user = userService.queryById(order.getOpenId());
                user.setBalanceNum(user.getBalanceNum() + INSURANCE_ENQUIRY_FEE.getFee());
                // 订单支付状态改为已退款
                order.setPayStatu(PayStatuEnum.HAVE_REFUND.getPayStatu());
                order.setOrderStatus(OrderEnum.Filed.getStatus());
                msgService.sendRefundMsg(user.getOpenId(), 22 + "元");
                insuranceOrderDao.update(order);
                userService.update(user);
            }
            return "success";
        }
        return null;
    }


}
