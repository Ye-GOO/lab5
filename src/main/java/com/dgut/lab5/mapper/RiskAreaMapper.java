package com.dgut.lab5.mapper;

import com.dgut.lab5.bean.NewPeriod;
import com.dgut.lab5.bean.RiskArea;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

@Mapper
public interface RiskAreaMapper {



    @Select("select * from riskarea where date = #{date}")
    public List<RiskArea> findRiskAreaByDate(Date date);

    @Select("select * from riskarea ")
    public List<RiskArea> findAllRiskArea();

    @Insert("insert into riskarea values(null,#{date},#{province},#{city},#{location},#{level})")
    public void addRiskArea(RiskArea riskArea);

    @Delete("delete from riskarea where date = #{date}")
    public void deleteRiskAreaByDate(Date date);
}
