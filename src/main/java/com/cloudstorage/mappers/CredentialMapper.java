package com.cloudstorage.mappers;
import com.cloudstorage.models.Credentials;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS")
    List<Credentials> findAll();

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialID}")
    public Credentials findOne(int crendentialID);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    public List<Credentials> findByUserID(int userid);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES ( #{credentials.url}, #{credentials.username}, #{credentials.key}, #{credentials.password}, #{userID})")
    @Options(useGeneratedKeys = true, keyProperty = "credentials.credentialId")
    public int insert(Credentials credentials, Integer userID);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialID}")
    public int delete(int credentialID);

    @Update("UPDATE CREDENTIALS SET credentialid = #{credentialId}, username = #{credentials.username}, key = #{credentials.key}, password = #{credentials.password}, url = #{credentials.url}")
    public int update(Credentials credentials, Integer credentialId);


}