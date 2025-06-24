package dorm.backend.demo.controller;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.upload.FastImageFile;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import dorm.backend.demo.common.ResultVO;
import dorm.backend.demo.entity.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.github.tobato.fastdfs.*;

import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("/api/avatar")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "头像上传接口", description = "头像上传用的")
public class AvatarUploadController {

    @Autowired
    private FastFileStorageClient storageClient;

    /**
     * 上传用户头像接口
     *
     * @param file      用户上传的头像文件
     * @return          包含主图和缩略图路径的响应
     */
    @PostMapping("/upload")
    public ResultVO uploadAvatar(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal User user) {

        try (InputStream inputStream = file.getInputStream()) {
            String userId = user.getUserId();
            // 构建 FastImageFile 对象并生成缩略图
            FastImageFile fastImageFile = new FastImageFile.Builder()
                    .withThumbImage(200, 200)  // 固定尺寸缩略图
                    .withFile(inputStream, file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()))
                    .withMetaData(createMetaData(userId))
                    .build();

            // 上传到 FastDFS
            StorePath storePath = storageClient.uploadImage(fastImageFile);

            // 拼接返回路径
            String fullPath = storePath.getFullPath(); // 主图地址
            String thumbPath = fastImageFile.getThumbImagePath(storePath.getPath()); // 缩略图路径

            Map<String, String> result = new HashMap<>();
            result.put("userId", userId);
            result.put("avatarUrl", fullPath);
            result.put("thumbnailUrl", storePath.getGroup() + "/" + thumbPath);

            return ResultVO.success(result);

        } catch (Exception e) {
            throw new RuntimeException("头像上传失败: " + e.getMessage());
        }
    }

    /**
     * 创建元数据
     */
    private Set<MetaData> createMetaData(String userId) {
        Set<MetaData> metaDataSet = new HashSet<>();
        metaDataSet.add(new MetaData("userId", userId));
        metaDataSet.add(new MetaData("uploadTime", new Date().toString()));
        metaDataSet.add(new MetaData("type", "avatar"));
        return metaDataSet;
    }
}
