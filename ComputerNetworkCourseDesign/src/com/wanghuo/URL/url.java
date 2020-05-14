package com.wanghuo.URL;

import java.util.Map;

public class url {
    private String protocol;
    private String host;
    private int port;
    private String fileName;
    private Map<String, String > parameters;
    private String path;

    public url(){}
    public url(String protocol, String host, int port, String fileName,String path) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.path = path;
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }
}
