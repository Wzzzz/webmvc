package com.example.DAO;


import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


import com.example.model.Comment;


@Mapper
public interface  CommentDAO {
    String TABLE_NAME = " comment ";
    String INSERT_FIELDS = " userId, creatDate, userName, content ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{creatDate},#{userName},#{content})"})
    int addComment(Comment comment);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " order by creatDate desc limit #{offset},#{limit}"})
    List<Comment> selectLatestComment(@Param("offset") int offset,
    		                          @Param("limit") int limit);

	
	

}
