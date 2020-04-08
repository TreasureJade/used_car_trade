package com.swpu.cins.used_car_trade.utils;

import com.swpu.cins.used_car_trade.vo.ResultVO;
import com.swpu.cins.used_car_trade.vo.ResultVO2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class SendPostRequestUtil {


    /**
     * 向目的URL发送post请求
     *
     * @param url    目的url
     * @param params 发送的参数
     * @return ResultVO
     */
    public static ResultVO sendPostRequest(String url, MultiValueMap<String, Object> params) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);
        //执行HTTP请求，将返回的结构使用ResultVO类格式化
        ResponseEntity<ResultVO> response = client.exchange(url, method, requestEntity, ResultVO.class);
        System.out.println(response.getBody());
        return response.getBody();
    }

    public static ResultVO2 sendPostRequest2(String url, MultiValueMap<String, Object> params) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);
        //执行HTTP请求，将返回的结构使用ResultVO类格式化
        ResponseEntity<ResultVO2> response = client.exchange(url, method, requestEntity, ResultVO2.class);
        System.out.println(response.getBody());
        return response.getBody();
    }

    public static Map<Object, Object> sendPostRequest2(String url, Map<String, Object> param) {
        RestTemplate restTemplate = new RestTemplate();
        param.put("param1", "1");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<String>(headers),
                Map.class,
                param);
        Map<Object, Object> body = response.getBody();
        return body;
    }

    /**
     * 发送购买报告请求
     *
     * @param url       请求url
     * @param partnerId id
     * @param secretKey 秘钥
     * @param vin       车架号
     * @return infoMap
     */
    public static Object sendBuyReportRequest(String url, String partnerId, String secretKey, String vin, String callBackUrl) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        // 组装 sn
        String ts = Md5Util.getNowTime();
        String str = Md5Util.assemblyBuyReportString(partnerId, vin, secretKey, ts, callBackUrl);
        String sn = Md5Util.encryption(str);


        params.add("partner_id", partnerId);
        params.add("ts", ts);
        params.add("sign", sn);
        params.add("vin", vin);
        params.add("callbackUrl", callBackUrl);

        ResultVO resultVO = SendPostRequestUtil.sendPostRequest(url, params);
        log.info("返回的状态码为：{}，MSG为：{}", resultVO.getCode(), resultVO.getMsg());

        if (resultVO.getCode() != 1000) {
            return ReturnCodeUtil.switchCode(resultVO.getCode());
        }
        System.out.println(resultVO);
        return (LinkedHashMap) resultVO.getData();
    }

    /**
     * 发送获取报告请求
     *
     * @param url       请求url
     * @param partnerId id
     * @param secretKey 秘钥
     * @param orderNo   订单号
     * @return infoMap
     */
    public static Object sendGetReportRequest(String url, String partnerId, String secretKey, String orderNo) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        // 组装 sn
        String ts = Md5Util.getNowTime();
        String str = Md5Util.assmBlyBuyReportString(orderNo, partnerId, ts, secretKey);
        System.out.println(str);
        String sn = Md5Util.encryption(str);

        params.add("partner_id", partnerId);
        params.add("ts", ts);
        params.add("sign", sn);
        params.add("orderNo", orderNo);

        ResultVO resultVO = SendPostRequestUtil.sendPostRequest(url, params);
        log.info("返回的状态码为：{}，MSG为：{}", resultVO.getCode(), resultVO.getMsg());

        if (resultVO.getCode() != 1000) {
            return ReturnCodeUtil.switchCode(resultVO.getCode());
        }
        return (ArrayList) resultVO.getData();
    }

    public static String getCnTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

}
