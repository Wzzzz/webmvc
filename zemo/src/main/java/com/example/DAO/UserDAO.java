package com.example.DAO;

import java.util.Date;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.User;

@Mapper
public interface UserDAO {
    String TABLE_NAME = " user ";
    String INSERT_FIELDS = " name, password, information, datetime ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{name},#{password},#{information},#{datetime})"})
    int addUser(User user);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id = #{id}"})
    User selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where name = #{name}"})
    User selectByName(String name);
    
    
    @Update({"update ", TABLE_NAME, " set information=#{information} where id=#{id}"})
    void updateMessage(@Param("information") String information, @Param("id") int id);
    
    @Update({"update ", TABLE_NAME, " set datetime = #{datetime} where id = #{id}"})
    void updateDatetime(@Param("datetime") Date datetime,@Param("id") int id);
    
    @Delete({"delete from ", TABLE_NAME, " where id = #{id}"})
    void deleteById(int id);
    
}
