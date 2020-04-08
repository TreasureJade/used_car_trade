package com.swpu.cins.used_car_trade.service.impl;

import com.swpu.cins.used_car_trade.entity.User;
import com.swpu.cins.used_car_trade.dao.UserDao;
import com.swpu.cins.used_car_trade.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2020-02-21 17:33:06
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    /**
     * 通过ID查询单条数据
     *
     * @param openId 主键
     * @return 实例对象
     */
    @Override
    public User queryById(String openId) {
        return userDao.queryById(openId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<User> queryAllByLimit(int offset, int limit) {
        return this.userDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User insert(User user) {
        this.userDao.insert(user);
        return user;
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public boolean update(User user) {
        return userDao.update(user)==1;
    }

    /**
     * 通过主键删除数据
     *
     * @param openId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String openId) {
        return this.userDao.deleteById(openId) > 0;
    }
}