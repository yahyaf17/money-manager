package io.strargazer.moneymanager.security;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import io.strargazer.moneymanager.model.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {

    private String id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private Boolean isGuest;
    private Integer dateResetPreference;

    private Set<GrantedAuthority> authorities;

    public UserDetailsImpl(String id, String username, String email, String password,
                           Boolean isGuest, Integer dateResetPreference, Set<GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.isGuest = isGuest;
        this.dateResetPreference = dateResetPreference;
    }

    public static UserDetailsImpl build(Users user) {
        Set<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority("ROLE_USER"));

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getIsGuest(),
                user.getDateResetPreference(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Boolean getGuest() {
        return isGuest;
    }

    public Integer getDateResetPreference() {
        return dateResetPreference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
