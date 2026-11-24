package Application.Cart.GetCart;

import java.util.List;

public record CartDto(List<CartItemDto> items, int totalAmount, boolean isEmpty) {}