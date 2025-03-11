package org.example;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class CheckBoxTableCell<S, T> extends TableCell<S, T> {
    private final CheckBox checkBox;

    public CheckBoxTableCell() {
        this.checkBox = new CheckBox();
        this.checkBox.setOnAction(event -> {
            if (isEditing()) {
                commitEdit((T) Boolean.valueOf(this.checkBox.isSelected()));
            }
        });
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
            setText(null);
        } else {
            if (item instanceof Boolean) {
                this.checkBox.setSelected((Boolean) item);
                setGraphic(this.checkBox);
                setText(null);
            }
        }
    }

    public static <S> Callback<TableColumn<S, Boolean>, TableCell<S, Boolean>> forTableColumn(TableColumn<S, Boolean> column) {
        return tc -> new CheckBoxTableCell<>();
    }
}
