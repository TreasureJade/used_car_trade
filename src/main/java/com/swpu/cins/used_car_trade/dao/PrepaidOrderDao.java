package com.swpu.cins.used_car_trade.dao;

import com.swpu.cins.used_car_trade.entity.PrepaidOrder;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (PrepaidOrder)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-16 22:09:27
 */
public interface PrepaidOrderDao {

    /**
     * 通过ID查询单条数据
     *
     * @param wxOrderNo 主键
     * @return 实例对象
     */
    PrepaidOrder queryById(String wxOrderNo);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<PrepaidOrder> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param prepaidOrder 实例对象
     * @return 对象列表
     */
    List<PrepaidOrder> queryAll(PrepaidOrder prepaidOrder);

    /**
     * 新增数据
     *
     * @param prepaidOrder 实例对象
     * @return 影响行数
     */
    int insert(PrepaidOrder prepaidOrder);

    /**
     * 修改数据
     *
     * @param prepaidOrder 实例对象
     * @return 影响行数
     */
    int update(PrepaidOrder prepaidOrder);

    /**
     * 通过主键删除数据
     *
     * @param wxOrderNo 主键
     * @return 影响行数
     */
    int deleteById(String wxOrderNo);

}