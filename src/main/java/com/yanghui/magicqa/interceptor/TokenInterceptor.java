package com.yanghui.magicqa.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanghui.magicqa.utils.TokenFactory;
import net.minidev.json.JSONObject;
import org.apache.maven.surefire.shade.org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import java.io.IOException;
import java.io.OutputStream;

/**
 *       拦截器：在每次调用接口时进行拦截，校验用户传递的token是否有效并且正确
 **/
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private TokenFactory tokenFactory;

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws IOException {

        String tokenType = request.getHeader("tokenType"); // "accessToken" or "refreshToken"
        String tokenValue = request.getHeader("tokenValue");
        // 检查用户传递的 token 信息是否合法
        if (StringUtils.isBlank(tokenType) || StringUtils.isBlank(tokenValue)) {
            System.out.println("Interceptor-preHandle:没有传入对应的身份信息，返回登录页面");
            this.responseCheckTokenCode(null,3,response);
            return false;
        }
        // 检查用户传递的 token 是否 存在且正确
        try {
            if (redisTemplate.opsForValue().get(tokenValue) != null) {
                System.out.println("token校验成功");
                if(tokenType == "refreshToken"){
                    this.responseCheckTokenCode(tokenFactory.produceAccessToken(tokenValue),1,response);
                }
                return true;
            } else {
                switch(tokenType){
                    case "accessToken":
                        System.out.println("Interceptor-preHandle:accessToken无效，请用refreshToken验证");
                        this.responseCheckTokenCode(null,2,response);
                        break;
                    case "refreshToken":
                        System.out.println("Interceptor-preHandle:refreshToken无效，返回登录页面");
                        this.responseCheckTokenCode(null,3,response);
                        break;
                }
                return false;
            }
        } catch (Exception e) {
            System.out.println("token校验失败,信息匹配错误，返回登录页面");
            this.responseCheckTokenCode(null,3,response);
            return false;
        }

    }

    /**
     * 编写token校验失败后的responseBody数据
     * @param errorTokenCode :{1:返回新的accessToken,2:请用refreshToken验证,3:返回登录页面}
     */
    private void responseCheckTokenCode(String accessToken,int errorTokenCode, HttpServletResponse resp) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("checkTokenCode", errorTokenCode);
        jsonObject.put("accessToken", accessToken);
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/json");
        OutputStream out = resp.getOutputStream();
        out.write(this.objectToJson(jsonObject).getBytes("utf-8"));
        out.flush();
    }

    /**
     * 将对象转换成json字符串。
     *
     * @param data
     * @return
     */
    private static String objectToJson(Object data) {
        try {
            String string = new ObjectMapper().writeValueAsString(data);
            return string;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 请求处理之后、视图被渲染之前 进行调用（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView mv)
            throws Exception { }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行 （主要是用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception ex)
            throws Exception { }

}
