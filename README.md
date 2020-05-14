# HttpDownload
## 2020年5月14
  基于socket的http多线程下载，传入的url（这个url必须是可以下载的、请求资源的连接）、下载的线程数、保存的本地位置这三个参数，然后通过解析url，获取host、path、port等信息来组装报文。
  先发送head请求来获得请求资源的的基本信息，然后根据线程数，和range字头来的发送多个请求来获取数据。结束完成后将各个文件块整合。
  现在只对MP4、.exe文件进行了测试，暂时可以下载下来。
  还缺少的功能有：
- 文件正确性即请求的阶段资源有没有更新的校验
- 对于每次的分块的请求的响应报文没有做正确性的检验
- 断线续传的功能
- 是先边下载边整合还是下载完后一起整合，整合的文件没有删除掉
- 下载速度较慢
