package com.example.DAO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.Session;

@Mapper
public interface SessionDAO {
    String TABLE_NAME = " session ";
    String INSERT_FIELDS = " userId, ticket, expired, status ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{ticket},#{expired},#{status})"})
    int addSession(Session session);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where ticket=#{ticket}"})
    Session selectByTicket(String ticket);
    

    @Update({"update ", TABLE_NAME," set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket,@Param("status") int status);
    
	
	
	

}
