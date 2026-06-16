package ims.ImsService;

import ims.domain.Product;

public class FixedReorderPolicy implements ReorderPolicy {

    private final int amount;

    public FixedReorderPolicy(int amount) {
        if(amount <= 0) throw new IllegalArgumentException();
        this.amount = amount;
    }

    @Override
    public int reorderQuantity(Product product, int currentQty) {
        return amount;
    }
}
