package com.lv.community.provider;

import com.alibaba.fastjson.JSON;
import com.lv.community.dto.AccessTokenDTO;
import com.lv.community.dto.GithubUser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author lvjiangtao
 * @create 2021-06-07-19:19
 */
@Component
@Slf4j
public class GithubProvider {

    //AccessTokenDTO 里面封装了获取 token 需要的参数
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            log.info(string);

            String[] accesstoken1 = string.split("&");
            String token = accesstoken1[0].split("=")[1];
            System.out.println("token--->"+token);


            return token;
        } catch (IOException e) {
        }

        return null;
    }
    //通过token  获取  user信息
    public GithubUser getUser(String accessToken) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization","token "+accessToken)
                .build();

        Response response = client.newCall(request).execute();
        String string = response.body().string();
        GithubUser githubUser = JSON.parseObject(string, GithubUser.class);

        return githubUser;
    }
}