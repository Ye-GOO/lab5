package com.dgut.lab5.service;

import com.dgut.lab5.bean.Clock;
import com.dgut.lab5.bean.RiskArea;
import com.dgut.lab5.bean.User;
import com.dgut.lab5.mapper.RiskAreaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

@Service
public class RiskAreaService {
    @Autowired
    private RiskAreaMapper riskAreaMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ClockService clockService;

    public List<RiskArea> findAllRiskArea(){
        return riskAreaMapper.findAllRiskArea();
    }

    public List<RiskArea> findRiskAreaByDate(Date date){
        return riskAreaMapper.findRiskAreaByDate(date);
    }

    public void addRiskArea(RiskArea riskArea){
        riskAreaMapper.addRiskArea(riskArea);
    }

    public void deleteRiskAreaByDate(Date date){
        riskAreaMapper.deleteRiskAreaByDate(date);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void copy(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = String.valueOf(new java.util.Date().getTime());
        time = (Long.parseLong(time) - 1000 * 60 * 60 * 24 ) + "";
        Date t = new Date(new java.util.Date().getTime());
        Date y = new Date(new java.util.Date(Long.valueOf(time)).getTime());
        List<RiskArea> riskAreaList = riskAreaMapper.findRiskAreaByDate(y);
        if(riskAreaList!=null) {
            for (RiskArea riskArea : riskAreaList) {
                riskArea.setDate(t);
                riskAreaMapper.addRiskArea(riskArea);
            }
        }

        List<Clock> clocks = clockService.findAllClockByDate(y);
        List<User> users = userService.findAllUser();
        Iterator<User> user = users.iterator();
        while(user.hasNext()){
            User u = user.next();
            Iterator<Clock> clock = clocks.iterator();
            while (clock.hasNext()){
                Clock c = clock.next();
                if(u.getAccount().equals(c.getAccount()))
                    user.remove();
            }
        }
        if(users!=null)
            for(User user1:users){
                user1.setClocks(0);
            }


    }


}
