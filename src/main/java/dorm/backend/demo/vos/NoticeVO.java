package dorm.backend.demo.vos;

import java.util.Date;
import lombok.Data;
@Data
public class NoticeVO {
    private Long id;
    private String title;
    private String content;
    private String priority;
    private Date createTime;
}