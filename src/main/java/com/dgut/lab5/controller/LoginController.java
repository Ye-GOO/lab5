package com.dgut.lab5.controller;

import com.dgut.lab5.bean.Clock;
import com.dgut.lab5.bean.RiskArea;
import com.dgut.lab5.bean.User;
import com.dgut.lab5.mapper.ClockMapper;
import com.dgut.lab5.service.ClockService;
import com.dgut.lab5.service.RiskAreaService;
import com.dgut.lab5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClockService clockService;

    @Autowired
    private RiskAreaService riskAreaService;

    @GetMapping("/logout")
    public String logout(HttpServletResponse response, HttpSession session) {
        session.setAttribute("user", null);
        session.setAttribute("flag", null);
        session.setAttribute("fdate", null);
        session.setAttribute("cdate", null);
        Cookie cookie = new Cookie("account", "none");
        response.addCookie(cookie);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(HttpServletResponse response, HttpSession session) {
        return "login";
    }


    @GetMapping("/")
    public String login(Model model, HttpServletRequest request, HttpSession session) {
        Cookie[] cookies = request.getCookies();
        User user = (User) session.getAttribute("user");
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                if (user != null)
                    break;
                if (cookie.getName().equals("account") && !cookie.getValue().equals("none")) {
                    user = userService.findByAccount(cookie.getValue());
                    session.setAttribute("user",user);
                    break;
                }
            }
        }

        if (user == null) {
            return "login";
        }


        if(user.getIdentity().equals("管理员"))
            return "administration";

        Date date = new java.sql.Date(new java.util.Date().getTime());
        List<RiskArea> riskAreas = riskAreaService.findRiskAreaByDate(date);
        List<RiskArea> highRiskAreas = new ArrayList<>();
        List<RiskArea> midRiskAreas = new ArrayList<>();
        List<RiskArea> lowRiskAreas = new ArrayList<>();

        Iterator<RiskArea> riskArea = riskAreas.iterator();
        while(riskArea.hasNext()){
            RiskArea r = riskArea.next();
            if(r.getLevel().equals("高风险地区"))
                highRiskAreas.add(r);
            if(r.getLevel().equals("中风险地区"))
                midRiskAreas.add(r);
            if(r.getLevel().equals("低风险地区"))
                lowRiskAreas.add(r);
        }
        model.addAttribute("highRiskAreas", highRiskAreas);
        model.addAttribute("midRiskAreas", midRiskAreas);
        model.addAttribute("lowRiskAreas", lowRiskAreas);

        model.addAttribute("user", user);

        Clock clock = clockService.findLatestClockByAccount(user.getAccount());
        if (clock == null)
            model.addAttribute("clock", new Clock());
        else
            model.addAttribute("clock", clock);

        Clock clock2 = clockService.findTodayClockByAccount(user.getAccount(), date);
        if (clock2 == null)
            model.addAttribute("message", "你今天未打卡");
        else {
            model.addAttribute("no_sub", 1);
            model.addAttribute("message", "你已连续打卡" + user.getClocks() + "天");
        }
        model.addAttribute("date", date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = String.valueOf(new java.util.Date().getTime());
        time = (Long.parseLong(time) - 1000 * 60 * 60 * 24 * 14) + "";
        java.util.Date t = new java.util.Date();
        java.util.Date p = new java.util.Date(Long.valueOf(time));
        String pass = sdf.format(p);
        String today = sdf.format(t);
        model.addAttribute("pass", pass);
        model.addAttribute("today", today);

        String flag = (String)session.getAttribute("flag");
        if(flag!=null) {
            Clock clock3 = (Clock) session.getAttribute("clock");
            model.addAttribute("clock", clock3);
            if (flag.equals("1")) {
                model.addAttribute("error", "信息不能为空");
                return "index";
            }
            if(flag.equals("2")){
                model.addAttribute("error", "16和16.1有逻辑错误");
                return "index";
            }
            if(flag.equals("3")){
                model.addAttribute("error", "输入位置与定位不符");
                return "index";
            }
        }

        return "index";

    }

    @PostMapping("/verify")
    public String verify(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");
        User user = userService.find(account, password);
        session.setAttribute("user", user);
        if (user != null) {
            if (rememberMe != null) {
                Cookie cookie = new Cookie("account", account);
                cookie.setMaxAge(60 * 60 * 24 * 30);                                //30天免登录
                response.addCookie(cookie);
            }
            return "redirect:/";
        } else {
            model.addAttribute("error","登录信息错误");
            return "login";
        }
    }
}
