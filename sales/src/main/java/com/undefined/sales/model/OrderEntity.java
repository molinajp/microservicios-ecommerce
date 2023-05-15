package com.undefined.sales.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.undefined.sales.dto.OrderDTO;
import com.undefined.sales.utils.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEntity {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "order_date")
    private Date orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "pay_method")
    private PaymentMethod paymethod;

    @Column(name = "client_id")
    private Long clientId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<ItemEntity> item;

    public static OrderEntity orderDTOToOrder(OrderDTO orderDTO) {

        return OrderEntity.builder().clientId(orderDTO.getClientId()).item(orderDTO.getItems().stream()
                .map(item -> ItemEntity.builder().productId(item.getProductId()).quantity(item.getQuantity()).build())
                .collect(Collectors.toList())).orderDate(new Date()).totalAmount(orderDTO.getTotalAmount())
                .paymethod(orderDTO.getPaymethod()).build();
    }
}
