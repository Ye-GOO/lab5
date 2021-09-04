package com.dgut.lab5.mapper;

import com.dgut.lab5.bean.Clock;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.sql.Date;
import java.util.List;

@Mapper
public interface ClockMapper {
    @Select("select * from clock where account = #{account} and date = (SELECT MAX(date) FROM clock where account = #{account} )")
    public Clock findLatestClockByAccount(String account);

    @Insert("insert into clock values(null,#{account},#{date},#{physicalcondition},#{reachkeyareas},#{contactpersonnel14},#{contactpersonnel},#{quarantine},#{inDG},#{province},#{city},#{temperature},#{channel})")
    public void clock(Clock clock);

    @Select("select * from clock where account = #{account} and date = #{date}")
    public Clock findTodayClockByAccount(String account, Date date);

    @Select("select * from clock where date = #{date}")
    public List<Clock> findAllClockByDate(Date date);
}
