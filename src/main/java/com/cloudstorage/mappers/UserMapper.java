package com.cloudstorage.mappers;
import com.cloudstorage.models.User;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM USERS")
    List<User> getAllUsers();

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUser(String username);

    @Select("SELECT * FROM USERS WHERE userid = #{userid}")
    User getUserById(Integer userid);

    @Insert("INSERT INTO USERS(username, salt, password, firstname, lastname) VALUES (#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userID")
    int insert(User user);

    @Delete("DELETE FROM USERS WHERE userid = #{userid}")
    void delete(Integer userid);

}
