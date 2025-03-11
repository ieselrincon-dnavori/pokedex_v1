package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PokemonAPI {
    private static final String API_URL = "https://pokeapi.co/api/v2/pokemon/";

    public static Pokemon getPokemon(int id) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + id))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);

        String name = jsonObject.get("name").getAsString();
        int number = jsonObject.get("id").getAsInt();
        String type = jsonObject.getAsJsonArray("types").get(0).getAsJsonObject().get("type").getAsJsonObject().get("name").getAsString();
        String imageUrl = jsonObject.getAsJsonObject("sprites").get("front_default").getAsString();

        JsonArray abilitiesArray = jsonObject.getAsJsonArray("abilities");
        String abilities = StreamSupport.stream(abilitiesArray.spliterator(), false)
                .map(element -> element.getAsJsonObject().getAsJsonObject("ability").get("name").getAsString())
                .collect(Collectors.joining(", "));

        String height = jsonObject.get("height").getAsString();
        String weight = jsonObject.get("weight").getAsString();

        // Ajustar el orden de los parámetros según el constructor de Pokemon
        return new Pokemon(name, number, type, abilities, height, weight, imageUrl);
    }
}

