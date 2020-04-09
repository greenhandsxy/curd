package com.example.curd.service;

import com.alibaba.fastjson.JSONObject;
import com.example.curd.dao.CreateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreatService {

    @Autowired
    CreateDao createDao;
    public String create(String postJson){
        JSONObject jsonObject=JSONObject.parseObject(postJson);
        String data=jsonObject.getString("data");
        createDao.insert(data);
        return "添加成功";
    }
}
