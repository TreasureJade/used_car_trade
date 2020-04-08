package com.swpu.cins.used_car_trade.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.swpu.cins.used_car_trade.dao.MaintenanceRecordOrderDao;
import com.swpu.cins.used_car_trade.dto.MaintenanceDTO;
import com.swpu.cins.used_car_trade.dto.MaintenanceErrDTO;
import com.swpu.cins.used_car_trade.entity.MaintenanceRecordOrder;
import com.swpu.cins.used_car_trade.entity.User;
import com.swpu.cins.used_car_trade.enums.OrderEnum;
import com.swpu.cins.used_car_trade.enums.PayStatuEnum;
import com.swpu.cins.used_car_trade.enums.ResultEnum;
import com.swpu.cins.used_car_trade.form.CheckBrandForm;
import com.swpu.cins.used_car_trade.form.GetAllInsuranceOrderNoForm;
import com.swpu.cins.used_car_trade.form.GetInsuranceReportForm;
import com.swpu.cins.used_car_trade.form.PurchaseReportForm;
import com.swpu.cins.used_car_trade.service.MaintenanceRecordService;
import com.swpu.cins.used_car_trade.service.SendTemMsgService;
import com.swpu.cins.used_car_trade.service.UserService;
import com.swpu.cins.used_car_trade.utils.*;
import com.swpu.cins.used_car_trade.vo.MaintenanceVO;
import com.swpu.cins.used_car_trade.vo.ResultVO;
import com.swpu.cins.used_car_trade.vo.ResultVO2;
import com.swpu.cins.used_car_trade.vo.ResultVO3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;


@Service
@Slf4j
public class MaintenanceRecordServiceImpl implements MaintenanceRecordService {


    @Value("${car.partner_id}")
    private String partnerId;

    @Value("${car.secret_key}")
    private String secretKey;

    @Value("${car.url.check_brand_url}")
    private String checkBrandUrl;

    @Value("${car.url.buy_report_url}")
    private String buyReportUrl;

    @Value("${car.url.get_report_url}")
    private String getReportUrl;

    @Value("${car.url.call_back_url}")
    private String callBackUrl;

    @Autowired
    private MaintenanceRecordOrderDao recordOrderDao;

    @Autowired
    private UserService userService;

    @Autowired
    private SendTemMsgService msgService;


    @Override
    public ResultVO checkBrand(CheckBrandForm form) {
        Map<String, Object> param = new HashMap<>();
        String vin = form.getVin();
        String ts = Md5Util.getNowTime();
        String str = "partner_id=" + partnerId + "&" + "ts=" + ts + "&" + "vin=" + vin + "&partner_key=" + secretKey;
        String sn = Md5Util.encryption(str);

        param.put("partner_id", partnerId);
        param.put("ts", ts);
        param.put("sign", sn);
        param.put("vin", vin);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<ResultVO2> response = restTemplate.exchange(
                checkBrandUrl + "?partner_id={partner_id}&ts={ts}&sign={sign}&vin={vin}",
                HttpMethod.GET,
                new HttpEntity<String>(headers),
                ResultVO2.class,
                param);

        ResultVO2 resultVO = (ResultVO2) response.getBody();

        log.info("返回的状态码为：{}，MSG为：{}", resultVO.getReturncode(), resultVO.getMessage());

        if (resultVO.getReturncode() != 1106) {
            return ReturnCodeUtil.switchCode(resultVO.getReturncode());
        }

        LinkedHashMap infoMap = (LinkedHashMap) resultVO.getResult();
        String brandName = (String) infoMap.get("brand_name");

        double fee = FeeChoseUtil.judgeCarBrand(brandName);
        infoMap.put("fee", fee);

        MaintenanceRecordOrder order = recordOrderDao.queryByOpenIdAndVin(form.getOpenId(), form.getVin());

        if (order == null) {
            MaintenanceRecordOrder order1 = new MaintenanceRecordOrder();
            System.out.println(form.getVin());
            order1.setVin(form.getVin());
            order1.setOpenId(form.getOpenId());
            order1.setBrandName(brandName);
            order1.setOrderStatus(OrderEnum.DID_NOT_SEE.getStatus());
            order1.setCreatTime(new Date());
            recordOrderDao.insert(order1);
        }

        return ResultVOUtil.success(infoMap);
    }

