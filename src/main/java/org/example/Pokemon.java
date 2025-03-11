package org.example;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;

public class Pokemon {
    private final StringProperty name;
    private final IntegerProperty number;
    private final StringProperty type;
    private final StringProperty abilities;
    private final StringProperty height;
    private final StringProperty weight;
    private final StringProperty imageUrl;
    private final BooleanProperty selected;

    public Pokemon(String name, int number, String type, String abilities, String height, String weight, String imageUrl) {
        this.name = new SimpleStringProperty(name);
        this.number = new SimpleIntegerProperty(number);
        this.type = new SimpleStringProperty(type);
        this.abilities = new SimpleStringProperty(abilities);
        this.height = new SimpleStringProperty(height);
        this.weight = new SimpleStringProperty(weight);
        this.imageUrl = new SimpleStringProperty(imageUrl);
        this.selected = new SimpleBooleanProperty(false);
    }

    public String getName() {
        return name.get();
    }

    public Integer getNumber() {
        return number.get();
    }

    public String getType() {
        return type.get();
    }

    public String getAbilities() {
        return abilities.get();
    }

    public String getHeight() {
        return height.get();
    }

    public String getWeight() {
        return weight.get();
    }

    public String getImageUrl() {
        return imageUrl.get();
    }

    public Boolean getSelected() {
        return selected.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setNumber(Integer number) {
        this.number.set(number);
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public void setAbilities(String abilities) {
        this.abilities.set(abilities);
    }

    public void setHeight(String height) {
        this.height.set(height);
    }

    public void setWeight(String weight) {
        this.weight.set(weight);
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl.set(imageUrl);
    }

    public void setSelected(Boolean selected) {
        this.selected.set(selected);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public IntegerProperty numberProperty() {
        return number;
    }

    public StringProperty typeProperty() {
        return type;
    }

    public StringProperty abilitiesProperty() {
        return abilities;
    }

    public StringProperty heightProperty() {
        return height;
    }

    public StringProperty weightProperty() {
        return weight;
    }

    public StringProperty imageUrlProperty() {
        return imageUrl;
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public boolean isSelected() {
        return selected.get();
    }
}

