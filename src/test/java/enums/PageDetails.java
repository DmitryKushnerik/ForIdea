package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PageDetails {
    PRODUCTS("Products", "inventory.html"),
    CART("Your Cart", "cart.html"),
    CHECKOUT1("Checkout: Your Information", "checkout-step-one.html"),
    CHECKOUT2("Checkout: Overview", "checkout-step-two.html"),
    CHECKOUT3("Checkout: Complete!", "checkout-complete.html");

    private final String pageName;
    private final String pageUrl;
}
