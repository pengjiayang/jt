package com.jt.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadLocal;

import redis.clients.jedis.JedisCluster;


@Component  //将拦截器交给spring管理
public class UserInteceptor implements HandlerInterceptor {
	@Autowired(required=false)
	private JedisCluster jedisCluster;
	/**
	 * 返回值结果:
	 * true:拦截放行
	 * false:请求拦截,重定向到登陆页面
	 * 
	 * 业务逻辑:
	 * 1.获取cookie数据
	 * 2.从coolie中获取token(ticket)
	 * 3判断redis缓存服务器中是否有数据
	 * 4
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token =null;
		//1.获取cookie信息
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if("JT_TICKET".equals(cookie.getName())) {
				token = cookie.getValue();
				break;
			}
		}
		//判断token是否有效
		if(!StringUtils.isEmpty(token)) {
			String str = jedisCluster.get(token);
			System.out.println(str);
			User user = ObjectMapperUtil.toObject(str, User.class);
		//	request.setAttribute("user", user);
			//request.getSession().setAttribute("JT_TICKET", user);
			UserThreadLocal.set(user);
			return true;
		}
		//3.重定向到用户登陆界面
		response.sendRedirect("http://www.jt.com/user/login.html");
		return false;//表示拦截
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//request.getSession().getAttribute("JT_TICKET");
		UserThreadLocal.remove();
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
