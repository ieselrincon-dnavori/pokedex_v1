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

        // Solicitud al endpoint principal del Pokémon
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + id))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonObject = new Gson().fromJson(response.body(), JsonObject.class);

        // Recuperar datos básicos del Pokémon,nombre,numero,typo,img miniatura
        String name = jsonObject.get("name").getAsString();
        int number = jsonObject.get("id").getAsInt();
        String type = jsonObject.getAsJsonArray("types").get(0).getAsJsonObject().get("type").getAsJsonObject().get("name").getAsString();
        String imageUrl = jsonObject.getAsJsonObject("sprites").get("front_default").getAsString();

        //Las habilidades.
        JsonArray abilitiesArray = jsonObject.getAsJsonArray("abilities"); 
        String abilities = StreamSupport.stream(abilitiesArray.spliterator(), false)
                .map(element -> element.getAsJsonObject().getAsJsonObject("ability").get("name").getAsString())
                .collect(Collectors.joining(", "));

        String height = jsonObject.get("height").getAsString();
        String weight = jsonObject.get("weight").getAsString();
        String baseExperience = jsonObject.get("base_experience").getAsString(); // Cambiado de 'pokemonObject' a 'jsonObject'

        // Solicitud al endpoint de especie para datos adicionales
        HttpRequest speciesRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://pokeapi.co/api/v2/pokemon-species/" + id))
                .build();
        HttpResponse<String> speciesResponse = client.send(speciesRequest, HttpResponse.BodyHandlers.ofString());
        JsonObject speciesObject = new Gson().fromJson(speciesResponse.body(), JsonObject.class);

        String habitat = speciesObject.get("habitat") != null
                ? speciesObject.getAsJsonObject("habitat").get("name").getAsString()
                : "Desconocido";
        String evolutionChainUrl = speciesObject.getAsJsonObject("evolution_chain").get("url").getAsString();

        // Devolver un nuevo Pokémon con todos los datos
        //quitar evolution chain, no funciona bien.
        return new Pokemon(name, number, type, abilities, height, weight, imageUrl, baseExperience, habitat, evolutionChainUrl);
    }

}

