package com.undefined.backendforfrontend.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.undefined.backendforfrontend.dto.ClientDTO;
import com.undefined.backendforfrontend.dto.ProcessOrderDTO;
import com.undefined.backendforfrontend.dto.ProductDTO;
import com.undefined.backendforfrontend.dto.SalesReportDTO;
import com.undefined.backendforfrontend.dto.TopItemsDTO;
import com.undefined.backendforfrontend.exception.CreateOrderException;
import com.undefined.backendforfrontend.service.impl.BffServiceImpl;

@ExtendWith(MockitoExtension.class)
class BFFControllerTest {

    @InjectMocks
    private BFFController controller;

    @Mock
    private BffServiceImpl service;
    private List<ProductDTO> productList;
    private ProcessOrderDTO processOrderDTO;

    @BeforeEach
    void setUp() {
        productList = new ArrayList<>();
        processOrderDTO = new ProcessOrderDTO();
    }

    @Test
    void testGetSalesByClient() {
        when(service.generateReport("1")).thenReturn(new SalesReportDTO());
        assertThat(controller.getSalesByClient("1")).isInstanceOf(ResponseEntity.class);
    }

    @Test
    void testGetSalesByClientThrowsException() {
        when(service.generateReport("1")).thenCallRealMethod();
        assertThatThrownBy(() -> controller.getSalesByClient("1")).isInstanceOf(Exception.class);
    }

    @Test
    void testGetClientById() {
        when(service.getOneClient(1L)).thenReturn(new ClientDTO());
        assertThat(controller.getClientById(1L)).isInstanceOf(ResponseEntity.class);
    }

    @Test
    void testGetClientByIdThrowsException() {
        when(service.getOneClient(1L)).thenCallRealMethod();
        assertThatThrownBy(() -> controller.getClientById(1L)).isInstanceOf(Exception.class);
    }

    @Test
    void testGetAllProducts() {

        when(service.getAllProducts()).thenReturn(productList);
        assertThat(controller.getAllProducts()).isInstanceOf(ResponseEntity.class);
    }

    @Test
    void testGetAllProductsThrowsException() {
        when(service.getAllProducts()).thenCallRealMethod();
        assertThatThrownBy(() -> controller.getAllProducts()).isInstanceOf(Exception.class);
    }

    @Test
    void testRegisterOrder() {
        when(service.createOrder(processOrderDTO)).thenReturn("good");
        assertThat(controller.registerOrder(processOrderDTO)).isInstanceOf(ResponseEntity.class);
    }

    @Test
    void testRegisterOrderThrowsException() {
        doThrow(new CreateOrderException("cucumber")).when(service).createOrder(processOrderDTO);
        assertThatThrownBy(() -> controller.registerOrder(processOrderDTO)).isInstanceOf(CreateOrderException.class);
    }

    @Test
    void testGetTopItems() {
        when(service.getTopItems(ArgumentMatchers.anyInt())).thenReturn(List.of(new TopItemsDTO()));
        assertThat(controller.getItems(ArgumentMatchers.anyInt())).isInstanceOf(ResponseEntity.class);
    }
}
