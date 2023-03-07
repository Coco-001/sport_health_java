package com.coco.controller;

import com.coco.entity.SysUser;
import com.coco.entity.WxRun;
import com.coco.service.SportService;
import com.coco.service.SysUserService;
import com.coco.utils.*;
import com.coco.vo.Encrypted;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mini")
@Api(tags = "小程序相关接口")
public class MiniController {

    @Value("${mini.appid}")
    private String appid;

    @Value("${mini.secret}")
    private String secret;

    @Autowired
    private SysUserService userService;

    @Autowired
    private SportService sportService;

    @ApiOperation(value = "微信小程序登录")
    @GetMapping("/login")
    public Result login(String code) {
        if (StringUtils.isEmpty(code)) {
            return Result.fail("登录失败, 请联系管理员！");
        }
        // 构建get请求
        String url = "https://api.weixin.qq.com/sns/jscode2session?" + "appid=" +
                appid +
                "&secret=" +
                secret +
                "&js_code=" +
                code +
                "&grant_type=authorization_code";
        String result = HttpUtils.getResponse(url);
        // 发送请求
        JSONObject jsonObject = JSON.parseObject(result);
        String openid = jsonObject.getString("openid");
        String sessionKey = jsonObject.getString("session_key");
        log.info("微信小程序唯一标识：{}", openid);
        return userService.miniLogin(openid, sessionKey);
    }

    @ApiOperation(value = "更新用户信息")
    @PostMapping("/update/info")
    public Result updateInfo(@RequestBody SysUser user) {
        return userService.updateByopenId(user);
    }

    @PostMapping("/wxrun")
    public Result getRunStep(@RequestBody Encrypted encrypted) {
        String data = DecryptDataUtils.decryptData(encrypted.getEncryptedData(), encrypted.getSessionKey(), encrypted.getIv());
        log.info("加密数据--> {}", data);
        JSONObject result = JSONObject.parseObject(data);
        JSONArray stepInfoList = result.getJSONArray("stepInfoList");
        List<WxRun> runs = new ArrayList<>();
        for (Object o : stepInfoList) {
            JSONObject obj = (JSONObject)o;
            Long timestamp = obj.getLong("timestamp");
            WxRun step = new WxRun(encrypted.getOpenid(), DateUtils.timeStampConvertString(timestamp), obj.getInteger("step"));
            runs.add(step);
        }
        return sportService.insertStep(runs);
    }

    @GetMapping("/step/report")
    public Result stepReport() {
        return sportService.stepReport();
    }

}
