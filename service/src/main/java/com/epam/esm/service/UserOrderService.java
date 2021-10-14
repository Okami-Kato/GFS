package com.epam.esm.service;

import com.epam.esm.service.dto.request.CreateUserOrderRequest;
import com.epam.esm.service.dto.response.UserOrderItem;
import com.epam.esm.service.dto.response.UserOrderResponse;

import java.util.List;
import java.util.Optional;

public interface UserOrderService {
    List<UserOrderItem> findAll(int pageNumber, int pageSize);

    List<UserOrderItem> findAllByUserId(int pageNumber, int pageSize, int userId);

    List<UserOrderItem> findAllByCertificateId(int pageNumber, int pageSize, int certificateId);

    Optional<UserOrderResponse> find(int id);

    long getCount();

    UserOrderResponse create(CreateUserOrderRequest userOrder);
}
