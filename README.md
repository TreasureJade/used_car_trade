# used_car_trade
车八方4s维保记录查询公众号


### 主要功能
  - 微信公众号自定义菜单，点击菜单栏跳转链接
  - 微信支付充值
  - 上传行驶本，通过百度API接口识别车架号
  - 根据车架号查询二手车的维保记录和出险记录
  - 查询成功扣除费用，失败退款
    
### 技术栈
 
 - 后端：springboot+mybatis+[WxJavaSDK](https://github.com/Wechat-Group/WxJava)
 - 前端：html+css+js+H5
 
### 运行环境
- OS:Centos7
- 数据库：Mysql8.0
- 开发基础框架：Spring boot
- web容器：Tomcat
 
 
### 使用说明
- clone本项目
- 修改application.yml中的配置信息:
    - 数据库用户和密码
    - 根据需要修改jwt.secret和jwt.expiration
    - 微信支付配置
    - 微信公众号配置
    - 蚂蚁女王相关配置
- 在微信公众平台配置信息
- 启动项目（本地需要内网穿透）
- 打开微信公众号

### 后续
- 添加后台管理系统
 -
