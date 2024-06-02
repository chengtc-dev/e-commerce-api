package dev.chengtc.ecommerceapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.chengtc.ecommerceapi.model.dto.member.MemberDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Transactional
    @Test
    public void register_success() throws Exception {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setName("Elon Mask");
        memberDTO.setEmail("info@tesla.com");
        memberDTO.setPassword("P@ssw0rd");
        memberDTO.setPhone("310-363-6000");

        mockMvc.perform(buildRegisterRequest(memberDTO))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("Elon Mask")))
                .andExpect(jsonPath("$.email", equalTo("info@tesla.com")))
                .andExpect(jsonPath("$.password", nullValue()))
                .andExpect(jsonPath("$.phone", equalTo("310-363-6000")));
    }

    @Transactional
    @Test
    public void register_invalidArgument() throws Exception {
        MemberDTO memberDTO = new MemberDTO();

        mockMvc.perform(buildRegisterRequest(memberDTO))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", equalTo(400)))
                .andExpect(jsonPath("$.message", allOf(
                        containsString("phone: must not be blank"),
                        containsString("password: must not be blank"),
                        containsString("email: must not be blank"),
                        containsString("name: must not be blank")
                )));
    }

    private RequestBuilder buildRegisterRequest(MemberDTO memberDTO) throws JsonProcessingException {
        return MockMvcRequestBuilders
                .post("/api/members/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberDTO));
    }
}