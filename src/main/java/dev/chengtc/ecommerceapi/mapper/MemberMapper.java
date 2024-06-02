package dev.chengtc.ecommerceapi.mapper;

import dev.chengtc.ecommerceapi.model.dto.member.MemberDTO;
import dev.chengtc.ecommerceapi.model.entity.Member;

public class MemberMapper {

    public static Member toEntity(MemberDTO memberDTO) {
        Member member = new Member();
        member.setName(memberDTO.getName());
        member.setEmail(memberDTO.getEmail());
        member.setPassword(memberDTO.getPassword());
        member.setPhone(memberDTO.getPhone());
        return member;
    }

    public static MemberDTO toDTO(Member member) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setName(member.getName());
        memberDTO.setEmail(member.getEmail());
        memberDTO.setPhone(member.getPhone());
        return memberDTO;
    }

}
