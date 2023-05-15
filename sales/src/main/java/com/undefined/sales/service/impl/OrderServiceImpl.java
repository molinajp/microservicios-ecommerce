package com.undefined.sales.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.undefined.sales.dto.OrderDTO;
import com.undefined.sales.exceptions.OrderException;
import com.undefined.sales.model.OrderEntity;
import com.undefined.sales.repository.ItemRepository;
import com.undefined.sales.repository.OrderRepository;
import com.undefined.sales.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ItemRepository itemRepository;

    @Override
    public List<OrderDTO> getSalesByClientId(Long clientId) {
        List<OrderEntity> response = orderRepository.findSalesByClientId(clientId);
        if (response.isEmpty()) {
            throw new OrderException(new Date(), HttpStatus.NOT_FOUND.value(), " Order not found or doesnt exist. ");
        }
        return response.stream().map(OrderDTO::orderToDto).collect(Collectors.toList());
    }

    @Override
    public OrderDTO registerOrder(OrderDTO orderDTO) {
        OrderEntity response = orderRepository.save(OrderEntity.orderDTOToOrder(orderDTO));
        return OrderDTO.orderToDto(response);
    }

    @Override
    public List<Map<Long, Integer>> getTopItems(Integer limit) {
        return itemRepository.getTopItems(limit);
    }

}
