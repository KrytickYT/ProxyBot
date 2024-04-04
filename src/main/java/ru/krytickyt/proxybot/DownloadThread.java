package ru.krytickyt.proxybot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class DownloadThread {
    private static ArrayList<DownloadThread.IProxy> proxies;
    private static final long updateSeconds = 60;

    public static void run() {
        proxies = new ArrayList<>();
        new Thread(() -> {
            while (true) {
                try {
                    proxies.clear();
                    for(String url : Main.proxyUrlList) {
                        try {
                            URL website = new URL(url);
                            URLConnection connection = website.openConnection();
                            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36");
                            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            String inputLine;
                            while ((inputLine = in.readLine()) != null) {
                                getProxies().add(new IProxy(inputLine, (inputLine.contains(":80") ? "http" : getUrlProxyType(url))));
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                    Thread.sleep(updateSeconds * 1000L);
                } catch (Exception exception) {}
            }
        }).start();
    }

    public static ArrayList<IProxy> getProxies() {
        return proxies;
    }

    public static class IProxy {
        private String proxy;
        private String type;

        public IProxy(String proxy, String type) {
            this.proxy = proxy;
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public String getProxy() {
            return proxy;
        }
    }

    private static String getUrlProxyType(String url) {
        if(url.contains("socks5")) {
            return "socks5";
        } else if(url.contains("socks4")) {
            return "socks4";
        } else if(url.contains("http")) {
            return "http";
        } else {
            return "socks5";
        }
    }
}
