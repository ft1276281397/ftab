package com.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSONObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.annotation.IgnoreAuth;
import com.entity.EIException;
import com.entity.TokenEntity;
import com.service.TokenService;
import com.utils.R;

/**
 * 权限(Token)验证
 */
@Component
// 将类声明为Spring组件
public class AuthorizationInterceptor implements HandlerInterceptor {
    // 实现HandlerInterceptor接口

    public static final String LOGIN_TOKEN_KEY = "Token";
    // 定义登录Token的键

    @Autowired
    private TokenService tokenService;
    // 注入TokenService

	@Override

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在请求处理之前进行拦截

        String servletPath = request.getServletPath();
        // 获取请求路径
        if("/dictionary/page".equals(request.getServletPath())  || "/file/upload".equals(request.getServletPath()) || "/yonghu/register".equals(request.getServletPath()) ){
            // 请求路径是字典表或者文件上传 直接放行
            return true;
            // 直接返回true，不进行权限验证
        }
        //支持跨域请求
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        // 设置允许的HTTP方法
        response.setHeader("Access-Control-Max-Age", "3600");
        // 设置预检请求的有效期
        response.setHeader("Access-Control-Allow-Credentials", "true");
        // 允许发送凭据
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,request-source,Token, Origin,imgType, Content-Type, cache-control,postman-token,Cookie, Accept,authorization"); // 设置允许的请求头
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        // 设置允许的源

        IgnoreAuth annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(IgnoreAuth.class);
            // 获取方法上的IgnoreAuth注解
        } else {
            return true;
            // 如果不是HandlerMethod，直接放行
        }

        //从header中获取token
        String token = request.getHeader(LOGIN_TOKEN_KEY);
        // 从请求头中获取Token

        /**
         * 不需要验证权限的方法直接放过
         */
        if(annotation!=null) {
        	return true;
            // 如果方法上有IgnoreAuth注解，直接放行
        }

        TokenEntity tokenEntity = null;
        if(StringUtils.isNotBlank(token)) {
        	tokenEntity = tokenService.getTokenEntity(token);
            // 根据Token获取TokenEntity
        }

        if(tokenEntity != null) {
        	request.getSession().setAttribute("userId", tokenEntity.getUserid());
            // 将用户ID设置到session中
        	request.getSession().setAttribute("role", tokenEntity.getRole());
            // 将角色设置到session中
        	request.getSession().setAttribute("tableName", tokenEntity.getTablename());
            // 将表名设置到session中
        	request.getSession().setAttribute("username", tokenEntity.getUsername());
            // 将用户名设置到session中
        	return true;
            // 返回true，继续处理请求
        }

		PrintWriter writer = null;
		response.setCharacterEncoding("UTF-8");
        // 设置响应字符编码为UTF-8
		response.setContentType("application/json; charset=utf-8");
        // 设置响应内容类型为JSON
		try {
		    writer = response.getWriter(); // 获取PrintWriter对象
		    writer.print(JSONObject.toJSONString(R.error(401, "请先登录")));
            // 返回401错误信息
		} finally {
		    if(writer != null){
		        writer.close();
                // 关闭PrintWriter对象
		    }
		}
//				throw new EIException("请先登录", 401);
// 抛出401异常（注释掉）
		return false;
        // 返回false，阻止请求继续处理
    }
}
