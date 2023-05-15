package com.undefined.sales.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.undefined.sales.dto.OrderDTO;
import com.undefined.sales.model.ItemEntity;
import com.undefined.sales.model.OrderEntity;
import com.undefined.sales.repository.ItemRepository;
import com.undefined.sales.repository.OrderRepository;
import com.undefined.sales.utils.PaymentMethod;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl service;

    @Mock
    private OrderRepository orderepository;

    @Mock
    private ItemRepository itemrepository;

    List<ItemEntity> itemList;

    List<OrderEntity> orderList;

    @BeforeEach
    void setUp() {

        itemList = new ArrayList<>();

        orderList = Arrays.asList(
                OrderEntity.builder().totalAmount(new BigDecimal(123)).orderDate(new Date())
                        .paymethod(PaymentMethod.EFVO).clientId(1L).item(itemList).build(),
                OrderEntity.builder().totalAmount(new BigDecimal(123)).orderDate(new Date())
                        .paymethod(PaymentMethod.EFVO).clientId(2L).item(itemList).build());
    }

    @Test
    void givenClientId_theReturnListOfSales_orders() {

        given(orderepository.findSalesByClientId(1L)).willReturn(orderList);

        List<OrderDTO> list = service.getSalesByClientId(1L);

        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    void givenClientId_theReturnNotNullResponse() {

        given(orderepository.findSalesByClientId(ArgumentMatchers.anyLong())).willReturn(orderList);

        assertThat(service.getSalesByClientId(ArgumentMatchers.anyLong())).isInstanceOf(ArrayList.class);
    }

    @Test
    void givenOrderEntity_thenReturnObject() {

        OrderEntity orderEntity = OrderEntity.builder().id(null).clientId(1L).orderDate(new Date()).item(itemList)
                .totalAmount(new BigDecimal(1)).paymethod(PaymentMethod.TARJETA).build();

        when(orderepository.save(Mockito.any(OrderEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        assertThat(service.registerOrder(OrderDTO.orderToDto(orderEntity))).isInstanceOf(OrderDTO.class);
    }

    @Test
    void givenMapList_thenReturnArrayList() {
        List<Map<Long, Integer>> mapForReturn = new ArrayList<>();

        given(itemrepository.getTopItems(ArgumentMatchers.anyInt())).willReturn(mapForReturn);

        assertThat(service.getTopItems(ArgumentMatchers.anyInt())).isInstanceOf(ArrayList.class);
    }

}
