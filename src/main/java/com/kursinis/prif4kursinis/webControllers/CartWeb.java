package com.kursinis.prif4kursinis.webControllers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kursinis.prif4kursinis.hibernateControllers.CustomHib;
import com.kursinis.prif4kursinis.model.Cart;
import com.kursinis.prif4kursinis.model.Product;
import com.kursinis.prif4kursinis.webControllers.serializers.LocalDateGsonSerializer;
import com.kursinis.prif4kursinis.webControllers.serializers.ProductGsonSerializer;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;
import java.util.List;

@Controller
public class CartWeb {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("coursework-shop");
    CustomHib customHib = new CustomHib(entityManagerFactory);

    //Useris pagal id, GET su PathVariable
    @RequestMapping(value = "/product/getAllCarts", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getAllProducts(){

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        builder.registerTypeAdapter(Product.class, new ProductGsonSerializer());
        Gson gson = builder.create();

        List<Cart> cartlist = customHib.getAllRecords(Cart.class);

        return gson.toJson(cartlist);

    }

}
