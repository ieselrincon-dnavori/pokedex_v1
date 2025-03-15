package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PokedexController {

    @FXML
    private TableView<Pokemon> pokemonTable;
    @FXML
    private TableColumn<Pokemon, String> nameColumn;
    @FXML
    private TableColumn<Pokemon, Integer> numberColumn;
    @FXML
    private TableColumn<Pokemon, String> typeColumn;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private ImageView pokemonImage;
    @FXML
    private TextField searchByName;
    @FXML
    private TextField searchByType;
    @FXML
    private TextField trainerName;
    private ObservableList<Pokemon> pokemonData = FXCollections.observableArrayList();

  @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        numberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty().asObject());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        pokemonTable.setItems(pokemonData);
        pokemonTable.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
        pokemonTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPokemonDetails(newValue));

      loadPokemonData();

        searchByName.textProperty().addListener((observable, oldValue, newValue) -> filterPokemonList());
        searchByType.textProperty().addListener((observable, oldValue, newValue) -> filterPokemonList());
    }


    private void showPokemonDetails(Pokemon pokemon) {
        if (pokemon != null) {
            descriptionArea.setText("Nombre: " + pokemon.getName()
                    + "\nTipo: " + pokemon.getType()
                    + "\nHabilidades: " + pokemon.getAbilities()
                    + "\nAltura: " + (Double.parseDouble(pokemon.getHeight()) / 10) + " m"
                    + "\nPeso: " + (Double.parseDouble(pokemon.getWeight()) / 10) + " kg");
            pokemonImage.setImage(new Image(pokemon.getImageUrl()));
        } else {
            descriptionArea.setText("");
            pokemonImage.setImage(null);
        }
    }

    @FXML
    private ProgressBar progressBar;

    private void loadPokemonData() {
        progressBar.setVisible(true);
        progressBar.setProgress(0);
        int totalPokemon = 10;

        CompletableFuture.runAsync(() -> {
            for (int i = 1; i <= totalPokemon; i++) {
                try {
                    Pokemon pokemon = PokemonAPI.getPokemon(i);
                    int progress = i; // Captura el índice actual
                    javafx.application.Platform.runLater(() -> {
                        pokemonData.add(pokemon);
                        progressBar.setProgress((double) progress / totalPokemon); // Actualiza el progreso
                    });
                } catch (IOException | InterruptedException e) {
                    ErrorManager.handleException(e, "Error en la API", true);
                }
            }
            javafx.application.Platform.runLater(() -> progressBar.setVisible(false)); // Oculta al terminar
        });
    }


    @FXML
    private void savePokemonToPDF() {
        List<Pokemon> selectedPokemons = pokemonTable.getSelectionModel().getSelectedItems();

        if (!selectedPokemons.isEmpty()) {
            String filePath = Paths.get(System.getProperty("user.home"), "pokemon_details.pdf").toString();
            String trainer = trainerName.getText();
            PokemonPDFGenerator.generatePokemonPDF(selectedPokemons, filePath, trainer);
        } else {
            System.out.println("No hay ningún Pokémon seleccionado.");
        }
    }


    private void filterPokemonList() {
        String nameFilter = searchByName.getText().toLowerCase();
        String typeFilter = searchByType.getText().toLowerCase();

        ObservableList<Pokemon> filteredList = pokemonData.stream()
                .filter(pokemon -> pokemon.getName().toLowerCase().contains(nameFilter))
                .filter(pokemon -> pokemon.getType().toLowerCase().contains(typeFilter))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        pokemonTable.setItems(filteredList);
    }
}


