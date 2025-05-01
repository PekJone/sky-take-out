package com.sky.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-01  11:16
 */

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class AliOssUtil {
    private String endPoint;
    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;

    public String upload(byte[] bytes,String objectName){
        OSS  ossClient = new OSSClientBuilder().build(endPoint,accessKeyId,accessKeySecret);

        try{
            ossClient.putObject(bucketName,objectName,new ByteArrayInputStream(bytes));
        }catch (OSSException e){

        }catch (ClientException e){

        }finally {
            if (ossClient!=null){
                ossClient.shutdown();
            }
        }
        StringBuilder stringBuilder = new StringBuilder("http://");
        stringBuilder.append(bucketName)
                .append(".")
                .append(endPoint).append("/")
                .append(objectName);
        log.info("文件上传到：{}",stringBuilder.toString());
        return stringBuilder.toString();
    }
}
