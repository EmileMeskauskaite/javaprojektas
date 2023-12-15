package com.kursinis.prif4kursinis.webControllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kursinis.prif4kursinis.hibernateControllers.CustomHib;
import com.kursinis.prif4kursinis.model.Customer;
import com.kursinis.prif4kursinis.model.Manager;
import com.kursinis.prif4kursinis.model.User;
import com.kursinis.prif4kursinis.webControllers.serializers.CustomerGsonSerializer;
import com.kursinis.prif4kursinis.webControllers.serializers.LocalDateGsonSerializer;
import com.kursinis.prif4kursinis.webControllers.serializers.ManagerGsonSerializer;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.Properties;

@Controller
public class UserWeb {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("coursework-shop");
    CustomHib customHib = new CustomHib(entityManagerFactory);

    @RequestMapping(value = "/getUserById/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getUserById(@PathVariable(name = "id") int id){

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        builder.registerTypeAdapter(Manager.class, new ManagerGsonSerializer());
        builder.registerTypeAdapter(Customer.class, new CustomerGsonSerializer());
        //builder.registerTypeAdapter(User.class, new UserGsonSerializer());
        Gson gson = builder.create();


        User user = customHib.getEntityById(User.class, id);

        return user != null ? gson.toJson(user) : "";


        //return customHib.getEntityById(User.class, id).toString();
    }

    @RequestMapping(value = "/user/deleteUser/0", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String deleteUser(@RequestBody Map<String, Integer> payload) {
        int id = payload.get("id");
        customHib.deleteEntityById(User.class, id);
        return "User deleted";
    }

    @PostMapping(value = "/user/getUserByCredentials", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getUserByCredentials(@RequestBody String data) {
        System.out.println(data);
        Gson parser = new Gson();
        Properties properties = parser.fromJson(data, Properties.class);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        builder.registerTypeAdapter(Manager.class, new ManagerGsonSerializer());
        builder.registerTypeAdapter(Customer.class, new CustomerGsonSerializer());
        Gson gson = builder.create();

        User user = customHib.getUserByCredentials(properties.getProperty("login"), properties.getProperty("psw"));

        return user != null ? gson.toJson(user) : "";
    }


    @RequestMapping(value = "/user/createUser", method = RequestMethod.POST)

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String createUser(@RequestBody String data){
        System.out.println(data);
        Gson parser = new Gson();
        Properties properties = parser.fromJson(data, Properties.class);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        builder.registerTypeAdapter(Manager.class, new ManagerGsonSerializer());
        builder.registerTypeAdapter(Customer.class, new CustomerGsonSerializer());
        Gson gson = builder.create();

        User user = customHib.createUser(
                properties.getProperty("login"),
                properties.getProperty("password"),
                properties.getProperty("name"),
                properties.getProperty("surname")

        );

        return user != null ? gson.toJson(user) : "";
    }
    @RequestMapping(value = "/user/updateUser", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String updateUser(@RequestBody String data) {
        try {
            Gson parser = new Gson();
            Properties properties = parser.fromJson(data, Properties.class);

            // Input validation
            String userIdString = properties.getProperty("id");
            if (userIdString == null) {
                return "Error: User ID is required for update.";
            }

            int userId;
            try {
                userId = Integer.parseInt(userIdString);
            } catch (NumberFormatException e) {
                return "Error: Invalid user ID format. Please provide a valid integer for the user ID.";
            }

            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
            builder.registerTypeAdapter(Manager.class, new ManagerGsonSerializer());
            builder.registerTypeAdapter(Customer.class, new CustomerGsonSerializer());
            Gson gson = builder.create();

            User user = customHib.updateUser(
                    properties.getProperty("login"),
                    properties.getProperty("password"),
                    properties.getProperty("name"),
                    properties.getProperty("surname"),
                    userId
            );

            if (user != null) {
                return gson.toJson(user);
            } else {
                return "Error: User update failed.";
            }
        } catch (Exception e) {
            return "Error: Internal server error.";
        }
    }
    @RequestMapping(value="/user/getAllUsers", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getAllUsers(){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        builder.registerTypeAdapter(Manager.class, new ManagerGsonSerializer());
        builder.registerTypeAdapter(Customer.class, new CustomerGsonSerializer());
        Gson gson = builder.create();

        return gson.toJson(customHib.getAllRecords(User.class));
    }
    @RequestMapping(value="/user/updateUser1", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String updateUser(@RequestBody User user){
        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(Manager.class, new ManagerGsonSerializer());
        builder.registerTypeAdapter(Customer.class, new CustomerGsonSerializer());
        Gson gson = builder.create();

        return gson.toJson(customHib.updateEntity(user));

    }

}
