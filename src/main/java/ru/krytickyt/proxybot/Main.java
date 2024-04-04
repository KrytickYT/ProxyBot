package ru.krytickyt.proxybot;


import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {
    public static JDA getJda() {
        return jda;
    }

    private static JDA jda;

    public static String[] proxyUrlList = {
            "https://api.openproxylist.xyz/socks5.txt", 
            "https://api.openproxylist.xyz/socks4.txt", 
            "https://raw.githubusercontent.com/vakhov/fresh-proxy-list/master/socks4.txt", 
            "https://raw.githubusercontent.com/vakhov/fresh-proxy-list/master/socks5.txt", 
            "https://raw.githubusercontent.com/casals-ar/proxy-list/main/http", 
            "https://raw.githubusercontent.com/TheSpeedX/SOCKS-List/master/socks5.txt", 
            "https://raw.githubusercontent.com/TheSpeedX/SOCKS-List/master/socks4.txt", 
            "https://raw.githubusercontent.com/TheSpeedX/SOCKS-List/master/http.txt"
    };
    public static void main(String[] args) {
        DownloadThread.run();
                jda = JDABuilder.createDefault("")
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new ChatMessage())
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .build();
        jda.setAutoReconnect(true);
    }
}