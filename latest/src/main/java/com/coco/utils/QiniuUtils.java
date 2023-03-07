package com.coco.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class QiniuUtils {

    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket;

    @Autowired
    private UploadManager uploadManager;

    /**
     * 鉴权
     * @return 返回鉴权字符串
     */
    public String uploadToken() {
        Auth auth = Auth.create(accessKey, secretKey);
        return auth.uploadToken(bucket);
    }

    /**
     * 根据文件路径去上传文件
     * @param filepath
     * @param fileName
     * @return
     */
    public String upload(String filepath, String fileName) {
        String name = this.genName(fileName);
        try {
            Response response = uploadManager.put(filepath, name, this.uploadToken());
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info("文件上传成功==> key:{} <==> hash: {}", putRet.key, putRet.hash);
            return name;
        } catch (QiniuException e) {
            Response r = e.response;
            try {
                log.error("文件上传失败==>{}", r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
            return null;
        }
    }

    /**
     * 根据字节上传文件
     * @param bytes
     * @param fileName
     * @return
     */
    public String upload(byte[] bytes, String fileName) {
        try {
            Response response = uploadManager.put(bytes, this.genName(fileName), this.uploadToken());
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info("文件上传成功==> key:{} <==> hash: {}", putRet.key, putRet.hash);
            return putRet.key;
        } catch (QiniuException e) {
            Response r = e.response;
            try {
                log.error("文件上传失败==>{}", r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
            return null;
        }
    }

    /**
     * 根据文件输入刘上传文件
     * @param stream
     * @param fileName
     * @return
     */
    public String upload(InputStream stream, String fileName) {
        try {
            Response response = uploadManager.put(stream, this.genName(fileName), this.uploadToken(), null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info("文件上传成功==> key:{} <==> hash: {}", putRet.key, putRet.hash);
            return this.genName(fileName);
        } catch (QiniuException e) {
            Response r = e.response;
            try {
                log.error("文件上传失败==>{}", r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
            return null;
        }
    }

    /**
     * 根据文件名删除文件
     * @param fileName
     */
    public void delete(String fileName) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2());
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, fileName);
            log.info("删除文件成功！");
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            log.error("删除失败==>code {}", ex.code());
            log.error(ex.response.toString());
        }
    }

    /**
     * 根据文件名生成时间文件名
     * @param fileName
     * @return
     */
    public String genName(String fileName) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(new Date()) + fileName.substring(fileName.lastIndexOf("."));
    }

}
