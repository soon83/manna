package com.sss.config;

import com.sss.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto implements UserDetails {

    @NotBlank(message = "username 는 필수값입니다.")
    private String username;

    @NotBlank(message = "password 는 필수값입니다.")
    private String password;

    private Set<LoginAuthority> authorities;

    private boolean enabled;

    public LoginDto(Member member) {
        this.username = member.getLoginId();
        this.password = member.getLoginPassword();
        this.authorities = new HashSet<>();
        authorities.add(new LoginAuthority(1L, "ROLE_USER"));
        this.enabled = true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }
}
