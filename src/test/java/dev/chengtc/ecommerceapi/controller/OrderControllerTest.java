package dev.chengtc.ecommerceapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.chengtc.ecommerceapi.model.dto.order.OrderItemRequest;
import dev.chengtc.ecommerceapi.model.dto.order.OrderPlaceRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Transactional
    @Test
    public void placeOrder_success() throws Exception {
        OrderPlaceRequest request = createOrderPlaceRequest();

        mockMvc.perform(buildPlaceOrderRequest(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderNumber", notNullValue()))
                .andExpect(jsonPath("$.buyerEmail", equalTo("normal_member@e-commerce.org")))
                .andExpect(jsonPath("$.totalAmount", equalTo(15985.00)))
                .andExpect(jsonPath("$.orderItems", hasSize(2)))
                .andExpect(jsonPath("$.orderDate", notNullValue()));
    }

    @Transactional
    @Test
    public void placeOrder_invalidArgument() throws Exception {
        OrderPlaceRequest request = createInvalidOrderPlaceRequest();

        mockMvc.perform(buildPlaceOrderRequest(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", equalTo(400)))
                .andExpect(jsonPath("$.message", allOf(
                        containsString("orderItems[1].quantity: must be greater than 0"),
                        containsString("orderItems[1].sku: must not be blank"),
                        containsString("buyerEmail: must not be blank"),
                        containsString("orderItems[0].quantity: must be greater than 0"),
                        containsString("orderItems[0].sku: must not be blank")
                )))
                .andExpect(jsonPath("$.path", equalTo("/api/orders")));
    }

    @Transactional
    @Test
    public void placeOrder_emptyOrderItems() throws Exception {
        OrderPlaceRequest request = new OrderPlaceRequest();
        request.setBuyerEmail("normal_member@e-commerce.org");

        mockMvc.perform(buildPlaceOrderRequest(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", equalTo(400)))
                .andExpect(jsonPath("$.message", allOf(
                        containsString("orderItems: must not be empty")
                )))
                .andExpect(jsonPath("$.path", equalTo("/api/orders")));
    }

    @Transactional
    @Test
    public void placeOrder_productStockShortage() throws Exception {
        OrderPlaceRequest request = createOrderPlaceRequest();
        request.getOrderItems().get(0).setQuantity(10000000);

        mockMvc.perform(buildPlaceOrderRequest(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", equalTo(400)))
                .andExpect(jsonPath("$.message", allOf(
                        containsStringIgnoringCase("Product stock shortage"),
                        containsStringIgnoringCase("remaining stock")
                )))
                .andExpect(jsonPath("$.path", equalTo("/api/orders")));
    }

    @Transactional
    @Test
    public void placeOrder_productNotFound() throws Exception {
        OrderPlaceRequest request = createOrderPlaceRequest();
        request.getOrderItems().get(0).setSku("?");

        mockMvc.perform(buildPlaceOrderRequest(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", equalTo(400)))
                .andExpect(jsonPath("$.message", allOf(
                        containsStringIgnoringCase("not found")
                )))
                .andExpect(jsonPath("$.path", equalTo("/api/orders")));
    }

    private static OrderPlaceRequest createOrderPlaceRequest() {
        OrderPlaceRequest request = new OrderPlaceRequest();
        List<OrderItemRequest> orderItemRequests = new ArrayList<>();

        OrderItemRequest orderItemRequest = new OrderItemRequest();
        orderItemRequest.setSku("MB-P-13-M1-SIL");
        orderItemRequest.setQuantity(10);
        orderItemRequests.add(orderItemRequest);

        OrderItemRequest orderItemRequest2 = new OrderItemRequest();
        orderItemRequest2.setSku("MB-P-13-M1-GRE");
        orderItemRequest2.setQuantity(5);
        orderItemRequests.add(orderItemRequest2);

        request.setOrderItems(orderItemRequests);
        request.setBuyerEmail("normal_member@e-commerce.org");
        return request;
    }

    private static OrderPlaceRequest createInvalidOrderPlaceRequest() {
        OrderPlaceRequest request = new OrderPlaceRequest();
        List<OrderItemRequest> orderItemRequests = new ArrayList<>();

        OrderItemRequest orderItemRequest = new OrderItemRequest();
        orderItemRequest.setQuantity(-10);
        orderItemRequests.add(orderItemRequest);

        OrderItemRequest orderItemRequest2 = new OrderItemRequest();
        orderItemRequest2.setQuantity(-5);
        orderItemRequests.add(orderItemRequest2);

        request.setOrderItems(orderItemRequests);
        return request;
    }

    private RequestBuilder buildPlaceOrderRequest(OrderPlaceRequest request) throws JsonProcessingException {
        return MockMvcRequestBuilders
                .post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(httpBasic("normal_member@e-commerce.org", "normal_member"))
                .with(csrf());
    }
}