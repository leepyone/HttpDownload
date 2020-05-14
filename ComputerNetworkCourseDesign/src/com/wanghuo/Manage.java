package com.wanghuo;

import com.wanghuo.Socket.SendRequest;
import com.wanghuo.URL.url;
import com.wanghuo.tool.urlAnalysis;

import java.io.IOException;
import java.net.UnknownHostException;

public class Manage {
    private static void downloadFile(String url, int ThreadNumber,String localPath){
        url url1 = urlAnalysis.analysisUrl(url);
        SendRequest sendRequest = new SendRequest(url1);
//        SendRequest sendRequest1  = new SendRequest(url1,ThreadNumber, localPath);
//        sendRequest1.run();
        try {
            sendRequest.SendGet(ThreadNumber, localPath);
        }catch (UnknownHostException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        String url = "http://wppkg.baidupcs.com/issue/netdisk/yunguanjia/BaiduNetdisk_6.9.6.9.exe";

        String url = "https://dldir1.qq.com/music/clntupate/QQMusic_YQQWinPCDL.exe";
//        String url ="https://media.w3.org/2010/05/sintel/trailer.mp4";
//        String url="http://vfx.mtime.cn/Video/2019/03/21/mp4/190321153853126488.mp4";
//        url url1 = urlAnalysis.analysisUrl(url);
//        SendRequest sendRequest1  = new SendRequest(url1,3, "D:\\test");
//        sendRequest1.run();
        downloadFile(url,3,"D:\\test");
    }

}
