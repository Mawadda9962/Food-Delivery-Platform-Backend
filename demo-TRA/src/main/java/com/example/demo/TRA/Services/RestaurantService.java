package com.example.demo.TRA.Services;

import com.example.demo.TRA.DTOs.RequestDTO.ComboMealRequestDTO;
import com.example.demo.TRA.DTOs.RequestDTO.MenuItemRequestDTO;
import com.example.demo.TRA.DTOs.RequestDTO.RestaurantRequestDTO;
import com.example.demo.TRA.DTOs.ResponseDTO.ComboMealResponseDTO;
import com.example.demo.TRA.DTOs.ResponseDTO.MenuItemResponseDTO;
import com.example.demo.TRA.DTOs.ResponseDTO.RestaurantResponseDTO;
import com.example.demo.TRA.Entities.ComboMeal;
import com.example.demo.TRA.Entities.MenuItem;
import com.example.demo.TRA.Entities.Restaurant;
import com.example.demo.TRA.Entities.RestaurantOwner;
import com.example.demo.TRA.Exceptions.ResourceNotFoundException;
import com.example.demo.TRA.Repositories.ComboMealRepository;
import com.example.demo.TRA.Repositories.MenuItemRepository;
import com.example.demo.TRA.Repositories.RestaurantOwnerRepository;
import com.example.demo.TRA.Repositories.RestaurantRepository;
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

    @Autowired
    ComboMealRepository comboMealRepository;

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
        Restaurant restaurant = restaurantRepository.findActiveById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

        restaurant.setAcceptingOrders(status);
        restaurant.setUpdateDate(LocalDateTime.now());

        Restaurant saved = restaurantRepository.save(restaurant);
        return RestaurantResponseDTO.fromEntity(saved);
    }

    //Updating deliveryFee
    public RestaurantResponseDTO updateDeliveryFee(Integer restaurantId, double newFee){
        Restaurant restaurant = restaurantRepository.findActiveById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

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
    public List<RestaurantResponseDTO> getRestaurantsUnderDeliveryFee(double maxFee) {
        List<Restaurant> restaurants = restaurantRepository.findByDeliveryFeeLessThanEqual(maxFee);
        return RestaurantResponseDTO.fromEntity(restaurants);
    }

    //get Menu For Restaurant
    public List<MenuItemResponseDTO> getMenuForRestaurant(Integer restaurantId){
        restaurantRepository.findActiveById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

        List<MenuItem> menuItems = menuItemRepository.findByRestaurantId(restaurantId);
        return MenuItemResponseDTO.fromEntity(menuItems);
    }


    //bulk Update Menu Item Prices
    public List<MenuItemResponseDTO> bulkUpdateMenuItemPrices(Integer restaurantId, double percentageIncrease) {
        restaurantRepository.findActiveById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

        List<MenuItem> menuItems = menuItemRepository.findByRestaurantId(restaurantId);

        for (MenuItem item : menuItems) {
            double updatedPrice = item.getPrice() + (item.getPrice() * (percentageIncrease / 100));
            item.setPrice(updatedPrice);
            item.setUpdateDate(LocalDateTime.now());
            menuItemRepository.save(item);
        }
        return MenuItemResponseDTO.fromEntity(menuItems);
    }

    //Get All Restaurants
    public List<RestaurantResponseDTO> getAllRestaurants(){
        List<Restaurant> restaurants = restaurantRepository.findAllActiveRestaurants();
        return RestaurantResponseDTO.fromEntity(restaurants);
    }

    //Get Restaurants by ID
    public RestaurantResponseDTO getRestaurantById(Integer restaurantId){
        Restaurant restaurant = restaurantRepository.findActiveById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

        return RestaurantResponseDTO.fromEntity(restaurant);
    }

    //Add new Item to Restaurant
    public MenuItemResponseDTO addMenuItem(Integer restaurantId, MenuItemRequestDTO dto){
        Restaurant restaurant = restaurantRepository.findActiveById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

        MenuItem menuItem = dto.toEntity();
        menuItem.setRestaurant(restaurant);
        menuItem.setIsActive(true);
        menuItem.setCreateDate(LocalDate.now());
        menuItem.setUpdateDate(LocalDateTime.now());

        MenuItem saved = menuItemRepository.save(menuItem);
        return MenuItemResponseDTO.fromEntity(saved);
    }

    //Mark MenuItem Available (Out of Stock)
    public MenuItemResponseDTO setMenuItemAvailability(Integer itemId, boolean status) {
        MenuItem menuItem = menuItemRepository.findActiveById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + itemId));

        menuItem.setIsAvailable(status);
        menuItem.setUpdateDate(LocalDateTime.now());

        MenuItem saved = menuItemRepository.save(menuItem);
        return MenuItemResponseDTO.fromEntity(saved);
    }

    //Get All ComboMeal for Restaurant
    public List<ComboMealResponseDTO> getCombosForRestaurant(Integer restaurantId) {
        restaurantRepository.findActiveById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

        List<ComboMeal> comboMeals = comboMealRepository.findByRestaurantId(restaurantId);
        return ComboMealResponseDTO.fromEntity(comboMeals);
    }

    //Create a new ComboMeal
    public ComboMealResponseDTO createComboMeal(Integer restaurantId, ComboMealRequestDTO dto) {
        Restaurant restaurant = restaurantRepository.findActiveById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

        ComboMeal comboMeal = dto.toEntity();
        comboMeal.setRestaurant(restaurant);
        comboMeal.setIsActive(true);
        comboMeal.setCreateDate(LocalDate.now());
        comboMeal.setUpdateDate(LocalDateTime.now());

        ComboMeal saved = comboMealRepository.save(comboMeal);
        return ComboMealResponseDTO.fromEntity(saved);
    }
}
