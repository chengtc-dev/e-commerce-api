package dev.chengtc.ecommerceapi.service.impl;

import dev.chengtc.ecommerceapi.exception.member.MemberExistsException;
import dev.chengtc.ecommerceapi.exception.member.RoleNotFoundException;
import dev.chengtc.ecommerceapi.mapper.MemberMapper;
import dev.chengtc.ecommerceapi.model.dto.member.MemberDTO;
import dev.chengtc.ecommerceapi.model.entity.Member;
import dev.chengtc.ecommerceapi.model.entity.Role;
import dev.chengtc.ecommerceapi.repository.MemberRepository;
import dev.chengtc.ecommerceapi.repository.RoleRepository;
import dev.chengtc.ecommerceapi.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberDTO register(MemberDTO memberDTO) {
        if (memberRepository.existsByEmail(memberDTO.getEmail()))
            throw new MemberExistsException(memberDTO.getEmail());

        Role normalRole = roleRepository.findByName("ROLE_NORMAL_MEMBER")
                .orElseThrow(() -> new RoleNotFoundException("ROLE_NORMAL_MEMBER"));

        Member member = MemberMapper.toEntity(memberDTO);
        String encodePassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodePassword);
        member.setRoles(Collections.singleton(normalRole));
        member = memberRepository.save(member);
        return MemberMapper.toDTO(member);
    }
}
