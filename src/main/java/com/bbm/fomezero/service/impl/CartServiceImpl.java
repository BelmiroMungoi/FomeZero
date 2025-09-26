package com.bbm.fomezero.service.impl;

import com.bbm.fomezero.model.Cart;
import com.bbm.fomezero.model.User;
import com.bbm.fomezero.repository.CartRepository;
import com.bbm.fomezero.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Override
    @Transactional
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }
}
