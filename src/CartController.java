package poly.edu.ASSM.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import poly.edu.ASSM.Services.util.CartService;

@RestController
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/cart/add/{id}")
    public void addToCart(@PathVariable Integer id) {
        cartService.add(id);
    }

    @GetMapping("/cart/count")
    public int countCart() {
        return cartService.getCount();
    }
}
