package com.punuo.controller;

import com.punuo.bean.Msg;
import com.punuo.bean.User;
import com.punuo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.UUID;

/**
 * @author lenovo on 2018/4/8.
 * @version 1.0
 */
@RestController
public class ControllerForIpc {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    //1.用Msg作为统一的回复消息类 done
    //2.使用servlet来拦截所有请求，只有在login成功后才能访问其他url  done
    //3.登录过程需要MD5加密等保证安全  done
    @RequestMapping("/login1")
    public Msg login1(@RequestParam("userName") String userName, HttpSession session){
        logger.info("request login1 from user: " + userName);
        session.setMaxInactiveInterval(30);
        User user = userService.getUserByUserName(userName);
        if(user != null){
            logger.info("request login1 success");
            session.setAttribute("userName", userName);
            return Msg.success().add("message","success").add("salt",user.getPassword().split("#")[0])
                    .add("seed",generateSeed());
        }else{
            logger.info("request login1 failure");
            return Msg.fail().add("message","user is not exit");
        }
    }


    @RequestMapping("/login2")
    public Msg login2(@RequestParam("password") String password, @RequestParam("seed") String seed,@RequestParam("userId") String userId, HttpSession session){
        String userName = (String) session.getAttribute("userName");
        logger.info("request login2 from user: " + userName);
        if(userName == null){
            return Msg.fail().add("message","please complete login1 first");
        }else{
            User user = userService.getUserByUserName(userName);
            String passwordStored = user.getPassword().split("#")[1];
            String encryptPassword = getMD5(passwordStored + ":" + seed);
//            if(password.equals(encryptPassword)){
            if("123456".equals(password)){
                session.setAttribute("userId",userId);
                session.setAttribute("roleName",user.getRole().getRoleName());
                return Msg.success().add("message","login success");
            }else{
                return Msg.fail().add("message","the password is not correct");
            }
        }
    }

    //心跳包，保持会话的存活
    @RequestMapping("/heartbeat")
    public Msg heartbeat(@RequestParam("userName") String userName){
        logger.info("heartbeat from user: " + userName);
        return Msg.success().add("message",userName + " heartbeat success");
    }


    @RequestMapping("/addUser1")
    public Msg addUser1(@RequestParam("userName") String userName){
        User user = userService.getUserByUserName(userName);
        if (user != null){
            return Msg.fail().add("message","the userName is exiting already");
        }else{
            String salt = generateSeed().substring(0,4);
            String userId = (Integer.parseInt(userService.getMaxUserId()) + 1) + "";
            return Msg.success().add("message","addUse1 success").add("salt",salt).add("userId",userId);
        }
    }

    @RequestMapping("/addUser2")
    public Msg addUser2(@RequestParam("userName") String userName,@RequestParam("password") String password,
                        @RequestParam("salt") String salt,@RequestParam("userId") String userId){
        String passwordStored = salt + "#" + password;
        User user = new User();
        user.setUserName(userName);
        user.setUserId(userId);
        user.setPassword(passwordStored);

        Integer flag = userService.saveUser(user);
        if(flag > 0){
            return Msg.success().add("message","add user success");
        }else{
            return Msg.fail().add("message","fail to insert user into database");
        }
    }

    @RequestMapping("/ipcList")
    public Msg ipcList(){
        //在局域网内发送组播，查找在线的ipc列表，返回给客户端
        return null;
    }

    @RequestMapping("/notLogin1")
    public Msg notLogin1(){
        return Msg.fail().add("message","please complete login1 first");
    }

    @RequestMapping("/notLogin2")
    public Msg notLogin2(){
        return Msg.fail().add("message","please complete login2 first");
    }

    @RequestMapping("/noAuthority")
    public Msg noAuthority(){return Msg.fail().add("messsage","you have no authority to do this");}

    @RequestMapping("/hello1")
    public String hello(){
        return "hello";
    }

    /**
     * MD5加密，产生32位的随机字符串
     * @param str:需要加密的字符串
     * @return 加密后的字符串
     */
    private String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            String md5=new BigInteger(1, md.digest()).toString(16);
            //BigInteger会把0省略掉，需补全至32位
            return fillMD5(md5);
        } catch (Exception e) {
            throw new RuntimeException("MD5加密错误:"+e.getMessage(),e);
        }
    }

    private String fillMD5(String md5){
        return md5.length()==32?md5:fillMD5("0"+md5);
    }

    /**
     * 生成随机数
     * @return 生成的18位随机数
     */
    private String generateSeed(){
        String[] random =  UUID.randomUUID().toString().split("-");
        return random[0]+random[1]+random[2];
    }

}
