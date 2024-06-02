package dev.chengtc.ecommerceapi.service.impl;

import dev.chengtc.ecommerceapi.model.entity.Member;
import dev.chengtc.ecommerceapi.model.entity.Role;
import dev.chengtc.ecommerceapi.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("Member not found for: " + username));

        String memberEmail = member.getEmail();
        String memberPassword = member.getPassword();
        Set<Role> roles = member.getRoles();
        Set<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        // 轉換成 Spring Security 指定的 User 格式
        return new User(memberEmail, memberPassword, authorities);
    }
}
