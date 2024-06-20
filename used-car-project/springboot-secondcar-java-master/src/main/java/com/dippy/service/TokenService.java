package com.dippy.service;


import com.dippy.dto.LoginUser;
import com.dippy.dto.Token;

/**
 * Token管理器<br>
 * 默认基于redis，实现类为 com.boot.security.server.service.impl.TokenServiceJWTImpl<br>
 *
 */
public interface TokenService {

	Token saveToken(LoginUser loginUser);

	void refresh(LoginUser loginUser);

	LoginUser getLoginUser(String token);

	boolean deleteToken(String token);

}
