package com.dgut.lab5.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiskArea {
    private Integer riskareaId;
    private Date date;
    private String province;
    private String city;
    private String location;
    private String level;
}
