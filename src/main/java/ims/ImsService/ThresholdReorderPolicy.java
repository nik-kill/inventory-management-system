package ims.ImsService;

import ims.domain.Product;

public class ThresholdReorderPolicy implements ReorderPolicy {
    @Override
    public int reorderQuantity(Product product, int currentQty) {
        return product.getReorderThreshold();
    }
}
