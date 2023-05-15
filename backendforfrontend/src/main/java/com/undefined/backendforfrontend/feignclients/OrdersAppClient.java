package com.undefined.backendforfrontend.feignclients;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.undefined.backendforfrontend.dto.OrderCreationDTO;
import com.undefined.backendforfrontend.dto.OrderDTO;

@FeignClient(name = "orders-service", path = "${orders-service.base-path}")
public interface OrdersAppClient {

    @PostMapping("/orders")
    OrderDTO registerOrder(@RequestBody OrderCreationDTO orderCreationDTO);

    @GetMapping("/orders")
    List<OrderDTO> getSalesByClientId(@RequestParam Long clientId);

    @GetMapping("/top-items")
    List<Map<String, String>> getItems(@RequestParam Integer limit);

    @GetMapping("/actuator/health")
    Map<String, String> checkHealth();

}
