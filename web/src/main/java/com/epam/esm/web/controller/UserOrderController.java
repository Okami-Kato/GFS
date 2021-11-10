package com.epam.esm.web.controller;

import com.epam.esm.service.UserOrderService;
import com.epam.esm.service.dto.request.CreateUserOrderRequest;
import com.epam.esm.service.dto.response.UserOrderItem;
import com.epam.esm.service.dto.response.UserOrderResponse;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ErrorCode;
import com.epam.esm.service.exception.InvalidEntityException;
import com.epam.esm.web.linker.UserOrderItemLinker;
import com.epam.esm.web.linker.UserOrderResponseLinker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.Optional;

@RestController
@Validated
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UserOrderController {
    private final UserOrderService orderService;

    private final UserOrderItemLinker orderItemLinker;
    private final UserOrderResponseLinker orderResponseLinker;

    private final PagedResourcesAssembler pagedResourcesAssembler;

    @Autowired
    public UserOrderController(UserOrderService orderService, UserOrderItemLinker orderItemLinker,
                               UserOrderResponseLinker orderResponseLinker, PagedResourcesAssembler pagedResourcesAssembler) {
        this.orderService = orderService;
        this.orderItemLinker = orderItemLinker;
        this.orderResponseLinker = orderResponseLinker;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    /**
     * Retrieves all orders of user with given id.
     *
     * @param pageable pagination restrictions.
     * @param userId   id of user.
     * @return page of found orders.
     * @throws EntityNotFoundException if user with given id wasn't found.
     */
    @PreAuthorize("hasRole('admin') || (hasRole('user') && #userId == authentication.getName())")
    @GetMapping(value = "/users/{userId}/orders")
    public PagedModel<UserOrderItem> findAllOrdersByUserId(@PathVariable String userId, Pageable pageable) {
        Page<UserOrderItem> ordersPage = orderService.findAllByUserId(userId, pageable);
        orderItemLinker.processCollection(ordersPage.getContent());
        return pagedResourcesAssembler.toModel(ordersPage);
    }

    /**
     * Retrieves all orders on certificate with given id.
     *
     * @param pageable      pagination restrictions.
     * @param certificateId id of certificate.
     * @return page of found orders.
     * @throws EntityNotFoundException if certificate with given id wasn't found.
     */
    @RolesAllowed("admin")
    @GetMapping(value = "/certificates/{certificateId}/orders")
    public PagedModel<UserOrderItem> findAllOrdersByCertificateId(@PathVariable Integer certificateId,
                                                                  Pageable pageable) {
        Page<UserOrderItem> ordersPage = orderService.findAllByCertificateId(certificateId, pageable);
        orderItemLinker.processCollection(ordersPage.getContent());
        return pagedResourcesAssembler.toModel(ordersPage);
    }

    /**
     * Retrieves order with given id.
     *
     * @param id id of desired order.
     * @return found order.
     * @throws EntityNotFoundException if order wasn't found.
     */
    @GetMapping(value = "/orders/{id}")
    public UserOrderResponse findOrder(@PathVariable int id, Principal principal) {
        Optional<UserOrderResponse> response = orderService.findById(id);
        if (response.isPresent()) {
            if (!response.get().getUserId().equals(principal.getName())) {
                throw new AccessDeniedException("Can't access other user's order");
            }
            orderResponseLinker.processEntity(response.get());
        }
        return response.orElseThrow(() -> new EntityNotFoundException("id=" + id, ErrorCode.USER_ORDER_NOT_FOUND));
    }

    /**
     * Creates new order from authorized user on certificate with given id.
     *
     * @param certificateId certificate to order.
     * @param principal     currently authorized user.
     * @return created order, if order is valid and service call was successful.
     * @throws IllegalArgumentException if order is null.
     * @throws InvalidEntityException   if order is invalid.
     */
    @PostMapping(value = "/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public UserOrderResponse createUserOrder(@RequestParam Integer certificateId, Principal principal) {
        UserOrderResponse response = orderService.create(new CreateUserOrderRequest(principal.getName(), certificateId));
        orderResponseLinker.processEntity(response);
        return response;
    }
}
