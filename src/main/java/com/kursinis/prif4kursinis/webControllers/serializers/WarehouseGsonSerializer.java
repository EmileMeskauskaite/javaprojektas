package com.kursinis.prif4kursinis.webControllers.serializers;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.kursinis.prif4kursinis.model.Manager;
import com.kursinis.prif4kursinis.model.Warehouse;

import java.lang.reflect.Type;

public class WarehouseGsonSerializer implements JsonSerializer<Warehouse> {
    @Override
    public JsonElement serialize(Warehouse warehouse, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("id", warehouse.getId());
            jsonObject.addProperty("title", warehouse.getTitle());
            jsonObject.addProperty("address", warehouse.getAddress());

            return jsonObject;
    }
}

