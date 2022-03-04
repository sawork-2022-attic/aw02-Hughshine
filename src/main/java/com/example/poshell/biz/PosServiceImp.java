package com.example.poshell.biz;

import com.example.poshell.db.PosDB;
import com.example.poshell.model.Cart;
import com.example.poshell.model.Item;
import com.example.poshell.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PosServiceImp implements PosService {

    private PosDB posDB;

    @Autowired
    public void setPosDB(PosDB posDB) {
        this.posDB = posDB;
    }

    @Override
    public Cart getCart() {
        return posDB.getCart();
    }

    @Override
    public Cart newCart() {
        return posDB.saveCart(new Cart());
    }

    @Override
    public void clearCart(Cart cart) {
        if (cart != null) {
            cart.getItems().clear();
        }
    }

    @Override
    public boolean modifyCart(Cart cart, int itemId, int newAmount) {
        if (cart != null) {
            if (itemId > cart.getItems().size() || itemId <= 0) return false;
            Item item = cart.getItems().get(itemId-1);
            if (newAmount <= 0) {
                cart.getItems().remove(itemId-1);
            } else {
                item.setAmount(newAmount);
            }
        }
        return true;
    }

    @Override
    public double checkout(Cart cart) {
        if (cart == null) {
            return 0;
        }
        double total = cart.total();
        clearCart(cart);
        return total;
    }

    @Override
    public double total(Cart cart) {
        if (cart == null) {
            return 0;
        }
        return cart.total();
    }

    @Override
    public boolean add(Product product, int amount) {
        return false;
    }

    @Override
    public boolean add(String productId, int amount) {

        Product product = posDB.getProduct(productId);
        if (product == null) return false;

        this.getCart().addItem(new Item(product, amount));
        return true;
    }

    @Override
    public List<Product> products() {
        return posDB.getProducts();
    }
}
