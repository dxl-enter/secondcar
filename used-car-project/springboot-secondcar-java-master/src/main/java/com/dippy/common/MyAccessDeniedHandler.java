package com.dippy.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 异常处理
 */
// @Component
@Slf4j
public class MyAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
	                   AccessDeniedException accessDeniedException) throws IOException, ServletException {
		log.error("SpringSecurity异常处理执行了--------");
		//响应状态
		// response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		//返回json格式
		response.setHeader("Content-Type", "application/json;charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.write("{\"status\":\"error\",\"msg\":\"权限不足，请联系管理员\"}");
		writer.flush();
		writer.close();
	}
}
