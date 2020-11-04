package com.joe.mapper;
 
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joe.entity.Users;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<Users> {

}
