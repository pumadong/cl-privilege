cl-privilege
==================

通用权限管理系统


ORM框架采用MyBatis，为了提高开发效率，先根据数据库表单结构自动生成Model和MyBatis相关类，生成命令如下：

java -jar mybatis-generator-core-1.3.1.jar -configfile config_privilege.xml -overwrite

生成时需要把mybatis-generator-core-1.3.1.jar、mysql-connector-java-5.1.24-bin.jar、config_privilege.xml放到一个目录下，生成的相关类和XML会放置到CreateResult文件夹下面。

参考网址：
http://www.mybatis.org/generator/
http://pan.baidu.com/s/1qW98L0C
http://qiuguo0205.iteye.com/blog/819100
http://jadethao.iteye.com/blog/1726115