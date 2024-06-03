package dev.chengtc.ecommerceapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.chengtc.ecommerceapi.model.dto.product.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static dev.chengtc.ecommerceapi.controller.ProductControllerTest.createTestProductDTO;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CsrfTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    @Test
    public void createProduct_noCsrfToken_fail() throws Exception {
        ProductDTO productDTO = createTestProductDTO();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO))
                .with(httpBasic("product_seller@e-commerce.org", "product_seller"));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isForbidden());
    }

    @Transactional
    @Test
    public void createProduct_withCsrfToken_success() throws Exception {
        ProductDTO productDTO = createTestProductDTO();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO))
                .with(httpBasic("product_seller@e-commerce.org", "product_seller"))
                .with(csrf());

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated());
    }

}
