package com.batch.example.BatchProcessing.processor;

import com.batch.example.BatchProcessing.entity.Product;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;


public class CustomerItemProcessor implements ItemProcessor<Product,Product> {


    @Override
    public Product process(Product item) throws Exception {

        int discountPer=Integer.parseInt(item.getDiscount());
        double originalPrice=Double.parseDouble(item.getPrice());
        double discount=(discountPer/100)*originalPrice;
        double finalPrice=originalPrice-discount;
        item.setDiscounted_price(String.valueOf(finalPrice));

        return item;
    }
}
