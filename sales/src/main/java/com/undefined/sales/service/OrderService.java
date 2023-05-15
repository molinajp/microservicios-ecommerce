package com.undefined.sales.service;

import java.util.List;
import java.util.Map;

import com.undefined.sales.dto.OrderDTO;

public interface OrderService {

    OrderDTO registerOrder(OrderDTO orderDTO);

    List<OrderDTO> getSalesByClientId(Long clientId);

    List<Map<Long, Integer>> getTopItems(Integer limit);

}
