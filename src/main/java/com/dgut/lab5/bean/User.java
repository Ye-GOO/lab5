package com.dgut.lab5.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer userId;
    private String userName;
    private String account;
    private String password;
    private String certificates;
    private String certificatesNo;
    private String identity;
    private String campus;
    private String college;
    private String classes;
    private String addressProvince;
    private String addressCity;
    private String street;
    private String phonenumber;
    private String emergencycontact;
    private String emergencynumber;
    private Integer clocks;
    private List<Clock> clockList;
}
