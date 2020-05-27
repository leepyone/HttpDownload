# HttpDownload
## 2020年5月14日
  基于socket的http多线程下载，传入的url（这个url必须是可以下载的、请求资源的连接）、下载的线程数、保存的本地位置这三个参数，然后通过解析url，获取host、path、port等信息来组装报文。
  先发送head请求来获得请求资源的的基本信息，然后根据线程数，和range字头来的发送多个请求来获取数据。结束完成后将各个文件块整合。
  现在只对MP4、.exe文件进行了测试，暂时可以下载下来。
  还缺少的功能有：
- 文件正确性即请求的阶段资源有没有更新的校验
- 对于每次的分块的请求的响应报文没有做正确性的检验
- 断线续传的功能
- 是先边下载边整合还是下载完后一起整合，整合的文件没有删除掉
- 下载速度较慢
## 2020年5月15日
 完善的功能有：
 - 每次分块得响应报文的的转状态码进行了校验
 - 使用RandomAccessFile，每个线程获取的数据先存储到文件中，然后进行状态码的解析。同时写入到目标文件的对应位置。
 - 可以把分块得数据删除
 
 还缺少的有：
 - 断点续传
 - 下载速度较慢的问题
 - 用户界面
 - 暂停的功能
## 2020年5月26日
- 测试的过程中发现有时exe下载的无法打开，下载的图片下班部分不对，考虑到两种情况可能是不同线程之间写入的顺序有问题，导致exe文件无法打开，图片文件可以打开应该是这个种类型的文件不存在校验，有部分的数据在正确的地方，然后就可以解析。而且之前测试的时候也存在正确的情况，这也验证了不同线程写入文件的顺序可能出现了问题。
- 下载的时间的构成有从服务器端获取数据的时间，然后把分块的文件整合的过程，但是观察进度发现线程都已经下载完了对应的数据但还没有转到开始整合数据，应该是keep-alive这属性使得这个连接等待了一段时间。
- 接下来先检查线程的写入顺序保证可以正确的完成文件的请求，然后在将分块的请求的message中connection属性改为closed看看能否解决。
- 最后开始完成post请求的提交，需要考虑数据提交的形式，还有如何验证。因为没有UI。。。。。
## 2020年5月27日
- 之前下载失败的原因是多线程下载了正确的数据，但是在写入的本地文件中时线程出现了错误，出现了线程的写入错误，于是我将写入的代码块上了锁，这的话就是串行的写入到文件中了，总之没有理解多线程的使用。
- 将Connection 的值调整为了close但是中间还是有一段等待的时间，而且占用最多的时间就是将分块文件中的数据整合到本地文件中，Java的url类中返回的数据好像没有包括头部信息 ，不知道是怎么实现的。如果可以解决这个问题下载的速度就可翻倍了
- 尝试了发送post请求，先搭建了一个简单的表单的提交的页面，然后发送post表单信息，观察是否可以正确的返回信息。待改善
- UI。。。。。  
