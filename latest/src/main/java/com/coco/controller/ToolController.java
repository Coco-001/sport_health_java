package com.coco.controller;

import com.coco.service.SysUserService;
import com.coco.utils.*;
import com.coco.vo.MailVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Random;


@RestController
@RequestMapping("/tool")
public class ToolController {

    @Autowired
    private QiniuUtils qiniuUtils;

    @Autowired
    private MailUtils mailUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysUserService userService;

    @Autowired
    private SmsUtils smsUtils;

    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation(value = "七牛云文件上传")
    @PostMapping("/upload")
    public Result uplaod(@RequestBody MultipartFile file) throws IOException {
        String url = qiniuUtils.upload(file.getBytes(), file.getOriginalFilename());
        return Result.success("文件上传成功！", url);
    }

    @ApiOperation(value = "忘记密码？邮件找回")
    @PostMapping("/forget/password")
    public Result forget(@RequestBody MailVo mail) {
        mail.setSubject("个人健康管理平台");
        Random random = new Random();
        int password = 100000 + random.nextInt(1000000);
        userService.updatePwdByMail(mail.getReceivers()[0], passwordEncoder.encode(MD5Utils.md5(String.valueOf(password))));
        mail.setContent("您的新密码：" + password + " ，请妥善保管！");
        return Result.success(mailUtils.sendMail(mail));
    }

    @PostMapping("/sms")
    public Result sendSms(String phoneNumber) {
        Random random = new Random();
        int code = 100000 + random.nextInt(899999);
        smsUtils.sendSms(phoneNumber, code);
        redisUtils.setValueTime(phoneNumber + "sms", code, 5);
        return Result.success("验证码发送成功！");
    }

}
