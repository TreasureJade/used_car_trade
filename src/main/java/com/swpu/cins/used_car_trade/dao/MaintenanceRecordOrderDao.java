package com.swpu.cins.used_car_trade.dao;

import com.swpu.cins.used_car_trade.entity.MaintenanceRecordOrder;
import com.swpu.cins.used_car_trade.vo.MaintenanceVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (MaintenanceRecordOrder)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-16 20:24:23
 */
public interface MaintenanceRecordOrderDao {

    /**
     * 通过ID查询单条数据
     *
     * @param vin 主键
     * @return 实例对象
     */
    MaintenanceRecordOrder queryById(String vin);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<MaintenanceRecordOrder> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param maintenanceRecordOrder 实例对象
     * @return 对象列表
     */
    List<MaintenanceRecordOrder> queryAll(MaintenanceRecordOrder maintenanceRecordOrder);

    /**
     * 新增数据
     *
     * @param maintenanceRecordOrder 实例对象
     * @return 影响行数
     */
    int insert(MaintenanceRecordOrder maintenanceRecordOrder);

    /**
     * 修改数据
     *
     * @param maintenanceRecordOrder 实例对象
     * @return 影响行数
     */
    int update(MaintenanceRecordOrder maintenanceRecordOrder);

    /**
     * 通过主键删除数据
     *
     * @param vin 主键
     * @return 影响行数
     */
    int deleteById(String vin);

    MaintenanceRecordOrder queryByOpenIdAndVin(String openId, String vin);

    MaintenanceRecordOrder queryByOrderNoAndOpenId(String orderNo, String openId);

    MaintenanceRecordOrder queryByOrderNo(String orderNo);

    List<MaintenanceVO> queryOrderByOpenId(String openId);
}