package com.epam.esm.service;

import com.epam.esm.service.dto.request.CreateUserOrderRequest;
import com.epam.esm.service.dto.response.UserOrderItem;
import com.epam.esm.service.dto.response.UserOrderResponse;

import java.util.List;
import java.util.Optional;

public interface UserOrderService {
    List<UserOrderItem> getAll(int pageNumber, int pageSize);

    List<UserOrderItem> getAll(int pageNumber, int pageSize, int userId);

    Optional<UserOrderResponse> get(int id);

    UserOrderResponse create(CreateUserOrderRequest userOrder);
}
