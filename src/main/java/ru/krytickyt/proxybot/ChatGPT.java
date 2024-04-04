package ru.krytickyt.proxybot;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class ChatGPT {
    public static String create(String prompt) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        String jsonBody = "{\"prompt\":\"" + prompt + "\",\"system\":\"Always talk in Russian.\",\"withoutContext\":true,\"stream\":false}";
        RequestBody body = RequestBody.create(mediaType, jsonBody);

        Request request = new Request.Builder()
                .url("https://api.binjie.fun/api/generateStream")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("origin", "https://chat.jinshutuan.com")
                .addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.5112.79 Safari/537.36")
                .addHeader("Accept-Charset", "UTF-8")
                .build();

        Response response = client.newCall(request).execute();

        String raw = response.body().string().replace("everyone", "упоминание").replace("here", "упоминание").replace("<", "").replace(">", "").replace("@", "");
        return (raw.contains(".aichatos.xyz") ? "Превышен лимит запросов к API. Подождите несколько минут и попробуйте снова" : raw);
    }
}
