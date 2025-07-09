package com.example.TenantEase.service.impl;

import com.example.TenantEase.Repository.UserRepository;
import com.example.TenantEase.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MyUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public MyUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found With Email " + username));

        // 1) Permissions from roles
//        Set<GrantedAuthority> authorities = user.getRoles().stream()
//                .flatMap(role -> role.getPermissions().stream())
//                .map(perm -> new SimpleGrantedAuthority(perm.getName()))
//                .collect(Collectors.toSet());

        // 2) Add role names as authorities: ROLE_<ROLE_NAME>
        Set<GrantedAuthority> roleAuthorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getDescription()))
                .collect(Collectors.toSet());

        // Merge permissions + roles
//        authorities.addAll(roleAuthorities);

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                roleAuthorities
        );
    }

    /**
     * Optionally, if you prefer a switch-based mapping instead of using the description:
     */
    private GrantedAuthority getAuthorityByRoleId(int roleId) {
        return switch (roleId) {
            case 1 -> new SimpleGrantedAuthority("ROLE_SUPER_ADMIN");
            case 2 -> new SimpleGrantedAuthority("ROLE_ADMIN");
            case 3 -> new SimpleGrantedAuthority("ROLE_TENANT");
            default -> new SimpleGrantedAuthority("ROLE_USER");
        };
    }
}
