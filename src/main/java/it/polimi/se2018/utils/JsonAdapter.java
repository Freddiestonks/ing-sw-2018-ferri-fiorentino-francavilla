package it.polimi.se2018.utils;

import com.google.gson.*;

import java.lang.reflect.Type;

public class JsonAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {

    @Override
    public final JsonElement serialize(T object, Type interfaceType, JsonSerializationContext context) {
        JsonObject wrapper = new JsonObject();
        wrapper.addProperty("type", object.getClass().getName());
        wrapper.add("data", (new Gson()).toJsonTree(object));
        return wrapper;
    }

    @Override
    public final T deserialize(JsonElement elem, Type interfaceType, JsonDeserializationContext context) {
        T object;
        JsonObject member = (JsonObject) elem;
        JsonElement typeString = member.get("type");
        if(typeString != null) {
            JsonElement data = member.get("data");
            Type actualType;
            try {
                actualType = Class.forName(typeString.getAsString());
            } catch (ClassNotFoundException e) {
                throw new JsonParseException(e);
            }
            object = context.deserialize(data, actualType);
        }
        else {
            object = (new Gson()).fromJson(member, interfaceType);
        }
        return object;
    }
}
