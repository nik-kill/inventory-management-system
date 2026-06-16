package ims.ImsService;

import ims.domain.Product;

public interface StockAlert {
    void onLowStock(Product product, String warhouseCode, int currentQty);
}
