# wheel-tool
## Java 生态相关架构整合以及工具集整理
1. 整合Redis
2. 整合Mybatis
3. 整合Mail
4. Spring定时器
5. 线程池相关优化配置
6. Spring缓存管理器以及缓存管理
```shell
src
├─main
│  ├─java # 公共配置
│  |  └─com.tool
│  |      ├─mail # 邮件相关
│  |      ├─module # 
│  |      ├─redis # Redis 相关 
│  |      ├─service # 公共服务类相关 
│  |      ├─thread # 线程池工具相关 
│  |      ├─timer # Spring 定时定时器相关 
│  |      └─utils # 工具包相关 
│  |
│  └─resourecs # 资源文件
│         ├─templates # 模板相关文件夹
│         │     └─mail # 邮件模块文件      
│         └─application.yml # 配置文件 
└─test # 测试模块
   └─java 
       └─ com.tool # 测试模块包路径
               
```
