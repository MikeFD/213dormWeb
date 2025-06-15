package dorm.backend.demo.dtos;

import lombok.Data;

@Data
public class DutyDTO {
    private Integer date;          // 格式: yyyy-MM-dd
    private String dutyPerson;
    private String dutyContent;
}