package com.example.demo_TRA.Services;

import com.example.demo_TRA.DTOs.RequestDTO.RestaurantRequestDTO;
import com.example.demo_TRA.DTOs.ResponseDTO.RestaurantResponseDTO;
import com.example.demo_TRA.Entities.Restaurant;
import com.example.demo_TRA.Entities.RestaurantOwner;
import com.example.demo_TRA.Exceptions.ResourceNotFoundException;
import com.example.demo_TRA.Repositories.MenuItemRepository;
import com.example.demo_TRA.Repositories.RestaurantOwnerRepository;
import com.example.demo_TRA.Repositories.RestaurantRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    RestaurantOwnerRepository restaurantOwnerRepository;

    @Autowired
    MenuItemRepository menuItemRepository;

    //create a brand-new Restaurant
    public RestaurantResponseDTO createResponse(RestaurantRequestDTO dto, Integer ownerId){
        List<RestaurantOwner> owners = restaurantOwnerRepository.findActiveById(ownerId);

        if (owners.isEmpty()) {
            throw new ResourceNotFoundException("Restaurant owner not found with id: " + ownerId);
        }

        RestaurantOwner owner = owners.get(0);

        Restaurant restaurant = dto.toEntity();
        restaurant.setOwner(owner);
        restaurant.setCreateDate(LocalDate.now());
        restaurant.setUpdateDate(LocalDateTime.now());
        restaurant.setIsActive(true);

        Restaurant saved = restaurantRepository.save(restaurant);
        return RestaurantResponseDTO.fromEntity(saved);
    }

    //Toggle Accepting Orders
    public RestaurantResponseDTO toggleAcceptingOrders(Integer restaurantId, boolean status) {
        List<Restaurant> result = restaurantRepository.findActiveById(restaurantId);

        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Restaurant not found with id: " + restaurantId);
        }

        Restaurant restaurant = result.get(0);
        restaurant.setAcceptingOrders(status);
        restaurant.setUpdateDate(LocalDateTime.now());

        Restaurant saved = restaurantRepository.save(restaurant);
        return RestaurantResponseDTO.fromEntity(saved);
    }

    //Updating deliveryFee
    public RestaurantResponseDTO updateDeliveryFee(Integer restaurantId, double newFee){
        List<Restaurant> result = restaurantRepository.findActiveById(restaurantId);

        if (result.isEmpty()){
            throw new ResourceNotFoundException("Restaurant not found with id: " + restaurantId);
        }

        Restaurant restaurant = result.get(0);
        restaurant.setDeliveryFee(newFee);
        restaurant.setUpdateDate(LocalDateTime.now());

        Restaurant saved = restaurantRepository.save(restaurant);
        return RestaurantResponseDTO.fromEntity(saved);
    }

    //get Restaurants By Cuisine
    public List<RestaurantResponseDTO> getRestaurantsByCuisine(String cuisine){
        List<Restaurant> restaurants = restaurantRepository.findByCuisineTypeIgnoreCase(cuisine);
        return RestaurantResponseDTO.fromEntity(restaurants);
    }

    //getRestaurantsUnderDeliveryFee (show me restaurants that charge no more than this much for delivery)
    public List<RestaurantResponseDTO> getRestaurantsUnderDeliveryFee(double maxFee){
        List<Restaurant> restaurants = restaurantRepository.findByDeliveryFeeLessThanEqual(maxFee);

    }





}
