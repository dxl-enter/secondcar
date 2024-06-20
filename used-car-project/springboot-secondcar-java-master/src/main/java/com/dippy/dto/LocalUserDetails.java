package com.dippy.dto;

import com.dippy.entity.SysUser;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 尚未使用该类、
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalUserDetails extends SysUser implements UserDetails {
    private static final long serialVersionUID = 1594783117560L;


    @Size(min = 3, max = 18)
    @ApiModelProperty(value = "用户账号", required = true, hidden = false, example = "用户账号")
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String username = "";

    @Size(min = 3, max = 64)
    @ApiModelProperty(value = "用户密码", required = true, hidden = false, example = "用户密码")
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String password = "";

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回分配给用户的角色列表
        return new ArrayList<>();
    }

    @Override
    public boolean isAccountNonExpired() {
        // 指定账户是否未过期.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 指定账户是否未锁定.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 指定密码是否未过期.
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 指定用户是否已启用, 禁用的用户无法进行身份验证.
        return true;
    }
}
