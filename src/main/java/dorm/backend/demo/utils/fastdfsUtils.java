package dorm.backend.demo.utils;

import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.upload.FastImageFile;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class fastdfsUtils {
    protected FastFileStorageClient storageClient;

//    public void UploadAvatarImage() {
//        FastImageFile fastImageFile =
//    }

//    private FastImageFile crtFastImageAndCrtThumbImageForAvatar() throws Exception {
//        String fileExtName = FilenameUtils.getExtension(file.getName());
//
//        // 获取图像尺寸（假设是已知的，或通过 ImageIO 读取）
//        int width = 200;
//        int height = 200;
//        long fileSize = file.length();
//
//        Set<MetaData> metaDataSet = createAvatarMetaData("user_001", "john_doe", width, height, fileSize);
//
//        return new FastImageFile.Builder()
//                .withThumbImage(0.5)  // 生成50%缩略图
//                .withFile(in, fileSize, fileExtName)
//                .withMetaData(metaDataSet)
//                .build();
//    }

    /**
     * 创建头像元数据
     *
     * @param userId 用户ID
     * @param username 用户名
     * @param width 原图宽度
     * @param height 原图高度
     * @param fileSize 文件大小（字节）
     * @return 元数据集合
     */
    private Set<MetaData> createAvatarMetaData(String userId, String username, int width, int height, long fileSize) {
        Set<MetaData> metaDataSet = new HashSet<>();

        metaDataSet.add(new MetaData("userId", userId));
        metaDataSet.add(new MetaData("username", username));
        metaDataSet.add(new MetaData("uploadTime", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)));
        metaDataSet.add(new MetaData("sourceType", "web")); // 或 mobile 等
        metaDataSet.add(new MetaData("fileSize", String.valueOf(fileSize)));
        metaDataSet.add(new MetaData("imageWidth", String.valueOf(width)));
        metaDataSet.add(new MetaData("imageHeight", String.valueOf(height)));
        metaDataSet.add(new MetaData("mimeType", "image/jpeg")); // 或 image/png
        metaDataSet.add(new MetaData("thumbnailSizes", "[128,64,32]"));

        return metaDataSet;
    }
}
