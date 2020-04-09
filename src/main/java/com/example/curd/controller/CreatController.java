package com.example.curd.controller;

import com.example.curd.service.CreatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreatController {


    @Autowired
    CreatService creatService;

    @GetMapping
    @RequestMapping("/create")
    public String create(@RequestBody String postJson){
        creatService.create(postJson);
     return "增加成功";
    }


}

