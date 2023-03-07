package com.coco;

import com.coco.utils.MD5Utils;
import com.coco.utils.MailUtils;
import com.coco.utils.SmsUtils;
import com.coco.vo.MailVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class JavaSportApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailUtils mailUtils;

    @Autowired
    private SmsUtils smsUtils;

    @Test
    void contextLoads() {
        System.out.println(passwordEncoder.encode(MD5Utils.md5("123456")));
        // 经过md5加密后再次由PasswordEncoder进行加密$2a$10$n/VyL3ghEVjCUsXFTM.Hv.gv0XI9UfPLujyOIWkrc.23ckYf2BTny
        String encode = passwordEncoder.encode(MD5Utils.md5("123456"));
        boolean matches = passwordEncoder.matches(MD5Utils.md5("123456"), encode);
        System.out.println(matches);
    }

    @Test
    void testMail() {
        MailVo mail = new MailVo();
        mail.setReceivers(new String[]{"1737194722@qq.com"});
        mail.setHtml(true);
        mail.setSubject("个人运动管理平台！");
        mail.setContent("<a href='https://www.baidu.com/' style='color: red'>邮件发送测试！</a>");
        System.out.println(mailUtils.sendMail(mail));
    }

    @Test
    void testSms() {
        //smsUtils.sendSms("15523285128");
    }

}
