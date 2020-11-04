package com.joe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joe.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select tr.role_name from t_role tr,t_user_role tur,t_user tu where tr.id = tur.role_id and tu.id = #{userId}")
    List<String> findRolesByUserId(@Param("userId") int userId);
}
