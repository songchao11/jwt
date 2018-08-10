package com.song.filter;

import com.song.shiro.JWTToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * JWT拦截器
 */
public class JWTFilter extends BasicHttpAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTFilter.class);

    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)throws UnauthorizedException {
        //判断请求的请求头上是否带有Token
        if(isLoginAttempt(request, response)){
            //如果存在，则进入executeLogin方法执行登入，检查token是否正确
            try{
                executeLogin(request, response);
                return true;
            }catch(Exception e){
                //token错误
                responseError(response, e.getMessage());
            }
        }
        //如果请求头上不存在token，则直接返回true (可能是执行登录操作，或者游客状态访问)
        return true;
    }

    /**
     * 判断请求头上是否带有Token
     */
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response){
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("Token");
        return token != null;
    }

    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception{
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("Token");
        JWTToken jwtToken = new JWTToken(token);
        //提交给realm进行登录，如果错误他会抛出异常并捕获
        getSubject(request, response).login(jwtToken);
        //如果没有抛出异常，则代表成功登录返回true
        return true;
    }

    protected void responseError(ServletResponse response, String message){
        try{
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            //设置编码，否则中文字符在重定向时会变为空字符串
            message = URLEncoder.encode(message, "UTF-8");
            httpServletResponse.sendRedirect("/unauthorized/" + message);
        }catch(Exception e){
            LOGGER.error(e.getMessage());
        }
    }

}
