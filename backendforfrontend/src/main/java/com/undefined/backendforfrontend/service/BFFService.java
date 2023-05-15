package com.undefined.backendforfrontend.service;

import java.util.List;

import com.undefined.backendforfrontend.dto.ClientDTO;
import com.undefined.backendforfrontend.dto.ProcessOrderDTO;
import com.undefined.backendforfrontend.dto.ProductDTO;
import com.undefined.backendforfrontend.dto.SalesReportDTO;
import com.undefined.backendforfrontend.dto.TopItemsDTO;
import com.undefined.backendforfrontend.exception.CreateOrderException;

public interface BFFService {

    public SalesReportDTO generateReport(String clientId);

    public ClientDTO getOneClient(Long clientId);

    public List<ProductDTO> getAllProducts();


    public String createOrder(ProcessOrderDTO processOrderDTO) throws CreateOrderException;

    public List<TopItemsDTO> getTopItems(Integer limit);


}
