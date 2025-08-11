package com.bbm.fomezero.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ingredient_itens")
public class IngredientItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private boolean inStock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private IngredientCategory category;

    @OneToMany(mappedBy = "ingredient")
    private List<FoodIngredient> foodIngredients = new ArrayList<>();
}