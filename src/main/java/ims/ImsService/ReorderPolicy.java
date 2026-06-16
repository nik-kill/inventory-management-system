package ims.ImsService;

import ims.domain.Product;

public interface ReorderPolicy {

    int reorderQuantity(Product product, int currentQty);
}
