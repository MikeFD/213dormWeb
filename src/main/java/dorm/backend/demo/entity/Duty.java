package dorm.backend.demo.entity;

import lombok.Data;

@Data
public class Duty {
    private Integer date;          // 值日日期 (主键)，格式: yyyy-MM-dd
    private String dutyPerson;    // 值日人员
    private String dutyContent;   // 值日内容
}