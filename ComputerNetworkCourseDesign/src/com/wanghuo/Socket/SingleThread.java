package com.wanghuo.Socket;

import com.wanghuo.File.DownLoadFile;
import com.wanghuo.File.LocalFile;
import com.wanghuo.Message.RequestMessage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.net.UnknownHostException;

public class SingleThread implements Runnable {
    private int  currentIndex;
    private Socket socket;
    private DownLoadFile downLoadFile;
    private LocalFile localFile;
    private long startPos;
    private long endPos;
    private boolean isDone;
    private RequestMessage requestMessage;
    public  SingleThread(int currentIndex, Socket socket, DownLoadFile downLoadFile, LocalFile localFile,RequestMessage requestMessage) {
        this.currentIndex = currentIndex;
        this.socket = socket;
        this.downLoadFile = downLoadFile;
        this.localFile = localFile;
        this.startPos = downLoadFile.getStartPos()[currentIndex];
        this.endPos = downLoadFile.getEndPos()[currentIndex];
        this.requestMessage = requestMessage;

    }
    public SingleThread( int currentIndex ){ this.currentIndex = currentIndex; }
    @Override
    public void run(){
//        System.out.println("进入了线程");
        BufferedOutputStream bufferedOutputStream =null;
        BufferedInputStream bufferedInputStream = null;
        String GETMessage = requestMessage.getGetMessage(startPos,endPos);
        byte[] buffer = new byte[1024*10];
        try{
            bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
            bufferedOutputStream.write(GETMessage.getBytes());
            bufferedOutputStream.flush();
            bufferedInputStream = new BufferedInputStream(socket.getInputStream());
            int len;

            RandomAccessFile file = new RandomAccessFile(localFile.getLocalPath()+this.currentIndex,"rwd");
            int count =0;
            boolean getRight =true;
            long sumSize = 0;
            int sendTimes=0;
            while(getRight) {
//                开始是先将sumSize进行初始化
                sumSize =0;
                System.out.println("线程" + this.currentIndex + "开始从服务器获取数据！");
                while ((len = bufferedInputStream.read(buffer)) != -1) {
//                downLoadFile.getStartPos()[currentIndex]+=len;
//                System.out.println("Thread:"+currentIndex+" is running!");
//                localFile.write(buffer,0,len);
                    file.write(buffer, 0, len);
                    sumSize+=len;
                    if (count % 20 == 0) {
                        long percent = sumSize*100/(endPos-startPos+1);
                        System.out.println("线程"+this.currentIndex+"已经下载的"+percent+"%");
                        Thread.yield();
                    }

                    count++;
                }
                if(!requestMessage.partRequestMessageDeal(file,this.currentIndex))
                    continue;
                synchronized (localFile) {
                    System.out.println("线程" + this.currentIndex + "数据获取完成开始写入到本地文件中！");
                    long fileLen = file.length();
                    long number = fileLen - (endPos - startPos + 1);
                    localFile.setPointer(startPos);
//                System.out.println("Thread" + this.currentIndex + " get dataSize:" + fileLen);
//                System.out.println("Thread" + this.currentIndex + " startPos:" + startPos + " endPos:" + endPos);
                    file.seek(number);

//              创建中间的part文件用来存储去除报文头的文件数据，现在直接写入到本地中不使用中间文件
//                String partFileName = localFile.getLocalPath() + this.currentIndex + ".bin";
//                RandomAccessFile partFile = new RandomAccessFile(partFileName, "rwd");
                    sumSize = 0;
                    while ((len = file.read(buffer)) != -1) {
                        sumSize += len;
                        localFile.write(buffer, 0, len);
                    }
                }
                sendTimes++;
//              判断返回的状态码是否正确，若不正确则重新发送
                getRight = !requestMessage.partRequestMessageDeal(file,this.currentIndex);
                if(sendTimes==3) {
                    System.out.println("重发3次数，请重新检查url！");
                    System.exit(0);
                }
            }
            System.out.println("线程"+this.currentIndex+" 写入本地文件数据的大小为： dataSize:"+sumSize);
//            写入完成后将文件关闭
            file.close();
            this.isDone =true;
//            socket关闭
            socket.close();
        }catch(UnknownHostException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public boolean getIsDone(){
        return  this.isDone;
    }
}
