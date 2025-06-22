package dorm.backend.demo.service;

import dorm.backend.demo.entity.Notice;
import dorm.backend.demo.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    public List<Notice> findAllNotices() {
        return noticeMapper.findAllNotices();
    }

    public Notice getNoticeById(String id) {
        return noticeMapper.getNoticeById(id);
    }
}