package com.dgut.lab5.bean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import  java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewPeriod{
    public Integer newPeriodId;
    private Date start;
    private Date end;
    private List<String> locations;
}
