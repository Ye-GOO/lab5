package com.dgut.lab5.controller;

import com.dgut.lab5.bean.Clock;
import com.dgut.lab5.bean.User;
import com.dgut.lab5.utils.AddressUtils;
import com.dgut.lab5.service.ClockService;
import com.dgut.lab5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;

@Controller
public class ClockController {
    @Autowired
    private ClockService clockService;
    @Autowired
    private UserService userService;

    @PostMapping("/clock")
    public String clock(HttpServletRequest request, Model model,HttpSession session){

        User user = (User)session.getAttribute("user");
        String account = user.getAccount();

        session.setAttribute("flag","0");

        String certificates= request.getParameter("certificates");
        String certificatesNo = request.getParameter("certificatesNo");
        String campus = request.getParameter("campus");
        String phonenumber = request.getParameter("phonenumber");
        String emergencycontact = request.getParameter("emergencycontact");
        String emergencynumber = request.getParameter("emergencynumber");
        String physicalcondition = request.getParameter("physicalcondition");
        String reachkeyareas = request.getParameter("reachkeyareas");
        String contactpersonnel14 = request.getParameter("contactpersonnel14");
        String contactpersonnel = request.getParameter("contactpersonnel");
        String quarantine = request.getParameter("quarantine");
        String inDG = request.getParameter("inDG");
        String province = request.getParameter("province");
        String city= request.getParameter("city");
        String address_province = request.getParameter("address_province");
        String address_city= request.getParameter("address_city");
        String street = request.getParameter("street");
        String temperature = request.getParameter("temperature");
        String channel = request.getParameter("channel");

        user.setCertificates(certificates);
        user.setCertificatesNo(certificatesNo);
        user.setCampus(campus);
        user.setPhonenumber(phonenumber);
        user.setEmergencycontact(emergencycontact);
        user.setEmergencynumber(emergencynumber);
        user.setAddressProvince(address_province);
        user.setAddressCity(address_city);
        user.setStreet(street);


        if (reachkeyareas.equals("否"))
            channel="";
        if (inDG.equals("是，在莞")){
            province="广东省";
            city="东莞市";
        }

        Date date = new java.sql.Date(new java.util.Date().getTime());
        Clock clock = new Clock(null,account,date,physicalcondition,reachkeyareas,contactpersonnel14,contactpersonnel,quarantine,inDG,province,city,temperature,channel);

        session.setAttribute("user",user);
        session.setAttribute("clock",clock);

        if(certificatesNo.equals("") || phonenumber.equals("") || emergencycontact.equals("")
                || emergencynumber.equals("") || physicalcondition.equals("") || street.equals("") || temperature.equals("")
                || address_city.equals("城市") || address_province.equals("省份")){
            session.setAttribute("flag","1");
            return "redirect:/";
        }

        if(!inDG.equals("是，在莞")){
            if(city.equals("城市") || province.equals("省份")) {
                session.setAttribute("flag", "1");
                return "redirect:/";
            }
        }

        if(!inDG.equals("是，在莞")&&city.equals("东莞市")){
            session.setAttribute("flag","2");
            return "redirect:/";
        }


        String address = AddressUtils.getProvinceName();   //判断定位与输入的位置是否相同
        System.out.println(address);
        System.out.println(province+city);
        if (!address.equals(province+city))
        {
            session.setAttribute("flag","3");
            return "redirect:/";
        }

        clockService.clock(clock);
        userService.update(user);
        user = userService.findByAccount(account);
        session.setAttribute("user",user);
        return "redirect:/";
    }

}
