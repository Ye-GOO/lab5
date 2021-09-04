package com.dgut.lab5.service;

import com.dgut.lab5.bean.Clock;
import com.dgut.lab5.mapper.ClockMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class ClockService {
    @Autowired
    private ClockMapper clockMapper;

    public Clock findLatestClockByAccount(String account){
        return clockMapper.findLatestClockByAccount(account);
    }

    public Clock findTodayClockByAccount(String account, Date date){
        return clockMapper.findTodayClockByAccount(account,date);
    }

    public void clock(Clock clock){
        clockMapper.clock(clock);
    }

    public List<Clock> findAllClockByDate(Date date){
        return clockMapper.findAllClockByDate(date);
    }

}

