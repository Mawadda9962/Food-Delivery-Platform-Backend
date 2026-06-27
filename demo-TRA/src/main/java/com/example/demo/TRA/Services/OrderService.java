package com.example.demo.TRA.Services;

import com.example.demo.TRA.DTOs.RequestDTO.CorporateOrderRequestDTO;
import com.example.demo.TRA.DTOs.RequestDTO.OrderItemRequestDTO;
import com.example.demo.TRA.DTOs.ResponseDTO.CorporateOrderResponseDTO;
import com.example.demo.TRA.DTOs.ResponseDTO.OrderResponseDTO;
import com.example.demo.TRA.Entities.*;
import com.example.demo.TRA.Repositories.*;
import com.example.demo.TRA.Entities.*;
import com.example.demo.TRA.Exceptions.InvalidOrderStateException;
import com.example.demo.TRA.Exceptions.ResourceNotFoundException;
import com.example.demo.TRA.Repositories.*;
import com.example.demo.TRA.Utils.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

        Optional<Restaurant> restaurants = restaurantRepository.findActiveById(restaurantId);

        if (restaurants.isEmpty()) {
            throw new ResourceNotFoundException("Restaurant not found with id: " + restaurantId);
        }
        Restaurant restaurant = restaurants.get();

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
                MenuItem menuItem = menuItemRepository.findActiveById(itemDto.getMenuItemId())
                        .orElseThrow(() -> new ResourceNotFoundException("Menu item not found while creating order"));

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

    //add MenuItem To Order
    public OrderResponseDTO addMenuItemToOrder(Integer orderId, Integer menuItemId, int quantity) {
        Optional<Order> orders = orderRepository.findActiveById(orderId);

        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("Order not found with id: " + orderId);
        }

        Order order = orders.get();

        MenuItem menuItem = menuItemRepository.findActiveById(menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + menuItemId));

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
        Optional<Order> orders = orderRepository.findActiveById(orderId);
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
        Optional<Order> orders = orderRepository.findActiveById(orderId);
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("Order not found with id: " + orderId);
        }
        Order order = orders.get();

        order.setDiscountAmount(discountAmount);
        order.setUpdateDate(LocalDateTime.now());

        Order saved = orderRepository.save(order);
        return OrderResponseDTO.fromEntity(saved);
    }

    //Update Order State
    public OrderResponseDTO updateOrderStatus(Integer orderId, String newStatus) {
        Optional<Order> orders = orderRepository.findActiveById(orderId);
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("Order not found with id: " + orderId);
        }
        Order order = orders.get();

        order.setStatus(newStatus);
        order.setUpdateDate(LocalDateTime.now());

        Order saved = orderRepository.save(order);
        return OrderResponseDTO.fromEntity(saved);
    }

    // cancel Order
    public OrderResponseDTO cancelOrder(Integer orderId) {
        Optional<Order> orders = orderRepository.findActiveById(orderId);
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("Order not found with id: " + orderId);
        }
        Order order = orders.get();

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
        Optional<Order> orders = orderRepository.findActiveById(orderId);
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("Order not found with id: " + orderId);
        }
        Order order = orders.get();

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

    //place Corporate Order
    public CorporateOrderResponseDTO placeCorporateOrder(CorporateOrderRequestDTO dto) {
        Optional<Restaurant> restaurants = restaurantRepository.findActiveById(dto.getRestaurantId());
        if (restaurants.isEmpty()) {
            throw new ResourceNotFoundException("Restaurant not found with id: " + dto.getRestaurantId());
        }
        Restaurant restaurant = restaurants.get();

        CorporateOrder corporateOrder = dto.toEntity();
        corporateOrder.setCorporateCode(HelperUtils.generateCode("CORP"));
        corporateOrder.setRestaurant(restaurant);
        corporateOrder.setCreateDate(LocalDate.now());
        corporateOrder.setUpdateDate(LocalDateTime.now());
        corporateOrder.setIsActive(true);

        CorporateOrder saved = corporateOrderRepository.save(corporateOrder);
        return CorporateOrderResponseDTO.fromEntity(saved);
    }

    //confirm Order (lock the order as no more items can be added)
    public OrderResponseDTO confirmOrder(Integer orderId){
        Optional<Order> orders = orderRepository.findActiveById(orderId);
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("Order not found with id: " + orderId);
        }
        Order order = orders.get();
        if (!"PENDING".equals(order.getStatus())) {
            throw new InvalidOrderStateException(
                    "Order can only be confirmed when PENDING. Current status: " + order.getStatus());
        }

        order.setStatus("CONFIRMED");
        order.setUpdateDate(LocalDateTime.now());

        Order saved = orderRepository.save(order);
        return OrderResponseDTO.fromEntity(saved);
    }

    public OrderResponseDTO getOrderById(Integer orderId) {
        Optional<Order> orders = orderRepository.findActiveById(orderId);
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("Order not found with id: " + orderId);
        }
        return OrderResponseDTO.fromEntity(orders.get());
    }

    // Get Orders By Restaurant And Status
    public List<OrderResponseDTO> getOrdersByRestaurantAndStatus(Integer restaurantId, String status) {

        List<Order> orders = orderRepository.findByRestaurantIdAndStatus(restaurantId, status);

        return OrderResponseDTO.fromEntity(orders);
    }

    //Extended Use-Case
    //Status-change history for an order
    public Map<String, Object> getOrderTimeline(Integer orderId) {
        Order order = orderRepository.findActiveById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        Map<String, Object> timeline = new LinkedHashMap<>();
        timeline.put("orderCode", order.getOrderCode());
        timeline.put("currentStatus", order.getStatus());
        timeline.put("orderDate", order.getOrderDate());
        timeline.put("lastUpdated", order.getUpdateDate());

        if (order.getDelivery() != null) {
            timeline.put("assignedAt", order.getDelivery().getAssignedAt());
            timeline.put("pickedUpAt", order.getDelivery().getPickedUpAt());
            timeline.put("deliveredAt", order.getDelivery().getDeliveredAt());
        }

        return timeline;
    }


    //Duplicate a past order as a new PENDING order
    public OrderResponseDTO reorder(Integer orderId) {
        Order original = orderRepository.findActiveById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        Order newOrder = new Order();
        newOrder.setOrderCode(HelperUtils.generateCode("ORD"));
        newOrder.setOrderDate(LocalDate.now());
        newOrder.setStatus("PENDING");
        newOrder.setSubtotal(original.getSubtotal());
        newOrder.setDeliveryFee(original.getDeliveryFee());
        newOrder.setDiscountAmount(0.0);
        newOrder.setTotalAmount(original.getSubtotal() + original.getDeliveryFee());
        newOrder.setDeliveryNotes(original.getDeliveryNotes());
        newOrder.setCustomer(original.getCustomer());
        newOrder.setRestaurant(original.getRestaurant());
        newOrder.setCreateDate(LocalDate.now());
        newOrder.setUpdateDate(LocalDateTime.now());
        newOrder.setIsActive(true);

        Order saved = orderRepository.save(newOrder);

        List<OrderItem> originalItems = orderItemRepository.findByOrderId(orderId);
        for (OrderItem oi : originalItems) {
            OrderItem copy = new OrderItem();
            copy.setOrder(saved);
            copy.setMenuItem(oi.getMenuItem());
            copy.setQuantity(oi.getQuantity());
            copy.setUnitPrice(oi.getUnitPrice());
            copy.setTotalPrice(oi.getTotalPrice());
            copy.setSpecialInstructions(oi.getSpecialInstructions());
            copy.setCreateDate(LocalDate.now());
            copy.setUpdateDate(LocalDateTime.now());
            copy.setIsActive(true);
            orderItemRepository.save(copy);
        }

        return OrderResponseDTO.fromEntity(saved);
    }

    //Estimated delivery time based on driver location and distance
    public Map<String, Object> getOrderEta(Integer orderId) {
        Order order = orderRepository.findActiveById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        Map<String, Object> eta = new LinkedHashMap<>();
        eta.put("orderCode", order.getOrderCode());
        eta.put("status", order.getStatus());

        if (order.getDelivery() != null && order.getDelivery().getDeliveryDriver() != null) {
            DeliveryDriver driver = order.getDelivery().getDeliveryDriver();
            if (driver.getCurrentLat() != null && driver.getCurrentLng() != null
                    && order.getRestaurant().getLatitude() != null
                    && order.getRestaurant().getLongitude() != null) {

                double dist = HelperUtils.calculateDistance(
                        driver.getCurrentLat(), driver.getCurrentLng(),
                        order.getRestaurant().getLatitude(), order.getRestaurant().getLongitude());

                long etaMinutes = Math.round((dist / 30.0) * 60);
                eta.put("driverDistanceKm", dist);
                eta.put("estimatedMinutes", etaMinutes);
            } else {
                eta.put("estimatedMinutes", null);
                eta.put("note", "Driver location not available");
            }
        } else {
            eta.put("estimatedMinutes", null);
            eta.put("note", "No driver assigned yet");
        }

        return eta;
    }
}
