package com.dgut.lab5.mapper;

import com.dgut.lab5.bean.Clock;
import com.dgut.lab5.bean.NewPeriod;
import com.dgut.lab5.bean.User;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user where account = #{account} and password= #{password}")
    public User find(String account, String password);

    @Select("select * from user where account = #{account}")
    public User findByAccount(String account);

    @Update("update user set certificates = #{certificates},certificatesNo = #{certificatesNo},campus = #{campus},phonenumber = #{phonenumber},emergencycontact = #{emergencycontact}," +
            "emergencynumber = #{emergencynumber},addressprovince = #{addressProvince},addresscity = #{addressCity},street=#{street},clocks=clocks+1 where account = #{account}")
    public void update(User user);

    @Update("update user set clocks=0 where account = #{account}")
    public void setClocks(String account);

    @Select("select * from user")
    public List<User> findAllUser();

    //select * from user u,clock c where u.account=c.account and  temperature > #{temperature} and date = #{date}"

    @Select("select * from user where account = #{account}")
    @Results({
            @Result(property = "account",column = "account"),
            @Result(property = "clockList",column = "account",
                    many = @Many(select = "com.dgut.lab5.mapper.UserMapper.findClockListByAccount"))
    })
    public User findUserWithClockListByAccount(String account);

    @Select("select * from clock where account = #{account}")
    public List<Clock> findClockListByAccount(String account);


    @Select("select * from user u,clock c where u.account = c.account and date = #{param1} and temperature > #{param2}")
    @Results({
            @Result(property = "clockList",column = "clockid",
                    many = @Many(select = "com.dgut.lab5.mapper.UserMapper.findClockListByClockid"))
    })
    public List<User> findFeverUserWithClockListByDate(Date date,double temperature);

    @Select("select * from clock where clockid = #{clockid}")
    public List<Clock> findClockListByClockid(Integer clockid);


    @Select("select * from user u,clock c where u.account = c.account and date = #{date} and reachkeyareas = '是'")
    @Results({
            @Result(property = "clockList",column = "clockid",
                    many = @Many(select = "com.dgut.lab5.mapper.UserMapper.findClockListByClockid"))
    })
    public List<User> findKeyAreaUserWithClockListByDate(Date date);



}

