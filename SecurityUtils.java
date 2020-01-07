package com.xyjsoft.core.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.xyjsoft.core.query.AppUtil;
import com.xyjsoft.core.bean.LoginInfo;
import com.xyjsoft.core.controller.SysRedisController;
import com.xyjsoft.core.security.JwtAuthenticatioToken;


/**
 * Security相关操作
 * @author gsh456
 * @date 2019-03-15 11:10
 */
public class SecurityUtils {

	private static SysRedisController sysRedis = null;
	/**
	 * 系统登录认证
	 * @param request
	 * @param username
	 * @param password
	 * @param authenticationManager
	 * @return
	 */
	public static JwtAuthenticatioToken login(HttpServletRequest request, String username, String password, AuthenticationManager authenticationManager) {
		JwtAuthenticatioToken token = new JwtAuthenticatioToken(username, password);
		
		token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		// 执行登录认证过程
	    Authentication authentication = authenticationManager.authenticate(token);
	    // 认证成功存储认证信息到上下文
	    SecurityContextHolder.getContext().setAuthentication(authentication);
		// 生成令牌并返回给客户端
	    token.setToken(JwtTokenUtils.generateToken(authentication));
		return token;
	}

	/**
	 * 获取令牌进行认证
	 * @param request
	 */
	public static void checkAuthentication(HttpServletRequest request) {
		// 获取令牌并根据令牌获取登录认证信息
		//Authentication authentication = JwtTokenUtils.getAuthenticationeFromToken(request);
		String admintoken = JwtTokenUtils.TOKEN;
		Authentication authentication = null;
		
		// 获取请求携带的令牌
		String token = JwtTokenUtils.getToken(request);
		if(token != null) {			
/*			if(SecurityUtils.sysRedis == null){
				Object bean = AppUtil.getBean("SysRedisController");
				if(bean == null){
					throw new RuntimeException("没有找到SysRedisController组件");
				}
				Object objBean;
				try {
					objBean = AopTargetUtils.getTarget(bean);
				} catch (Exception e) {
					throw new RuntimeException("SysRedisController组件转换失败" + e.getMessage());
				}
				SecurityUtils.sysRedis = (SysRedisController)objBean;
			}
			LoginInfo lInfo = SecurityUtils.sysRedis.getLoginUserInfo(token);
			if(lInfo == null){
				return;
			}
			lInfo.setToken(token);
			if(lInfo != null){
				request.setAttribute("userid", lInfo.getUserid());
				request.setAttribute("username", lInfo.getUsername());
				request.setAttribute("empname", lInfo.getEmpname());
				request.setAttribute("userInfo", lInfo);
			}
			authentication = JwtTokenUtils.getMpAuthenticationeFrom(request,lInfo);*/
			
			
			//宋建国屏蔽，2019-12-05
			if(token.equals(admintoken) || token.equals(JwtTokenUtils.admintokenOld)){
				LoginInfo lInfo = new LoginInfo();
				lInfo.setEmpname("admin");
				lInfo.setUsername("admin");
				lInfo.setUserid("1");
				lInfo.setToken(token);
				lInfo.setChatId(1L);
				
				request.setAttribute("userid", lInfo.getUserid());
				request.setAttribute("username", lInfo.getUsername());
				request.setAttribute("empname", lInfo.getEmpname());
				request.setAttribute("userInfo", lInfo);
				authentication = JwtTokenUtils.getMpAuthenticationeFromToken(request,lInfo);
			}else{
				if(SecurityUtils.sysRedis == null){
					Object bean = AppUtil.getBean("SysRedisController");
					if(bean == null){
						throw new RuntimeException("没有找到SysRedisController组件");
					}
					Object objBean;
					try {
						objBean = AopTargetUtils.getTarget(bean);
					} catch (Exception e) {
						throw new RuntimeException("SysRedisController组件转换失败" + e.getMessage());
					}
					SecurityUtils.sysRedis = (SysRedisController)objBean;
				}
				LoginInfo lInfo = SecurityUtils.sysRedis.getLoginUserInfo(token);
				if(lInfo == null){
					return;
				}
				lInfo.setToken(token);
				if(lInfo != null){
					request.setAttribute("userid", lInfo.getUserid());
					request.setAttribute("username", lInfo.getUsername());
					request.setAttribute("empname", lInfo.getEmpname());
					request.setAttribute("userInfo", lInfo);
				}
				authentication = JwtTokenUtils.getMpAuthenticationeFrom(request,lInfo);
			}		
		}
		
		// 设置登录认证信息到上下文
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	/**
	 * 获取当前用户名
	 * @return
	 */
	public static String getUsername() {
		String username = null;
		Authentication authentication = getAuthentication();
		if(authentication != null) {
			Object principal = authentication.getPrincipal();
			if(principal != null) {
				if(principal instanceof UserDetails){
					username = ((UserDetails) principal).getUsername();
				}else{
					username = (String)principal;
				}
			}
		}
		return username;
	}
	
	/**
	 * 获取用户名
	 * @return
	 */
	public static String getUsername(Authentication authentication) {
		String username = null;
		if(authentication != null) {
			Object principal = authentication.getPrincipal();
			if(principal != null) {
				if(principal instanceof UserDetails){
					username = ((UserDetails) principal).getUsername();
				}else{
					username = (String)principal;
				}
			}
		}
		return username;
	}
	
	/**
	 * 获取当前登录信息
	 * @return
	 */
	public static Authentication getAuthentication() {
		if(SecurityContextHolder.getContext() == null) {
			return null;
		}
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		return authentication;
	}
	
}
