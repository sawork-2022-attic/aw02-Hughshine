package com.example.poshell.biz;

import com.example.poshell.model.Cart;
import com.example.poshell.model.Product;

import java.util.List;

public interface PosService {
    public Cart getCart();

    public Cart newCart();

    public boolean modifyCart(Cart cart, int itemId, int newAmount);

    public void clearCart(Cart cart);

    public double checkout(Cart cart);

    public double total(Cart cart);

    public boolean add(Product product, int amount);

    public boolean add(String productId, int amount);

    public List<Product> products();
}
