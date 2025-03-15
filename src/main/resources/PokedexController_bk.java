package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class PokedexController_bk {

    @FXML
    private TableView<Pokemon> pokemonTable;
    @FXML
    private TableColumn<Pokemon, Boolean> selectColumn;
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

        // Configurar la columna de selección con checkboxes
        selectColumn.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        selectColumn.setCellFactory(CheckBoxTableCell.forTableColumn(selectColumn));
        pokemonTable.setItems(pokemonData);
        pokemonTable.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
        pokemonTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPokemonDetails(newValue));
      pokemonTable.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);

      pokemonData.forEach(pokemon ->
              pokemon.selectedProperty().addListener((obs, oldValue, newValue) ->
                      System.out.println(pokemon.getName() + " seleccionado: " + newValue)
              )
      );


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

    private void loadPokemonData() {
        CompletableFuture.runAsync(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    Pokemon pokemon = PokemonAPI.getPokemon(i);
                    javafx.application.Platform.runLater(() -> pokemonData.add(pokemon));
                } catch (IOException | InterruptedException e) {
                    ErrorManager.handleException(e, "https://acortar.link/59JXpK", true);
                }
            }
        });
    }

    @FXML
    private void savePokemonToPDF() {
        List<Pokemon> selectedPokemons = pokemonData.stream()
                .filter(Pokemon::isSelected)
                .collect(Collectors.toList());

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


