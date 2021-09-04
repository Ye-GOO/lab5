package com.dgut.lab5.controller;

import com.dgut.lab5.bean.RiskArea;
import com.dgut.lab5.service.RiskAreaService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RiskAreaController {

    @Autowired
    private RiskAreaService riskAreaService;

    @GetMapping("/riskarea")
    public String riskarea() {
        return "riskarea";
    }

    @GetMapping("/updateArea")
    public String updateArea(Model model) {
        Date date = new java.sql.Date(new java.util.Date().getTime());
        List<RiskArea> riskAreaList = riskAreaService.findRiskAreaByDate(date);
        model.addAttribute("riskAreaList", riskAreaList);
        return "updateArea";
    }

    @GetMapping("/findRiskArea")
    @ResponseBody
    public String findRiskArea(Date date) {
        List<RiskArea> riskAreas = riskAreaService.findRiskAreaByDate(date);
        for (RiskArea r : riskAreas) {
            r.setDate(null);
        }
        String jsonString = JSONArray.fromObject(riskAreas).toString();
        return jsonString;
    }

    @PostMapping("/submit")
    public String update(HttpServletRequest request, Model model) {
        String add = request.getParameter("add");
        List<RiskArea> riskAreaList = new ArrayList<>();

        String count = request.getParameter("count");
        int j=0;
        if(count!=null)
            j = Integer.parseInt(count);
        for (int i = 0; i < j; i++) {
            String province = request.getParameter("province" + i);
            String city = request.getParameter("city" + i);
            String location = request.getParameter("location" + i);
            String level = request.getParameter("level" + i);
            if (level == null)
                continue;
            RiskArea riskArea = new RiskArea(null, null, province, city, location, level);
            riskAreaList.add(riskArea);
        }
        if(add!=null){
            riskAreaList.add(new RiskArea());
        }
        else{
            boolean flag= true;
            for (RiskArea riskArea : riskAreaList){
                if(riskArea.getProvince().equals("省份")||riskArea.getCity().equals("城市")||riskArea.getLocation().equals("")) {
                    model.addAttribute("error", "请补充完整信息");
                    flag=false;
                    break;
                }
            }
            if(flag){
                Date date = new Date(new java.util.Date().getTime());
                riskAreaService.deleteRiskAreaByDate(date);
                for (RiskArea riskArea : riskAreaList){
                    riskArea.setDate(date);
                    riskAreaService.addRiskArea(riskArea);
                }
                model.addAttribute("success", "修改成功");
            }

        }
        model.addAttribute("riskAreaList",riskAreaList);
        return "updateArea";
    }


}


