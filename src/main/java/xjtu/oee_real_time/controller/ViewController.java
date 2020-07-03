package xjtu.oee_real_time.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @基本功能:
 * @program:oee_real_time
 * @author:peicc
 * @create:2020-06-17 16:48:29
 **/
@Controller
public class ViewController {
    private Logger LOGGER= LoggerFactory.getLogger(getClass());
    /***
     * @函数功能：查看oee实时计算页面
     * @param session:
     * @param model:
     * @return：java.lang.String
     */
    @RequestMapping("/oeeRealTime.html")
    public String oeeRealTime(HttpSession session, Model model){
        JSONObject user=(JSONObject)session.getAttribute("userInfo");
        if (user!=null) {
            model.addAttribute("userName",user.get("userName"));
            model.addAttribute("userPhoto",user.get("userPhoto"));
        }
        LOGGER.info("进入oee实时计算页面");
        return "oeeRealTime";
    }
    /***
     * @函数功能：查看profile.html页面
     * @param session: 会话
     * @param model: 模型
     * @return：java.lang.String
     */
    @RequestMapping("/profile.html")
    public String viewProfile(HttpSession session, Model model){
        JSONObject user=(JSONObject)session.getAttribute("userInfo");
        LOGGER.info("进入profile.html页面："+user.toString());
        //将相关参数暴露给视图
        model.addAttribute("userName",user.get("userName"));
        model.addAttribute("nickName",user.get("nickName"));
        model.addAttribute("email",user.get("email"));
        model.addAttribute("jobNumber",user.get("jobNumber"));
        model.addAttribute("phoneNumber",user.get("phoneNumber"));
        model.addAttribute("domain",user.get("domain"));
        model.addAttribute("domainName",user.get("domainName"));
        model.addAttribute("permission",user.get("permission"));
        model.addAttribute("userPhoto",user.get("userPhoto"));
        return "profile";
    }

}
