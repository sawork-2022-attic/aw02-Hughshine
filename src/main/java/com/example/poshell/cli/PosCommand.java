package com.example.poshell.cli;

import com.example.poshell.biz.PosService;
import com.example.poshell.model.Cart;
import com.example.poshell.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class PosCommand {

    private PosService posService;

    @Autowired
    public void setPosService(PosService posService) {
        this.posService = posService;
    }

    @ShellMethod(value = "List Products", key = "p")
    public String products() {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (Product product : posService.products()) {
            stringBuilder.append("\t").append(++i).append("\t").append(product).append("\n");
        }
        return stringBuilder.toString();
    }

    @ShellMethod(value = "New Cart", key = "n")
    public String newCart() {
        return posService.newCart() + " OK";
    }

    @ShellMethod(value = "Add a Product to Cart", key = "a")
    public String addToCart(String productId, int amount) {
        if (posService.add(productId, amount)) {
            return posService.getCart().toString();
        }
        return "ERROR: No Cart";
    }

    @ShellMethod(value = "List Products in Cart", key = "l")
    public String listCart() {
        Cart cart = posService.getCart();
        if (cart == null) {
            return "ERROR: No Cart";
        }
        return cart.toString();
    }

    @ShellMethod(value = "Empty Your Cart", key = "e")
    public String emptyCart() {
        Cart cart = posService.getCart();
        if (cart == null) {
            return "ERROR: No Cart";
        }
        posService.clearCart(cart);
        return "OK";
    }

    @ShellMethod(value = "Modify Your Cart", key = "m")
    public String modifyCart(int itemId, int newAmount) {
        Cart cart = posService.getCart();
        if (cart == null) {
            return "ERROR: No Cart";
        }
        if (!posService.modifyCart(cart, itemId, newAmount)) {
            return "ERROR: No Such Item";
        }
        return "OK";
    }

    @ShellMethod(value = "Calculate Total Cost", key = "t")
    public String totalCost() {
        Cart cart = posService.getCart();
        if (cart == null) {
            return "ERROR: No Cart";
        }
        return "Total Cost: " + posService.total(cart);
    }

    @ShellMethod(value = "Checkout", key = "c")
    public String checkOut() {
        Cart cart = posService.getCart();
        if (cart == null) {
            return "ERROR: No Cart";
        }
        double total = posService.total(cart);
        posService.clearCart(cart);
        return "You have paid $" + total + ", thanks";
    }
}
