# 单点登录文章
https://yq.aliyun.com/articles/636281


##访问地址
单点登录服务端访问地址
8081
http://localhost:8082/taotaoshopping.system1/file/index.jsp

单点登录客户端访问地址
8082
http://localhost:8083/taotaoshopping.system2/file/index.jsp

#用户名和密码是root 123

1  存储部分使用 J_Util（jdbc） 工具类，后期改为redis可以控制token存活的时间
2 token客户端和服务端应该使用非对称加密算法 以公钥私钥的形式加密。


运行方式 
tomcat7:run

