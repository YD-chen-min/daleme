package com.yandan.demo.service;

import com.yandan.demo.dataobject.WorkInfo;
import com.yandan.demo.service.impl.UserServiceImpl;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Create by yandan
 * 2020/12/21  23:08
 */
@Service
public class SMSService {
    @Autowired
    private UserServiceImpl userService;
    private static OkHttpClient client=new OkHttpClient();
    public int sendMessage(String workIds){
        List<String> idList=Arrays.asList(workIds.split(";"));
        String text="【李俊然test】您的验证码是11111。如非本人操作，请忽略本短信";
        int count=0;
        for(String id :idList){
            WorkInfo workInfo=userService.getUserByWorkId(id);
            if(workInfo!=null&&workInfo.getPhoneNumber()!=null&&!"".equals(workInfo.getPhoneNumber())){
                FormBody formBody=new FormBody.Builder().
                        add("apikey","14ed7814cb79725101f44787db3131c7")
                        .add("mobile",workInfo.getPhoneNumber())
                        .add("text",text)
                .build();
                Request request=new Request.Builder().url("https://sms.yunpian.com/v2/sms/single_send.json")
                        .addHeader("Accept","application/json;charset=utf-8;")
                        .addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8;")
                        .post(formBody)
                        .build();
                Call call=client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        System.out.println(response.body().string());
                    }
                });
                count++;
            }
        }
        return count;
    }
}
