package com.dgut.lab5.controller;


import com.dgut.lab5.bean.Clock;
import com.dgut.lab5.bean.User;
import com.dgut.lab5.service.ClockService;
import com.dgut.lab5.service.UserService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClockService clockService;

    @GetMapping("/fever")
    public String fever(){
        return "fever";
    }

    @GetMapping("/channel")
    public String channel(){
        return "channel";
    }

    @GetMapping("/punch")
    public String punch(){
        return "punch";
    }

    @GetMapping("/findNoPunch")
    @ResponseBody
    public String findNoPunch(Date date){

        List<Clock> clocks = clockService.findAllClockByDate(date);

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

        String jsonString = JSONArray.fromObject(users).toString();
        return jsonString;
    }

    @GetMapping("/findFever")
    @ResponseBody
    public String findFever(Date date, HttpSession session){
        List<User> userList = userService.findFeverUserWithClockListByDate(date,37.3);
        for (User user : userList){
            user.getClockList().get(0).setDate(null);
        }
        String jsonString = JSONArray.fromObject(userList).toString();
        session.setAttribute("fdate",date);
        return jsonString;
    }

    @GetMapping("/findChannel")
    @ResponseBody
    public String findChannel(Date date,HttpSession session){
        List<User> userList = userService.findKeyAreaUserWithClockListByDate(date);
        System.out.println(date);
        System.out.println(userList);
        for (User user : userList){
            user.getClockList().get(0).setDate(null);
        }
        String jsonString = JSONArray.fromObject(userList).toString();
        session.setAttribute("cdate",date);
        return jsonString;
    }

    @GetMapping("/allRecord/{account}/{flag}")
    public String allRecord(@PathVariable("account") String account, @PathVariable("flag") Integer flag,Model model){
        User user = userService.findUserWithClockListByAccount(account);
        model.addAttribute("flag",flag);
        model.addAttribute("user",user);
        return "allRecord";
    }

}
