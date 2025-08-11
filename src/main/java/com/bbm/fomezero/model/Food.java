package com.bbm.fomezero.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "foods")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String description;
    private BigDecimal price;
    private boolean available;
    private boolean isVegetarian;
    private boolean isSeasonal;
    
    @CreationTimestamp
    private LocalDateTime creationDate;
    
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private FoodCategory category;
    
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    private List<FoodIngredient> ingredients = new ArrayList<>();
    
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();
    
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
}