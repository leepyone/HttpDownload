package com.wanghuo;

import com.sun.org.apache.bcel.internal.generic.ANEWARRAY;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_DARKENPeer;
import com.wanghuo.Socket.SendRequest;
import com.wanghuo.URL.url;
import com.wanghuo.tool.urlAnalysis;

import javax.imageio.event.IIOWriteProgressListener;
import java.io.IOException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Manage {
    private static void downloadFile(String url, int ThreadNumber,String localPath){
        url url1 = urlAnalysis.analysisUrl(url);
        SendRequest sendRequest = new SendRequest(url1);
//        SendRequest sendRequest1  = new SendRequest(url1,ThreadNumber, localPath);
//        sendRequest1.run();
        try {
            sendRequest.SendGet(ThreadNumber, localPath);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void sendPostRequest(String urlString){
        Scanner sc  = new  Scanner(System.in);
        List<String> lineList = new ArrayList<>();
//        String line ="";
//        System.out.println("请输入提交信息的网站");
//        String urlString  = sc.nextLine();
        System.out.println("请输入提交的信息：");
        while(sc.hasNextLine()){

//            String line ="";
//            try {
//                line = URLEncoder.encode(sc.nextLine(), "utf-8");
//            }catch (IOException e){
//                e.printStackTrace();
//            }
            String line = sc.nextLine();
            if(line.isEmpty())
                break;
            lineList.add(line);
        }
        url url = urlAnalysis.analysisUrl(urlString);
        SendRequest sendRequest = new SendRequest(url);
        sendRequest.SendPost(lineList);
    }

    public static void main(String[] args) {
//        String url = "http://wppkg.baidupcs.com/issue/netdisk/yunguanjia/BaiduNetdisk_6.9.7.4.exe";
//        String url = "http://kd.269.net/200.zip";

//        too slow
//        String url = "http://123.57.238.68:8090/upload/2020/5/%E8%AF%BE%E4%BB%B6-95ae25efd1af414a800155e32742997e.zip";

//        String url = "https://dldir1.qq.com/music/clntupate/QQMusicSetup.exe";
//        String url ="http://dldir1.qq.com/music/clntupate/QQMusic72282.apk";

//        ok
//        String url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";

//        String url ="http://123.57.238.68:8090/upload/2020/3/Fight.Club-92c4a9c729bb403a9900e4f3a6c6a3e5.jpg";
//        String url ="https://media.w3.org/2010/05/sintel/trailer.mp4";
//        String url="http://vfx.mtime.cn/Video/2019/03/21/mp4/190321153853126488.mp4";
//        url url1 = urlAnalysis.analysisUrl(url);
//        SendRequest sendRequest1  = new SendRequest(url1,3, "D:\\test");
//        sendRequest1.run();
//        downloadFile(url,2,"D:\\test");
        while(true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("输入1发送GET请求，输入2发送POST请求！");
            int a = sc.nextInt();
            if (a == 1) {
                Scanner scanner = new Scanner( System.in);
                System.out.println("请输入提交信息的网站网址,下载的线程数,请输入保存本地的位置");
                List<String> stringList = new ArrayList<>();
                while(scanner.hasNextLine()){
                    String line = scanner.nextLine();
                    if(line.isEmpty())
                        break;
                    stringList.add(line);
                }
//                System.out.println("请输入提交信息的网站网址");
//                String urlString  = sc.nextLine();
//                System.out.println("请输入下载的线程数：");
//                int ThreadNumbers  = Integer.parseInt(sc.nextLine());
//                System.out.println("请输入保存本地的位置：");
//                String localPath = sc.nextLine();
                String urlString = stringList.get(0);
                int ThreadNumbers = Integer.parseInt(stringList.get(1));
                String localPath = stringList.get(2);
                downloadFile(urlString,ThreadNumbers,localPath);
                break;
            } else if (a == 2) {
                Scanner scanner = new Scanner( System.in);
                System.out.println("请输入提交信息的网站网址");
                String urlString  = scanner.nextLine();
                sendPostRequest(urlString);
                break;
            } else {
                System.out.println("输入的不规范，请从从新输入！");
                continue;
            }
        }
    }

}
