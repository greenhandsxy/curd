package com.example.curd.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;

@Mapper
public interface CreateDao  {
    void insert(String data);
}
