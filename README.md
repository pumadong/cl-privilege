cl-privilege
==================

通用权限管理系统


一、mybatis-generator

ORM框架采用MyBatis，为了提高开发效率，先根据数据库表单结构自动生成Model和MyBatis相关类，生成命令如下：

java -jar mybatis-generator-core-1.3.1.jar -configfile config_privilege.xml -overwrite

生成时需要把mybatis-generator-core-1.3.1.jar、mysql-connector-java-5.1.24-bin.jar、config_privilege.xml放到一个目录下，生成的相关类和XML会放置到CreateResult文件夹下面。

参考网址：
http://www.mybatis.org/generator/
http://pan.baidu.com/s/1qW98L0C
http://qiuguo0205.iteye.com/blog/819100
http://jadethao.iteye.com/blog/1726115

二、Dubbo

客户端、服务器端通讯框架采用Dubbo，Dubbo官网：http://alibaba.github.io/dubbo-doc-static/Home-zh.htm

三、Jasig CAS

对于身份认证，采用单点登录系统：Jasig CAS，官网：http://www.jasig.org/cas

我对jasig server和client的jar进行了修改，达到目的：

1、对服务器的界面进行了修改，使用MetroNic这套模板；对服务器的认证方式做了更改，采用MySQL进行身份验证。

代码位置：https://github.com/pumadong/cas-server-3.5.2

2、对客户端进行了小调整，让登陆之后自动返回到登陆之前的页面

代码位置：https://github.com/pumadong/cas-client-3.2.1

四、Redis

对于服务器端，菜单树调用较为频繁，可以采用Redis缓存提高性能。

五、界面

采用了MetroNic这套模板，官网：http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469

如果商用的话，这套模板是需要购买的，25美元。

因为MetroNic的assets目录中都是的静态资源文件，我没有把它放入权限相关项目，而是单独配置了一个Nginx访问地址：http://127.0.0.1/privilege_inc/assets/

assets的内容，可以到这里下载：http://pan.baidu.com/s/1qW98L0C

由于我们另配了assets地址，所以一些文件里面对于资源文件的地址要从相对路径改为绝对路径，比如：
assets/scripts/core/app.js

六、Jquery插件

jsTree 	:	http://www.jstree.com/
jquery.validate 	:	http://bassistance.de/jquery-plugins/jquery-plugin-validation