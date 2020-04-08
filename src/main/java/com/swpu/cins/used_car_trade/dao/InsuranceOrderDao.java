package com.swpu.cins.used_car_trade.dao;

import com.swpu.cins.used_car_trade.entity.InsuranceOrder;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (InsuranceOrder)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-16 20:24:23
 */
public interface InsuranceOrderDao {

    /**
     * 通过ID查询单条数据
     *
     * @param orderNo 主键
     * @return 实例对象
     */
    InsuranceOrder queryById(String orderNo);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<InsuranceOrder> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param insuranceOrder 实例对象
     * @return 对象列表
     */
    List<InsuranceOrder> queryAll(InsuranceOrder insuranceOrder);

    /**
     * 新增数据
     *
     * @param insuranceOrder 实例对象
     * @return 影响行数
     */
    int insert(InsuranceOrder insuranceOrder);

    /**
     * 修改数据
     *
     * @param insuranceOrder 实例对象
     * @return 影响行数
     */
    int update(InsuranceOrder insuranceOrder);

    /**
     * 通过主键删除数据
     *
     * @param orderNo 主键
     * @return 影响行数
     */
    int deleteById(String orderNo);

    InsuranceOrder queryByOpenIdAndVin(String openId, String vin);

    List<InsuranceOrder> queryAllOrderNo(String openId);
}