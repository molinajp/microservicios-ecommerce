package com.undefined.sales.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.undefined.sales.dto.ItemDTO;
import com.undefined.sales.dto.OrderDTO;
import com.undefined.sales.service.OrderService;
import com.undefined.sales.utils.PaymentMethod;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    void givenOrderObject_whenGetByClientId_thenReturnOrderList() throws Exception {

        List<OrderDTO> orderList = new ArrayList<>();

        orderList.add(OrderDTO.builder().clientId(ArgumentMatchers.anyLong()).build());

        when(orderService.getSalesByClientId(1L)).thenReturn(orderList);

        assertThat(orderController.getSalesByClientId(ArgumentMatchers.anyLong())).isInstanceOf(ResponseEntity.class);

    }

    @Test
    void givenOrderObject_whenRegisterOrder_thenReturnOrders() throws Exception {

        List<ItemDTO> itemList = new ArrayList<>();

        OrderDTO order = OrderDTO.builder().totalAmount(new BigDecimal(1)).orderDate(new Date())
                .paymethod(PaymentMethod.EFVO).clientId(1L).items(itemList).build();

        when(orderService.registerOrder(order)).thenReturn(new OrderDTO());

        assertThat(orderController.registerOrder(order)).isInstanceOf(ResponseEntity.class);

    }

    @Test
    void givenOrderObject_whenGetTopItems_thenReturnListOfMap() throws Exception {

        List<Map<Long, Integer>> mapForReturn = new ArrayList<>();

        when(orderService.getTopItems(ArgumentMatchers.anyInt())).thenReturn(mapForReturn);

        assertThat(orderController.getItems(ArgumentMatchers.anyInt())).isInstanceOf(ResponseEntity.class);

    }
}
