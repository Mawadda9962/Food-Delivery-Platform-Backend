package com.example.demo_TRA.Services;

import com.example.demo_TRA.DTOs.RequestDTO.CorporateOrderRequestDTO;
import com.example.demo_TRA.DTOs.RequestDTO.OrderItemRequestDTO;
import com.example.demo_TRA.DTOs.RequestDTO.OrderRequestDTO;
import com.example.demo_TRA.DTOs.ResponseDTO.CorporateOrderResponseDTO;
import com.example.demo_TRA.DTOs.ResponseDTO.OrderResponseDTO;
import com.example.demo_TRA.Entities.*;
import com.example.demo_TRA.Exceptions.InvalidOrderStateException;
import com.example.demo_TRA.Exceptions.ResourceNotFoundException;
import com.example.demo_TRA.Repositories.*;
import com.example.demo_TRA.Utils.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    CorporateOrderRepository corporateOrderRepository;

    @Autowired
    MenuItemRepository menuItemRepository;


    //Create Order
    public OrderResponseDTO createOrder(Integer customerId, Integer restaurantId, List<OrderItemRequestDTO> items) {
        return createOrder(customerId, restaurantId, items, null);
    }

    public OrderResponseDTO createOrder(Integer customerId, Integer restaurantId, List<OrderItemRequestDTO> items, String notes) {
        Customer customer = customerRepository.findActiveById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));

        List<Restaurant> restaurants = restaurantRepository.findActiveById(restaurantId);

        if (restaurants.isEmpty()) {
            throw new ResourceNotFoundException("Restaurant not found with id: " + restaurantId);
        }
        Restaurant restaurant = restaurants.get(0);

        Order order = new Order();
        order.setOrderCode(HelperUtils.generateCode("ORD"));
        order.setOrderDate(LocalDate.now());
        order.setStatus("PENDING");
        order.setSubtotal(0.0);
        order.setDeliveryFee(restaurant.getDeliveryFee());
        order.setDiscountAmount(0.0);
        order.setTotalAmount(restaurant.getDeliveryFee());
        order.setDeliveryNotes(notes);
        order.setCustomer(customer);
        order.setRestaurant(restaurant);
        order.setCreateDate(LocalDate.now());
        order.setUpdateDate(LocalDateTime.now());
        order.setIsActive(true);

        Order savedOrder = orderRepository.save(order);

        double subtotal = 0.0;

        if (items != null) {
            for (OrderItemRequestDTO itemDto : items) {
                List<MenuItem> menuItems = menuItemRepository.findActiveById(itemDto.getMenuItemId());
                if (menuItems.isEmpty()) {
                    throw new ResourceNotFoundException("Menu item not found while creating order");
                }

                MenuItem menuItem = menuItems.get(0);

                double itemTotal = menuItem.getPrice() * itemDto.getQuantity();

                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(savedOrder);
                orderItem.setMenuItem(menuItem);
                orderItem.setQuantity(itemDto.getQuantity());
                orderItem.setUnitPrice(menuItem.getPrice());
                orderItem.setTotalPrice(itemTotal);
                orderItem.setSpecialInstructions(itemDto.getSpecialInstructions());
                orderItem.setCreateDate(LocalDate.now());
                orderItem.setUpdateDate(LocalDateTime.now());
                orderItem.setIsActive(true);

                orderItemRepository.save(orderItem);

                subtotal += itemTotal;
            }

        }
        double total = HelperUtils.calculateTotal(subtotal, savedOrder.getDeliveryFee(), savedOrder.getDiscountAmount());

        savedOrder.setSubtotal(subtotal);
        savedOrder.setTotalAmount(total);
        savedOrder.setUpdateDate(LocalDateTime.now());

        Order finalOrder = orderRepository.save(savedOrder);

        return OrderResponseDTO.fromEntity(finalOrder);
    }

    public OrderResponseDTO addMenuItemToOrder(Integer orderId, Integer menuItemId, int quantity) {
        List<Order> orders = orderRepository.findActiveById(orderId);

        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("Order not found with id: " + orderId);
        }

        Order order = orders.get(0);

        List<MenuItem> menuItems = menuItemRepository.findActiveById(menuItemId);
        if (menuItems.isEmpty()) {
            throw new ResourceNotFoundException("Menu item not found with id: " + menuItemId);
        }
        MenuItem menuItem = menuItems.get(0);

        double itemTotal = menuItem.getPrice() * quantity;

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setMenuItem(menuItem);
        orderItem.setQuantity(quantity);
        orderItem.setUnitPrice(menuItem.getPrice());
        orderItem.setTotalPrice(itemTotal);
        orderItem.setCreateDate(LocalDate.now());
        orderItem.setUpdateDate(LocalDateTime.now());
        orderItem.setIsActive(true);

        orderItemRepository.save(orderItem);
        return OrderResponseDTO.fromEntity(order);
    }

    //Remove Menu Item From Order

    public void removeMenuItemFromOrder(Integer orderId, Integer orderItemId) {
        List<Order> orders = orderRepository.findActiveById(orderId);
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("Order not found with id: " + orderId);
        }

        List<OrderItem> orderItems = orderItemRepository.findActiveById(orderItemId);
        if (orderItems.isEmpty()) {
            throw new ResourceNotFoundException("Order item not found with id: " + orderItemId);
        }

        OrderItem orderItem = orderItems.get(0);
        orderItem.setIsActive(false);
        orderItem.setUpdateDate(LocalDateTime.now());
        orderItemRepository.save(orderItem);
    }

    // apply Discount
    public OrderResponseDTO applyDiscount(Integer orderId, double discountAmount) {
        List<Order> orders = orderRepository.findActiveById(orderId);
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("Order not found with id: " + orderId);
        }
        Order order = orders.get(0);

        order.setDiscountAmount(discountAmount);
        order.setUpdateDate(LocalDateTime.now());

        Order saved = orderRepository.save(order);
        return OrderResponseDTO.fromEntity(saved);
    }

    //Update Order State
    public OrderResponseDTO updateOrderStatus(Integer orderId, String newStatus) {
        List<Order> orders = orderRepository.findActiveById(orderId);
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("Order not found with id: " + orderId);
        }
        Order order = orders.get(0);

        order.setStatus(newStatus);
        order.setUpdateDate(LocalDateTime.now());

        Order saved = orderRepository.save(order);
        return OrderResponseDTO.fromEntity(saved);
    }

    // cancel Order
    public OrderResponseDTO cancelOrder(Integer orderId) {
        List<Order> orders = orderRepository.findActiveById(orderId);
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("Order not found with id: " + orderId);
        }
        Order order = orders.get(0);

        if (!"PENDING".equals(order.getStatus())) {
            throw new InvalidOrderStateException("Order cannot be cancelled unless it is PENDING. Current status: " + order.getStatus());
        }

        order.setStatus("CANCELLED");
        order.setUpdateDate(LocalDateTime.now());

        Order saved = orderRepository.save(order);
        return OrderResponseDTO.fromEntity(saved);
    }

    // calculate Order Totals
    public OrderResponseDTO calculateOrderTotals(Integer orderId) {
        List<Order> orders = orderRepository.findActiveById(orderId);
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("Order not found with id: " + orderId);
        }
        Order order = orders.get(0);

        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);

        double subtotal = 0.0;
        for (OrderItem item : orderItems) {
            subtotal += item.getTotalPrice();
        }

        double fee = order.getDeliveryFee() != null ? order.getDeliveryFee() : 0.0;
        double discount = order.getDiscountAmount() != null ? order.getDiscountAmount() : 0.0;
        double total = HelperUtils.calculateTotal(subtotal, fee, discount);

        order.setSubtotal(subtotal);
        order.setTotalAmount(total);
        order.setUpdateDate(LocalDateTime.now());

        Order saved = orderRepository.save(order);
        return OrderResponseDTO.fromEntity(saved);
    }

    public CorporateOrderResponseDTO placeCorporateOrder(CorporateOrderRequestDTO dto) {
        List<Restaurant> restaurants = restaurantRepository.findActiveById(dto.getRestaurantId());
        if (restaurants.isEmpty()) {
            throw new ResourceNotFoundException("Restaurant not found with id: " + dto.getRestaurantId());
        }
        Restaurant restaurant = restaurants.get(0);

        CorporateOrder corporateOrder = dto.toEntity();
        corporateOrder.setCorporateCode(HelperUtils.generateCode("CORP"));
        corporateOrder.setRestaurant(restaurant);
        corporateOrder.setCreateDate(LocalDate.now());
        corporateOrder.setUpdateDate(LocalDateTime.now());
        corporateOrder.setIsActive(true);

        CorporateOrder saved = corporateOrderRepository.save(corporateOrder);
        return CorporateOrderResponseDTO.fromEntity(saved);
    }
}
