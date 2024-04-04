package ru.krytickyt.proxybot;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class ChatMessage extends ListenerAdapter {
    public static long commandCooldown;

    public static long chatgptCooldown;

    public static int delay = 10;

    public static int chatgptdelay = 3;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot())
            return;

        if (event.isFromType(ChannelType.PRIVATE))
            return;


        if (event.getMessage().getContentRaw().startsWith("!")) {
            String[] args = event.getMessage().getContentRaw().substring(1).split("\\s+");
            if (args[0].equalsIgnoreCase("socks4") && event.getChannel().getId().equals("1164978058885869718")) {
                if (commandCooldown > new Timestamp(System.currentTimeMillis()).getTime()) {
                    event.getChannel().sendMessage("Подожди " + ((commandCooldown - new Timestamp(System.currentTimeMillis()).getTime()) / 1000) + " секунд перед использованем команды").queue();
                } else {
                    commandCooldown = new Timestamp(System.currentTimeMillis()).getTime() + (delay * 1000L);
                    StringBuilder sb = new StringBuilder();
                    int count = 0;
                    for (DownloadThread.IProxy iProxy : DownloadThread.getProxies()) {
                        if (iProxy.getType().equalsIgnoreCase("socks4")) {
                            sb.append(iProxy.getProxy() + "\n");
                            count++;
                        }
                    }
                    event.getChannel().sendMessage("Socks4 прокси загружены! " + count + " шт").addFiles(FileUpload.fromData(sb.toString().getBytes(), "socks4.txt")).queue();
                }
            } else if (args[0].equalsIgnoreCase("socks5") && event.getChannel().getId().equals("1164978058885869718")) {
                if (commandCooldown > new Timestamp(System.currentTimeMillis()).getTime()) {
                    event.getChannel().sendMessage("Подожди " + ((commandCooldown - new Timestamp(System.currentTimeMillis()).getTime()) / 1000) + " секунд перед использованем команды").queue();
                } else {
                    commandCooldown = new Timestamp(System.currentTimeMillis()).getTime() + (delay * 1000L);
                    StringBuilder sb = new StringBuilder();
                    int count = 0;
                    for (DownloadThread.IProxy iProxy : DownloadThread.getProxies()) {
                        if (iProxy.getType().equalsIgnoreCase("socks5")) {
                            sb.append(iProxy.getProxy() + "\n");
                            count++;
                        }
                    }
                    event.getChannel().sendMessage("Socks5 прокси загружены! " + count + " шт").addFiles(FileUpload.fromData(sb.toString().getBytes(), "socks5.txt")).queue();
                }
            } else if (args[0].equalsIgnoreCase("http") && event.getChannel().getId().equals("1164978058885869718")) {
                if (commandCooldown > new Timestamp(System.currentTimeMillis()).getTime()) {
                    event.getChannel().sendMessage("Подожди " + ((commandCooldown - new Timestamp(System.currentTimeMillis()).getTime()) / 1000) + " секунд перед использованем команды").queue();
                } else {
                    commandCooldown = new Timestamp(System.currentTimeMillis()).getTime() + (delay * 1000L);
                    StringBuilder sb = new StringBuilder();
                    int count = 0;
                    for (DownloadThread.IProxy iProxy : DownloadThread.getProxies()) {
                        if (iProxy.getType().equalsIgnoreCase("http")) {
                            sb.append(iProxy.getProxy() + "\n");
                            count++;
                        }
                    }
                    event.getChannel().sendMessage("Http прокси загружены! " + count + " шт").addFiles(FileUpload.fromData(sb.toString().getBytes(), "http.txt")).queue();
                }
            } else if (args[0].equalsIgnoreCase("all") && event.getChannel().getId().equals("1164978058885869718")) {
                if (commandCooldown > new Timestamp(System.currentTimeMillis()).getTime()) {
                    event.getChannel().sendMessage("Подожди " + ((commandCooldown - new Timestamp(System.currentTimeMillis()).getTime()) / 1000) + " секунд перед использованем команды").queue();
                } else {
                    commandCooldown = new Timestamp(System.currentTimeMillis()).getTime() + (delay * 1000L);
                    StringBuilder sb = new StringBuilder();
                    int count = 0;
                    for (DownloadThread.IProxy iProxy : DownloadThread.getProxies()) {
                        sb.append(iProxy.getProxy() + "\n");
                        count++;
                    }
                    event.getChannel().sendMessage("Http/Socks4/Socks5 прокси загружены! " + count + " шт").addFiles(FileUpload.fromData(sb.toString().getBytes(), "all.txt")).queue();
                }
            } else if (args[0].equalsIgnoreCase("chatgpt")) {
                if(event.getChannel().getId().equals("1195057165682102353") || event.getMessage().getCategory().getName().equalsIgnoreCase("Tickets") || event.getMessage().getCategory().getName().equalsIgnoreCase("ADMINS")) {
                    if (chatgptCooldown > new Timestamp(System.currentTimeMillis()).getTime()) {
                        event.getChannel().sendMessage("Уже идет процесс генерации другого ответа, подожди " + ((chatgptCooldown - new Timestamp(System.currentTimeMillis()).getTime()) / 1000) + " секунд").mentionRepliedUser(false).setMessageReference(event.getMessage()).queue();
                    } else {
                        chatgptCooldown = new Timestamp(System.currentTimeMillis()).getTime() + (chatgptdelay * 1000L);
                        StringBuilder sb = new StringBuilder();
                        for (int i = 1; i < args.length; i++) {
                            sb.append(args[i]).append(" ");
                        }
                        event.getChannel().sendTyping().queue((channel) -> {
                            try {
                                Thread.sleep(800);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            String answer = "null";
                            try {
                                answer = ChatGPT.create(sb.toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (!answer.equalsIgnoreCase("")) {
                                if (answer.length() < 1800) {
                                    event.getChannel().sendMessage(answer).mentionRepliedUser(false).setMessageReference(event.getMessage()).queue();
                                } else {
                                    event.getChannel().sendMessage("Ответ слишком длинный, поэтому он был отправлен в виде файла:").addFiles(FileUpload.fromData(answer.getBytes(), "chatgpt.txt")).mentionRepliedUser(false).setMessageReference(event.getMessage()).queue();
                                }
                            }
                        });
                    }
                }
            }
        }
        if(event.getMessage().getContentRaw().contains(event.getJDA().getSelfUser().getAsMention())) {
            if (event.getChannel().getId().equals("1195057165682102353") || event.getMessage().getCategory().getName().equalsIgnoreCase("Tickets") || event.getMessage().getCategory().getName().equalsIgnoreCase("ADMINS")) {
                if (chatgptCooldown > new Timestamp(System.currentTimeMillis()).getTime()) {
                    event.getChannel().sendMessage("Уже идет процесс генерации другого ответа, подожди " + ((chatgptCooldown - new Timestamp(System.currentTimeMillis()).getTime()) / 1000) + " секунд").mentionRepliedUser(false).setMessageReference(event.getMessage()).queue();
                } else {
                    chatgptCooldown = new Timestamp(System.currentTimeMillis()).getTime() + (chatgptdelay * 1000L);
                    String question = event.getMessage().getContentRaw().replace("<@1137327305677619302>", "").replace("@NeoWare", "");
                    event.getChannel().sendTyping().queue((channel) -> {
                        try {
                            Thread.sleep(800);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        String answer = "null";
                        try {
                            answer = ChatGPT.create(question);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (!answer.equalsIgnoreCase("")) {
                            if (answer.length() < 1800) {
                                event.getChannel().sendMessage(answer).mentionRepliedUser(false).setMessageReference(event.getMessage()).queue();
                            } else {
                                event.getChannel().sendMessage("Ответ слишком длинный, поэтому он был отправлен в виде файла:").addFiles(FileUpload.fromData(answer.getBytes(), "chatgpt.txt")).mentionRepliedUser(false).setMessageReference(event.getMessage()).queue();
                            }
                        }
                    });
                }
            }
        }
    }
}

