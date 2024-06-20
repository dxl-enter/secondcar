package com.dippy.util;

import com.dippy.dto.LoginUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 获取当前用户
 */
public class UserUtil {

	/**
	 *
	 * @return 当前登录用户
	 */
	public static LoginUser getLoginUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			if (authentication instanceof AnonymousAuthenticationToken) {
				return null;
			}

			if (authentication instanceof UsernamePasswordAuthenticationToken) {
				return (LoginUser) authentication.getPrincipal();
			}
		}

		return null;
	}

}
