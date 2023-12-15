package com.kursinis.prif4kursinis.webControllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kursinis.prif4kursinis.hibernateControllers.CustomHib;
import com.kursinis.prif4kursinis.model.Customer;
import com.kursinis.prif4kursinis.model.Manager;
import com.kursinis.prif4kursinis.model.User;
import com.kursinis.prif4kursinis.model.Warehouse;
import com.kursinis.prif4kursinis.webControllers.serializers.CustomerGsonSerializer;
import com.kursinis.prif4kursinis.webControllers.serializers.LocalDateGsonSerializer;
import com.kursinis.prif4kursinis.webControllers.serializers.ManagerGsonSerializer;
import com.kursinis.prif4kursinis.webControllers.serializers.WarehouseGsonSerializer;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Properties;

@Controller
public class WarehouseWeb {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("coursework-shop");
    CustomHib customHib = new CustomHib(entityManagerFactory);

    @RequestMapping(value = "/Warehouse/getWarehouseById/{id}", method = RequestMethod.GET)

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getWarehousesById(@PathVariable(name = "id") int id){

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Warehouse.class, new WarehouseGsonSerializer());
        //builder.registerTypeAdapter(User.class, new UserGsonSerializer());
        Gson gson = builder.create();


        Warehouse warehouse = customHib.getEntityById(Warehouse.class, id);

        return warehouse != null ? gson.toJson(warehouse) : "";

    }




    @RequestMapping(value = "/Warehouse/deleteWarehouse/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String deleteWarehouse(@PathVariable(name = "id") int id) {
        System.out.println("Deleting warehouse with ID: " + id);
        customHib.delete(Warehouse.class, id);
        return "Warehouse deleted";
    }

    @RequestMapping(value = "/Warehouse/createWarehouse", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String createWarehouse(@RequestBody String data) {
        System.out.println("Creating warehouse");
        Gson parser = new Gson();
        Properties properties = parser.fromJson(data, Properties.class);

        Warehouse warehouse = new Warehouse();
        warehouse.setTitle(properties.getProperty("title"));
        warehouse.setAddress(properties.getProperty("address"));


        customHib.createWarehouse(properties.getProperty("title"), properties.getProperty("address"));
        return "Warehouse created";
    }

    @RequestMapping(value = "/Warehouse/updateWarehouse", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String updateWarehouse(@RequestBody String data) {
        System.out.println("Updating warehouse");
        Gson parser = new Gson();
        Properties properties = parser.fromJson(data, Properties.class);

        Warehouse warehouse = new Warehouse();
        warehouse.setTitle(properties.getProperty("title"));
        warehouse.setAddress(properties.getProperty("address"));

        customHib.update(warehouse);
        return "Warehouse updated";
    }



    @RequestMapping(value = "/Warehouse/getWarehouseByCredentials", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getWarehouseByCredentials(@RequestBody String data) {
        System.out.println(data);
        Gson parser = new Gson();
        Properties properties = parser.fromJson(data, Properties.class);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        builder.registerTypeAdapter(Manager.class, new ManagerGsonSerializer());
        builder.registerTypeAdapter(Customer.class, new CustomerGsonSerializer());
        Gson gson = builder.create();

        Warehouse warehouse = customHib.getWarehouseByCredentials(properties.getProperty("title"), properties.getProperty("address"));

        return warehouse != null ? gson.toJson(warehouse) : "";
    }

    @RequestMapping(value = "/Warehouse/getAllWarehouses", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getAllWarehouses() {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Warehouse.class, new WarehouseGsonSerializer());
        Gson gson = builder.create();

        return gson.toJson(customHib.getAllRecords(Warehouse.class));

    }
}

