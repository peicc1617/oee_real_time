package xjtu.oee_real_time.filter;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xjtu.oee_real_time.entity.User;
import xjtu.oee_real_time.util.AliPayUtil;
import xjtu.oee_real_time.util.HttpUtil;
import xjtu.oee_real_time.util.SSOURLUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @基本功能:
 * @program:spider
 * @author:peicc
 * @create:2020-02-06 14:08:46
 **/
@WebFilter(filterName = "SSOClientFilter",urlPatterns = "/oeeRealTime.html")
public class SSOClientFilter implements Filter {
    private Logger LOGGER= LoggerFactory.getLogger(getClass());
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("过滤器初始化");
    }

    @Override
    public void destroy() {
        LOGGER.info("过滤器销毁");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.info("过滤器正在执行");
        HttpServletRequest req=(HttpServletRequest) servletRequest;
        HttpServletResponse resp=(HttpServletResponse) servletResponse;
        HttpSession session=req.getSession();
        //1、判断是否有局部会话
        Boolean isLogin=(Boolean) session.getAttribute("isLogin");
        if(isLogin!=null && isLogin){
            //有局部会话,直接放行
            LOGGER.info("当前用户已登录，直接放行");
            filterChain.doFilter(req,resp);
            return;
        }
        //取出cookie
        Cookie[] cookies=req.getCookies();
        if (cookies!=null) {
            //判断cookie中是否存在token信息
            for (Cookie cookie:cookies) {
                if (cookie.getName().equals("token")) {
                    //取出token信息
                    String token= cookie.getValue();
                    //获取sessionId
                    String sessonId=session.getId();
                    //获取注销地址
                    String logOutURL= SSOURLUtil.CLIENT_URL+"/logOut";
                    //获取服务端验证地址
                    String sSOVerifyURL= SSOURLUtil.SSO_URL+"/verify";
                    //封装请求参数
                    Map<String,String> params=new HashMap<>();
                    params.put("token",token);
                    params.put("jesessionId",sessonId);
                    params.put("clientLogOutURL",logOutURL);
                    //到SSO服务端进行token验证
                    String res= HttpUtil.sendHttpRequest(sSOVerifyURL,params);
                    JSONObject jsonObject=JSONObject.parseObject(res);
                    if ((Boolean) jsonObject.get("state")){// SSO服务端token验证通过
                        LOGGER.info("token信息验证成功");
                        session.setAttribute("userInfo",jsonObject.get("content"));
                        session.setAttribute("isLogin",true);
                        filterChain.doFilter(req,resp);
                        return;
                    }

                }
            }
        }
        //判断是否是支付宝登录
        if (req.getParameter("auth_code")!=null && !req.getParameter("auth_code").equals("")) {
            LOGGER.info("用户使用支付宝登录");
            // 获取回调页面参数auth_code
            String auth_code=req.getParameter("auth_code");
            LOGGER.info("用户已授权，获取auth_code信息为："+auth_code);
            String access_token= AliPayUtil.getAcessToken(auth_code);
            if (access_token!=null&&(!access_token.equals(""))) {
                String userInfo= AliPayUtil.getUserInfo(access_token) ;
                LOGGER.info("支付宝用户信息为："+userInfo);
                JSONObject jsonObject=(JSONObject) JSONObject.parse(userInfo);
                User user=new User();
                String userName=(String)((JSONObject)jsonObject.get("alipay_user_info_share_response")).get("nick_name");
                LOGGER.info("支付宝用户昵称为："+userName);
                LOGGER.info("支付宝用户图像网址为："+((JSONObject)jsonObject.get("alipay_user_info_share_response")).get("avatar"));
                user.setUserName(userName);
                user.setNickName("支付宝");
                user.setPermission("2");
                user.setDomain(((JSONObject)jsonObject.get("alipay_user_info_share_response")).get("province")+"-"+((JSONObject)jsonObject.get("alipay_user_info_share_response")).get("city"));
                user.setPhoneNumber("保密");
                user.setEmail("保密");
                user.setJobNumber((String)((JSONObject)jsonObject.get("alipay_user_info_share_response")).get("user_id"));
                user.setDomainName("阿里巴巴");
                user.setUserPhoto((String)((JSONObject)jsonObject.get("alipay_user_info_share_response")).get("avatar"));//支付宝用户头像
                String userJson=JSONObject.toJSONString(user);
                JSONObject userJSONObject= JSONObject.parseObject(userJson);
                session.setAttribute("userInfo",userJSONObject);
                session.setAttribute("isLogin",true);
                filterChain.doFilter(req,resp);
                return;
            }
        }

        //没有局部会话，重定向到SSO
        LOGGER.info("当前用户未登录，跳转到登录页面");
        resp.sendRedirect(SSOURLUtil.SSO_URL+"/checkLogin?redirectUrl="+ SSOURLUtil.getSendRedirectURL(req));

    }
}
