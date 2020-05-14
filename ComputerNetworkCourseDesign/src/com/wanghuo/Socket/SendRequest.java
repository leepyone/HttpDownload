package com.wanghuo.Socket;

import com.wanghuo.File.DownLoadFile;
import com.wanghuo.File.LocalFile;
import com.wanghuo.Message.RequestMessage;
import com.wanghuo.URL.url;
import com.wanghuo.tool.errorDeal;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class SendRequest extends Thread{
    private url url;
    private DownLoadFile downLoadFile;

    private int ThreadNumbers;
    private String LocalPath;

    public SendRequest(){ }
    public SendRequest(url url){this.url =url;}
    public SendRequest(url url , int ThreadNumbers,String LocalPath){
        this.url =url;
        this.ThreadNumbers = ThreadNumbers;
        this.LocalPath = LocalPath;
    }


    private Socket Connect(){
        Socket socket=null;
        try {
            if (url.getProtocol().equalsIgnoreCase("http"))
                socket = new Socket(url.getHost(), url.getPort());
            else
                socket = (SSLSocket) ((SSLSocketFactory) SSLSocketFactory.getDefault()).createSocket(url.getHost(), url.getPort());
        }catch (UnknownHostException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        if(socket==null)
            errorDeal.errorPrint("socket 初始化错误！");
        return socket;
    }

    private void  SendHead() throws IOException {
//        根据head请求 返回的信息和url信息完成对downLoadFile的初始化
        Socket socket = Connect();
        List<String> headList = new ArrayList<>();
        OutputStream out =null;
        BufferedReader bufferedInputStream=null ;
        RequestMessage HeadMessage = new RequestMessage(url);
        try {
            out = socket.getOutputStream();
            bufferedInputStream = new BufferedReader( new InputStreamReader(socket.getInputStream(),"utf-8"));
            out.write(HeadMessage.getHeadMessage().getBytes());
            out.flush();
            String line=null;
            while(!(line = bufferedInputStream.readLine()).isEmpty()){
                headList.add(line);
            }
        }catch(UnknownHostException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }finally {
            out.close();
            bufferedInputStream.close();
        }
        HeadMessage.HeadMessageAnalysis(headList);
        downLoadFile = new DownLoadFile(url.getFileName(),HeadMessage.getTotalSize());
    }

    public void SendGet(int ThreadNumbers,String LocalPath) throws IOException {
//        先发送head请求获得 文件信息，以及判断是否能够连接上等等。。
        SendHead();
//        存储线程数
        this.downLoadFile.setThreadNumber(ThreadNumbers);
        long[] startPos =new long[ThreadNumbers];
        long[] endPos =new long[ThreadNumbers];
        downLoadFile.setEndPos(endPos);
        downLoadFile.setStartPos(startPos);
        this.downLoadFile.positionCalculate();
        this.downLoadFile.PrintFileMessage();
        RequestMessage requestMessage = new RequestMessage(url);
        long pointer = 0;
        LocalFile localFile = new LocalFile(url.getFileName(),LocalPath,pointer);
        localFile.setLength(downLoadFile.getTotalSize());
        SingleThread[] ThreadArray = new SingleThread[ThreadNumbers];
        System.out.println("开始下载！");
        long startTime=System.currentTimeMillis();
        for(int i =0;i<ThreadNumbers;i++){
            ThreadArray[i] = new SingleThread(i,Connect(),downLoadFile,localFile,requestMessage);
            Thread th = new  Thread(ThreadArray[i],"thread"+i);
            th.start();
        }
        boolean isDone=false;
        try {
            while (!isDone) {
                Thread.sleep(1000);
                isDone = true;
                for(int i =0;i<ThreadNumbers;i++){
                    isDone&=ThreadArray[i].getIsDone();
                }
//                downLoadFile.PrintFileMessage();
            }
            System.out.println("开始整合文件！");
            byte[] buffer =new byte[1024*10];
            for(int i =0;i<ThreadNumbers;i++){
                String partFileName = localFile.getLocalPath()+i+".bin";
                RandomAccessFile randomAccessFile = new RandomAccessFile(partFileName,"rwd");
                int len =0;
                while((len=randomAccessFile.read(buffer))!=-1){
                    localFile.write(buffer,0,len);
                }
            }
            long endTime=System.currentTimeMillis();
            System.out.println("file is done !");

            localFile.donePrint();
            localFile.close();
            System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
        }catch (InterruptedException e){
            e.printStackTrace();
        }


    }

    @Override
    public void run(){
        try {
            //        先发送head请求获得 文件信息，以及判断是否能够连接上等等。。
            SendHead();
//        存储线程数
            this.downLoadFile.setThreadNumber(ThreadNumbers);
            long[] startPos =new long[ThreadNumbers];
            long[] endPos =new long[ThreadNumbers];
            downLoadFile.setEndPos(endPos);
            downLoadFile.setStartPos(startPos);
            this.downLoadFile.positionCalculate();
            this.downLoadFile.PrintFileMessage();
            RequestMessage requestMessage = new RequestMessage(url);
            long pointer = 0;
            LocalFile localFile = new LocalFile(url.getFileName(),LocalPath,pointer);
            SingleThread[] ThreadArray = new SingleThread[ThreadNumbers];
            System.out.println("开始下载！");
            long startTime=System.currentTimeMillis();
            for(int i =0;i<ThreadNumbers;i++){
                ThreadArray[i] = new SingleThread(i,Connect(),downLoadFile,localFile,requestMessage);
                Thread th = new  Thread(ThreadArray[i],"thread"+i);
                th.start();
            }
            boolean isDone=false;
            try {
                while (!isDone) {
                    Thread.sleep(1000);
                    isDone = true;
                    for(int i =0;i<ThreadNumbers;i++){
                        isDone&=ThreadArray[i].getIsDone();
                    }
//                    downLoadFile.PrintFileMessage();
                }
                System.out.println("开始整合文件！");
                byte[] buffer =new byte[1024*10];
                for(int i =0;i<ThreadNumbers;i++){
                    String partFileName = localFile.getLocalPath()+"\\"+localFile.getFileName()+i;
                    RandomAccessFile randomAccessFile = new RandomAccessFile(partFileName,"rwd");
                    int len =0;
                    while((len=randomAccessFile.read(buffer))!=-1){
                        localFile.write(buffer,0,len);
                    }
                }
                long endTime=System.currentTimeMillis();
                System.out.println("file is done !");
                System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }catch (UnknownHostException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
        }




    }
}
