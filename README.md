# 一文助行（Android）
>  项目介绍

​	一款基于Android的火车票抢票APP，目前未接入支付功能，能查询票信息，对余票数量进行监控，检测到有票将会以APP通知、邮件等形式提醒用户。具体模块如下：

* 用户模块
  * 用户注册、登录
  * 修改密码、设置邮箱等
* 票信息查询模块
* 余票监控模块

 **PS：**服务器端详情查看另一个项目 https://github.com/Evenss/TicketServer

> 使用技术

* `okhttp` + `gson` 数据传输和解析
* 集成第三方推送 - 个推
* 主页采用ViewPager
* 列表展示采用RecycleView

> 数据来源

`去哪儿网`的火车票信息

> 屏幕截图

* 用户模块

![](https://i.imgur.com/wJoovDu.png)

![](https://i.imgur.com/gLDzXq7.png)

![](https://i.imgur.com/WUXSjQf.png)

* 查询模块

![](https://i.imgur.com/HZ0EOaB.png)

![](https://i.imgur.com/NtmVyP2.png)

* 余票监控

![](https://i.imgur.com/ctkFXTV.png)

* 推送模块

![](https://i.imgur.com/V8esccS.png)

![](https://i.imgur.com/zPA8Wqg.png)