package org.example;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import javafx.fxml.FXML;

import java.io.IOException;
import java.util.List;

public class PokemonPDFGenerator {

    public static void generatePokemonPDF(List<Pokemon> pokemons, String filePath, String trainerName) {
        if (pokemons == null || pokemons.isEmpty()) {
            System.out.println("La lista de Pokémon está vacía, no se generará el PDF.");
            return;
        }

        try {
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Añadir nombre del entrenador al PDF
            document.add(new Paragraph("Entrenador: " + trainerName + "\n\n"));

            for (Pokemon pokemon : pokemons) {
                document.add(new Paragraph("Nombre: " + pokemon.getName()));
                document.add(new Paragraph("Tipo: " + pokemon.getType()));
                document.add(new Paragraph("Habilidades: " + pokemon.getAbilities()));
                document.add(new Paragraph("Altura: " + (Double.parseDouble(pokemon.getHeight()) / 10) + " m"));
                document.add(new Paragraph("Peso: " + (Double.parseDouble(pokemon.getWeight()) / 10) + " kg"));

                try {
                    ImageData imageData = ImageDataFactory.create(pokemon.getImageUrl());
                    Image image = new Image(imageData);
                    document.add(image);
                } catch (IOException e) {
                    //ErrorManager.handleException(e, "/pika_erro.png");
                    ErrorManager.handleException(e, "https://acortar.link/59JXpK", true);
                }

                document.add(new Paragraph("\n")); // Añadir un espacio entre Pokémon
            }

            document.close();
            System.out.println("PDF generado correctamente en " + filePath);
        } catch (IOException e) {
           // ErrorManager.handleException(e, "/pika_erro.png");
            ErrorManager.handleException(e, "https://acortar.link/59JXpK", true);

        }
    }
}

