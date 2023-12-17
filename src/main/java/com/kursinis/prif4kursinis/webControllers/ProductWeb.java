package com.kursinis.prif4kursinis.webControllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kursinis.prif4kursinis.hibernateControllers.CustomHib;
import com.kursinis.prif4kursinis.model.Manager;
import com.kursinis.prif4kursinis.model.Product;
import com.kursinis.prif4kursinis.model.User;
import com.kursinis.prif4kursinis.webControllers.serializers.LocalDateGsonSerializer;
import com.kursinis.prif4kursinis.webControllers.serializers.ManagerGsonSerializer;
import com.kursinis.prif4kursinis.webControllers.serializers.ProductGsonSerializer;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ProductWeb {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("coursework-shop");
    CustomHib customHib = new CustomHib(entityManagerFactory);

    //Useris pagal id, GET su PathVariable
    @RequestMapping(value = "/product/getAllProducts", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getAllProducts() {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        builder.registerTypeAdapter(Product.class, new ProductGsonSerializer());
        Gson gson = builder.create();

        List<Product> productList = customHib.getAllRecords(Product.class);

        return gson.toJson(productList);

    }

    @RequestMapping(value ="/product/getAllProductsByid/{id}",method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getAllProductsByid(@PathVariable("id") int id) {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        builder.registerTypeAdapter(Product.class, new ProductGsonSerializer());
        Gson gson = builder.create();

        List<Product> productList = customHib.getAllRecords(Product.class);

        return gson.toJson(productList);

    }

    @RequestMapping(value = "/product/getAllProductsByWarehouse/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getAllProductsByWarehouse(@PathVariable("id") int id) {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        builder.registerTypeAdapter(Product.class, new ProductGsonSerializer());
        Gson gson = builder.create();

        List<Product> productList = customHib.getAllRecords(Product.class);

        return gson.toJson(productList);

    }

//update product put
    @RequestMapping(value = "/product/updateProduct", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String updateProduct(@RequestBody Product product) {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        builder.registerTypeAdapter(Product.class, new ProductGsonSerializer());
        Gson gson = builder.create();

        customHib.update(product);

        return gson.toJson(product);

    }

    @RequestMapping(value = "/product/createProduct", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String addProduct(@RequestBody Product product) {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        builder.registerTypeAdapter(Product.class, new ProductGsonSerializer());
        Gson gson = builder.create();

        customHib.create(product);

        return gson.toJson(product);

    }

    @RequestMapping(value = "/product/deleteProduct/{id}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String deleteProduct(@PathVariable("id") int id) {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        builder.registerTypeAdapter(Product.class, new ProductGsonSerializer());
        Gson gson = builder.create();

        customHib.delete(Product.class, id);

        return gson.toJson(id);

    }



}
