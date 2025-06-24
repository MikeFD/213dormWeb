package dorm.backend.demo.mapper;

import dorm.backend.demo.entity.Duty;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DutyMapper {
    @Insert("INSERT INTO duty_schedule(date, duty_person, duty_content, modified_by) " +
            "VALUES(#{duty.date}, #{duty.dutyPerson}, #{duty.dutyContent}, #{userId})")
    void insertDuty(@Param("duty") Duty duty, @Param("userId") String userId);

    @Update("UPDATE duty_schedule SET " +
            "duty_person = #{duty.dutyPerson}, " +
            "duty_content = #{duty.dutyContent}, " +
            "modified_by = #{username} " +
            "WHERE date = #{duty.date}")
    void updateDuty(@Param("duty") Duty duty, @Param("username") String username);

    @Select("SELECT " +
            "date AS date, " +
            "duty_person AS dutyPerson, " +
            "duty_content AS dutyContent " +
            "FROM duty_schedule " +
            "WHERE date = #{date}")
    Duty getDutyByDate(Integer date);

    @Select("SELECT " +
            "date AS date, " +
            "duty_person AS dutyPerson, " +
            "duty_content AS dutyContent " +
            "FROM duty_schedule ")
    List<Duty> getAllDuty();
}