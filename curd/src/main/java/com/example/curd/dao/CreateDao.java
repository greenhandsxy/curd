package com.example.curd.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CreateDao {
    void insert(String data);
}