    @Override
    public ResultVO purchaseReport(PurchaseReportForm form) {
        MaintenanceRecordOrder order = recordOrderDao.queryByOpenIdAndVin(form.getOpenId(), form.getVin());

        if (order == null) {
            return ResultVOUtil.error(ResultEnum.VIN_NOT_DETERMINED_TO_SUPPORT_THE_QUERY);
        }

        if (order.getOrderNo() != null) {
            return ResultVOUtil.success(order.getOrderNo());
        }

        String brandName = order.getBrandName();

        // 判断收费
        double fee = FeeChoseUtil.judgeCarBrand(brandName);

        User user = userService.queryById(form.getOpenId());
        double charge = user.getBalanceNum() - fee;

        // 判断账户余额是否充足
        if (charge < 0) {
            return ResultVOUtil.error(ResultEnum.LACK_OF_BALANCE);
        }


        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        // 组装 sn
        String orderNo = RandomUtil.getRandomStringByLength(16);
        String ts = Md5Util.getNowTime();
        String str = Md5Util.assmBlyBuyReportString(callBackUrl, orderNo, partnerId, ts, form.getVin(), secretKey);
        System.out.println(str);
        String sn = Md5Util.encryption(str);

        params.add("partner_id", partnerId);
        params.add("sign", sn);
        params.add("ts", ts);
        params.add("vin", form.getVin());
        params.add("orderid", orderNo);
        params.add("callbackurl", callBackUrl);
        System.out.println(callBackUrl);
        ResultVO2 resultVO = SendPostRequestUtil.sendPostRequest2(buyReportUrl, params);
        log.info("返回的状态码为：{}，MSG为：{}", resultVO.getReturncode(), resultVO.getMessage());

        if (resultVO.getReturncode() != 0) {
            if (resultVO.getReturncode() == 1102) {
                order.setOrderStatus(OrderEnum.QUERYING.getStatus());
                recordOrderDao.update(order);
                return ResultVOUtil.error(ResultEnum.IN_THE_QUERY);
            }
            return ReturnCodeUtil.switchCode(resultVO.getReturncode());
        }

        LinkedHashMap infoMap = (LinkedHashMap) resultVO.getResult();

        // 获取返回的订单编号
        String orderNo1 = (String) infoMap.get("orderId").toString();

        // 扣费
        user.setBalanceNum(charge);
        userService.update(user);
        order.setOrderNo(orderNo1);
        order.setCost(fee);
        order.setPayStatu(PayStatuEnum.PAY_SUCCESS.getPayStatu());
        recordOrderDao.update(order);
        System.out.println(order.getPayStatu());
        return ResultVOUtil.success(orderNo1);
    }

