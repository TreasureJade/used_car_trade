package com.swpu.cins.used_car_trade.service;

import com.swpu.cins.used_car_trade.entity.User;
import com.swpu.cins.used_car_trade.vo.ResultVO;

import java.util.List;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2020-02-21 17:33:06
 */
public interface UserService {

    /**
     * 通过ID查询单条数据
     *
     * @param openId 主键
     * @return 实例对象
     */
    User queryById(String openId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<User> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User insert(User user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    boolean update(User user);

    /**
     * 通过主键删除数据
     *
     * @param openId 主键
     * @return 是否成功
     */
    boolean deleteById(String openId);

}