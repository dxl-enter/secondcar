package com.dippy.util;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class ResponseUtil {

	public static void responseJson(HttpServletResponse response, int status, Object data) {
		try {
			log.info("ResponseUtil执行了---");

			// 设置跨域
			// response.setHeader("Access-Control-Allow-Origin", "*");
			// response.setHeader("Access-Control-Allow-Methods", "*");
			// // 设置后端返回JSON格式的数据
			// response.setContentType("application/json;charset=UTF-8");

			// 设置跨域
			response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
			// response.setHeader("Access-Control-Allow-Origin", "http://yuechi.com");
			// response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "*");
			// 设置后端返回JSON格式的数据
			response.setContentType("application/json;charset=UTF-8");
			// 状态码
			response.setStatus(status);

			response.getWriter().write(JSONUtil.toJsonStr(data));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
