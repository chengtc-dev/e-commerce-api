package dev.chengtc.ecommerceapi.service;

import dev.chengtc.ecommerceapi.model.dto.member.MemberDTO;

public interface MemberService {
    MemberDTO register(MemberDTO memberDTO);
}
