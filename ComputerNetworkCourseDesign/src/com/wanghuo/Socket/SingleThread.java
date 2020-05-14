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
            System.out.println("线程"+this.currentIndex+"开始从服务器获取数据！");
            while((len=bufferedInputStream.read(buffer))!=-1){
//                downLoadFile.getStartPos()[currentIndex]+=len;
//                System.out.println("Thread:"+currentIndex+" is running!");
//                localFile.write(buffer,0,len);
                file.write(buffer,0,len);
                if(count%20==0)
                    Thread.yield();
            }
            System.out.println("线程"+this.currentIndex+"数据获取完成开始写入到本地文件中！");
            long fileLen =file.length();
            long number = fileLen-(endPos-startPos+1);
//            localFile.setPointer(startPos);
            System.out.println("Thread"+this.currentIndex+" get dataSize:"+fileLen);
            System.out.println("Thread"+this.currentIndex+" startPos:"+startPos+" endPos:"+endPos);
            file.seek(number);
            long sumSize=0;
            String partFileName = localFile.getLocalPath()+this.currentIndex+".bin";
            RandomAccessFile partFile = new RandomAccessFile(partFileName,"rwd");
            while((len=file.read(buffer))!=-1){
                sumSize+=len;
                partFile.write(buffer,0,len);
            }
            System.out.println("线程"+this.currentIndex+" 写入本地文件数据的大小为： dataSize:"+sumSize);
            this.isDone =true;
            socket.close();
        }catch(UnknownHostException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
//            try {
//                socket.close();
//            }catch (IOException e){
//                e.printStackTrace();
//            }
        }

    }
    public boolean getIsDone(){
        return  this.isDone;
    }
}
