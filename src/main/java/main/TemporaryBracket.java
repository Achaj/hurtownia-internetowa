package main;

import main.entity.Product;

import java.util.List;

public  class TemporaryBracket {
    private static  List<Product> products;

    public static List<Product> getProducts() {
        return products;
    }

    public  void setProducts(List<Product> products) {
        this.products = products;
    }
    public void printsTemporaryBracket(){
        if (products!=null) {
            for (Product product : products) {
                System.out.println(product.toString());
            }
        }
    }
}
