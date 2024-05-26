package dev.chengtc.ecommerceapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.chengtc.ecommerceapi.model.dto.product.ProductDTO;
import dev.chengtc.ecommerceapi.model.dto.product.ProductQueryParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    private static ProductDTO getProductDTO() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("13-inch MacBook Air with M3 chip - Midnight");
        productDTO.setSku("MB-A-13-M3-MID");
        productDTO.setDescription("MacBook Air sails through work and play — and the M3 chip brings even greater capabilities to the world’s most popular laptop. With up to 18 hours of battery life, you can take the super portable MacBook Air anywhere and blaze through whatever you’re into.");
        productDTO.setPrice(BigDecimal.valueOf(1299));
        productDTO.setStock(10000000);
        return productDTO;
    }

    @Transactional
    @Test
    public void createProduct_success() throws Exception {
        ProductDTO productDTO = getProductDTO();

        RequestBuilder requestBuilder = callCreateProductAPI(productDTO);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.name", equalTo("13-inch MacBook Air with M3 chip - Midnight")))
                .andExpect(jsonPath("$.sku", equalTo("MB-A-13-M3-MID")))
                .andExpect(jsonPath("$.description", equalTo("MacBook Air sails through work and play — and the M3 chip brings even greater capabilities to the world’s most popular laptop. With up to 18 hours of battery life, you can take the super portable MacBook Air anywhere and blaze through whatever you’re into.")))
                .andExpect(jsonPath("$.price", equalTo(1299)))
                .andExpect(jsonPath("$.stock", equalTo(10000000)));
    }

    @Transactional
    @Test
    public void createProduct_productExists() throws Exception {
        ProductDTO productDTO = getProductDTO();

        RequestBuilder requestBuilder = callCreateProductAPI(productDTO);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.status", equalTo(400)))
                .andExpect(jsonPath("$.message", containsStringIgnoringCase("already exist")))
                .andExpect(jsonPath("$.path", equalTo("/api/products")));
    }

    @Transactional
    @Test
    public void createProduct_invalidArgument() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(BigDecimal.valueOf(-1299));
        productDTO.setStock(-10000000);

        RequestBuilder requestBuilder = callCreateProductAPI(productDTO);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.status", equalTo(400)))
                .andExpect(jsonPath("$.message", allOf(
                                containsString("sku: must not be blank"),
                                containsString("price: must be greater than 0"),
                                containsString("stock: must be greater than 0"),
                                containsString("name: must not be blank")
                        )))
                .andExpect(jsonPath("$.path", equalTo("/api/products")));
    }

    @Transactional
    @Test
    public void getProducts_success() throws Exception {
        RequestBuilder requestBuilder = callGetProductsAPI(null);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(10)));
    }

    @Transactional
    @Test
    public void getProducts_invalidArgument() throws Exception {
        ProductQueryParam param = new ProductQueryParam();
        param.setPageNumber(-1);
        param.setPageSize(-1);
        param.setOrderBy("?");
        param.setSortBy("?");

        RequestBuilder requestBuilder = callGetProductsAPI(param);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.status", equalTo(400)))
                .andExpect(jsonPath("$.message", allOf(
                        containsString("pageSize: must be greater than or equal to 0"),
                        containsString("pageNumber: must be greater than or equal to 0"),
                        containsString("orderBy: must match \"createdAt|price\""),
                        containsString("sortBy: must match \"desc|asc\"")
                )))
                .andExpect(jsonPath("$.path", equalTo("/api/products")));
    }

    private RequestBuilder callCreateProductAPI(ProductDTO productDTO) throws JsonProcessingException {
        return MockMvcRequestBuilders
                .post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO));
    }

    private RequestBuilder callGetProductsAPI(ProductQueryParam param) {
        if (param == null) {
            return MockMvcRequestBuilders
                    .get("/api/products");
        } else {
            return MockMvcRequestBuilders
                    .get("/api/products")
                    .param("keyword", param.getKeyword())
                    .param("orderBy", param.getOrderBy())
                    .param("sortBy", param.getSortBy())
                    .param("pageSize", String.valueOf(param.getPageSize()))
                    .param("pageNumber", String.valueOf(param.getPageNumber()));
        }
    }

}