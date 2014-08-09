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

对于服务器端，菜单树调用较为频繁，可以采用Redis缓存提高性能。当前暂无使用，在用做生产时可以考虑加上。

五、界面

采用了MetroNic2.0.2这套模板，官网：http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469

如果商用的话，这套模板是需要购买的，25美元。

因为MetroNic的assets目录中都是的静态资源文件，我没有把它放入权限相关项目，而是单独配置了一个Nginx访问地址：http://127.0.0.1/privilege_inc/assets/

assets的内容，可以到这里下载：http://pan.baidu.com/s/1qW98L0C

由于我们另配了assets地址，所以一些文件里面对于资源文件的地址要从相对路径改为绝对路径，比如：assets/scripts/core/app.js

六、Jquery插件

jsTree 	:	http://www.jstree.com/

jquery.validate 	:	http://bassistance.de/jquery-plugins/jquery-plugin-validation  http://docs.jquery.com/Plugins/Validation

DataTables	:	http://datatables.net/

Bootstram Modals	:	http://www.w3cschool.cc/bootstrap/bootstrap-v2-modal-plugin.html

colorbox	:	http://www.jacklmoore.com/colorbox/，用于弹出窗体，本系统使用的是MetroNic模板本身提供的模式(Bootstrap Modals)对话框，colorbox也是一种选择，这两种弹窗插件都比较好。

jquery-multi-select		：		http://loudev.com/

七、业务逻辑

对于模块，维护极少，不提供管理界面，手工操作数据库；

当前对于权限，仅控制到菜单级别，对于大多数系统来说，是适合的，如果需要更细致的权限级别，比如菜单里面的：CRUD，可以开发功能管理，实现步骤如下：

a.当需要一个控制时，管理员根据名称、意义，定制一个权限号，根据业务要求分配给某些角色

b.把权限号告知使用者，使用者根据此权限号，在程序中增加控制

八、其他

在datatable.js中，使用bootstrap_full_number分页方式，页码导航条宽度变得太高的问题，解决办法：bootstrap.min.css中，对于.pagination .li 去掉float:left之后，就好了。

在datatable.js中，对fnServerData段进行调整，用于向服务器端传递分页、查询等参数，同时也调整显示的提示文本内容。

jqueyr.validate.js和jquery.validte.min.js中，调整提示文本显示。