    @Override
    public ResultVO accessToReport(GetInsuranceReportForm form) {

        Map<String, Object> param = new HashMap<>();
        String orderNo = form.getOrderNo();
        String ts = Md5Util.getNowTime();
        String str = "orderid=" + orderNo + "&" + "partner_id=" + partnerId + "&" + "ts=" + ts + "&partner_key=" + secretKey;
        String sn = Md5Util.encryption(str);

        param.put("partner_id", partnerId);
        param.put("ts", ts);
        param.put("sign", sn);
        param.put("orderid", orderNo);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<ResultVO2> response = restTemplate.exchange(
                getReportUrl + "?orderid={orderid}&partner_id={partner_id}&ts={ts}&sign={sign}",
                HttpMethod.GET,
                new HttpEntity<String>(headers),
                ResultVO2.class,
                param);

        ResultVO2 resultVO = response.getBody();
        log.info("返回的状态码为：{}，MSG为：{}", resultVO.getReturncode(), resultVO.getMessage());

        if (resultVO.getReturncode() != 0) {
            MaintenanceRecordOrder order = recordOrderDao.queryByOrderNoAndOpenId(form.getOrderNo(), form.getOpenId());
            if (resultVO.getReturncode() == 1102) {
                order.setOrderStatus(OrderEnum.QUERYING.getStatus());
                recordOrderDao.update(order);
                return ResultVOUtil.error(ResultEnum.IN_THE_QUERY);
            }
            return ReturnCodeUtil.switchCode(resultVO.getReturncode());
        }
        MaintenanceRecordOrder order = recordOrderDao.queryByOrderNoAndOpenId(form.getOrderNo(), form.getOpenId());
        order.setOrderStatus(OrderEnum.FINISHED.getStatus());
        recordOrderDao.update(order);
        return ResultVOUtil.success(resultVO.getResult());
    }


    @Override
    public ResultVO getAllOrderNo(GetAllInsuranceOrderNoForm form) {
        List<MaintenanceVO> result = recordOrderDao.queryOrderByOpenId(form.getOpenId());
        return ResultVOUtil.success(result);
    }

    @Override
    public ResultVO3 callBack(HttpServletRequest request) {
        //获取到JSONObject
        String jsonParam = JsonUtils.getJSONParam(request);
        System.out.println(jsonParam);
        if (!"".equals(jsonParam)) {
            ResultVO2 resultVO2 = JSONObject.parseObject(jsonParam, ResultVO2.class);
            return judgeCode(resultVO2);
        }
        return null;
    }

    private ResultVO3 judgeCode(ResultVO2 resultVO2) {
        if (resultVO2 != null) {
            String res = (String) resultVO2.getResult();

            if (resultVO2.getReturncode() == 0) {
                MaintenanceDTO dto = JSONObject.parseObject(res, MaintenanceDTO.class);
                log.info("回调成功，结果成功，订单号为:{}", dto.getReportNo());
                // 修改订单状态，发送消息
                MaintenanceRecordOrder order = recordOrderDao.queryByOrderNo(dto.getReportNo());
                order.setOrderStatus(OrderEnum.FINISHED.getStatus());
                String date = SendPostRequestUtil.getCnTime(order.getCreatTime());
                recordOrderDao.update(order);
                msgService.sendQueryMsg(order.getOpenId(), dto.getReportNo(), dto.getBrand(), date, "订单查询成功");

                ResultVO3 vo = new ResultVO3();
                vo.setReturncode(200);
                vo.setMessage("success");
                return vo;
            }
            log.info("回调成功，结果失败，订单号为:{}", resultVO2.getOrderId());

            // 修改订单状态，发送消息
            MaintenanceRecordOrder order = recordOrderDao.queryByOrderNo(resultVO2.getOrderId());
            order.setOrderStatus(OrderEnum.Filed.getStatus());
            String date = SendPostRequestUtil.getCnTime(order.getCreatTime());
            System.out.println(order.toString());
            recordOrderDao.update(order);
            msgService.sendQueryMsg(order.getOpenId(), resultVO2.getOrderId(), order.getBrandName(), date, "订单查询失败");

            if (order.getPayStatu() == PayStatuEnum.PAY_SUCCESS.getPayStatu()) {
                // 退款
                User user = userService.queryById(order.getOpenId());
                user.setBalanceNum(user.getBalanceNum() + order.getCost());
                order.setPayStatu(PayStatuEnum.HAVE_REFUND.getPayStatu());
                userService.update(user);
                recordOrderDao.update(order);
                msgService.sendRefundMsg(user.getOpenId(), order.getCost() / 100 + "元");
            }
            ResultVO3 vo = new ResultVO3();
            vo.setReturncode(200);
            vo.setMessage("success");
            return vo;
        }
        return null;
    }


}
