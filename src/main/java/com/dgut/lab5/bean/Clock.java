package com.dgut.lab5.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clock {
    private Integer clockid;
    private String account;
    private Date date;
    private String physicalcondition;
    private String reachkeyareas;
    private String contactpersonnel14;
    private String contactpersonnel;
    private String quarantine;
    private String inDG;
    private String province;
    private String city;
    private String temperature;
    private String channel;

}
