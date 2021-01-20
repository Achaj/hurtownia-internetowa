package main;

import main.entity.Product;

import java.util.ArrayList;
import java.util.List;

public  class TemporaryBracket {
    public static  List<Product> products=new ArrayList<>();

    public static List<Product> getProducts() {
        return products;
    }

    public  void setProducts(List<Product> products) {
        this.products = products;
    }
    public void addProduct(Product  product){
        if(product!=null) {
            products.add(product);
        }
    }

    public void printsTemporaryBracket(){
        if (products!=null) {
            for (Product product : products) {
                System.out.println(product.toString());
            }
        }
    }
}
