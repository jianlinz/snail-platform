包含内容
====
- bom
- generator
- spring-boot及spring-cloud集成
- 权限集成spring-security 支持session共享+jwt组合鉴权

如何使用
====
- 1.部署nexus
- 2.cloud组件 非必须如果微服务需要 启动对应组件 目前仅提供 阿里
    - nacos
    - 
- 3.配置gradle.properties nexus参数
  - nexusPublicUrl
  - nexusReleasesUrl
  - nexusSnapshotUrl
  - nexusUsername
  - nexusPassword
  
- 4.由于全局依赖代码生成器和bom，但是bom和代码生成器没发布到中央仓库，所以先需要将bom和代码生成器发布到私服
  - 设置gradle.properties 设置generatorEnable=false
  - 发布bom gradlew :bom:snail-platform-dependencies:publish
  - 发布generator gradlew :plugin-source:generator:upload
  - 将generatorEnable改为true

- 5.其它配置
  - build-conf/configuration.gradle 中包含了 代码生成器配置 根据提示自行配置后执行gradlew :plugin:generator-tool:generate可代码生成

- 6.构建，发布到私服
  - gradlew clean
  - 不走单元测试 gradlew build -x test
  - gradlew upload

技术栈
=====
- 编译工具[gradle-6.7.1](https://docs.gradle.org/current/userguide/userguide.html)
- jdk版本 openJdk1.8+
- swagger 接口文档
- spring-boot 2.3.3
- jackson   
- mysql 5.7
- redis 5.6

编码规范
=====
- 编码规范 统一使用[阿里巴巴泰山版java开发规范](./knowledge/阿里巴巴泰山版java开发手册.pdf)

- 领域模型
  - 领域模型命名不遵循规范全部驼峰命名 避免复数形式的校验
  - Do（Data Object）：此对象与数据库表结构一一对应，通过 DAO 层向上传输数据源对象。
  - Dto（Data Transfer Object）：数据传输对象，Service 或 Manager 向外传输的对象。
  - Bo（Business Object）：业务对象，可以由 Service 层输出的封装业务逻辑的对象。
  - Query：数据查询对象，各层接收上层的查询请求。注意超过 2 个参数的查询封装，禁止使用 Map 类
    来传输。
  - Vo（View Object）：显示层对象，通常是 Web 向模板渲染引擎层传输的对象
  - 另在简单的业务中 可以统一用Do指代上述全部模型 如果有特殊需要再另建模型 避免模型过多导致代码复杂度提升 不易于维护


gradle
====
- queryDsl gradle
  - [plugin](https://github.com/ewerk/gradle-plugins)
  - [documentation](http://www.querydsl.com/static/querydsl/4.1.4/reference/html/ch03s03.html)
- spring控制版本依赖
  - [官方文档](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/)
  - [补充文档](https://segmentfault.com/a/1190000019622387)
  
开发工具设置
=====
- IDEA 编译gradle乱码
```
在VM设置中追加-Dfile.encoding=utf-8并重启
-->help-->Edit Custom VM Options-->-Dfile.encoding=utf-8
```

- lambda https://www.cnblogs.com/polary/p/13223992.html


- [swagger](http://springfox.github.io/springfox/docs/current/)

- clean     gradlew clean
- 发布bom    gradlew :bom:snail-platform-dependencies:publish
- 发布plugin-source gradlew :plugin-source:generator:upload
- 打包
  - 走单元测试 gradlew build 
  - 不走单元测试 gradlew build -x test
- 发布到私服  gradlew upload
- 代码生成 gradlew :plugin:generator-tool:generate