package com.exemplo.demo3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Objects;

public class CharacterItemController {

    @FXML
    private ImageView img;

    @FXML
    private Label nameLabel;

    @FXML
    private void click(MouseEvent mouseEvent) {
        listener.onClickListener(character);
    }

    private Character character;

    private MyListener listener;

    public void setCharacterData(Character character, MyListener listener) {
        this.character = character;
        this.listener = listener;
        nameLabel.setText(character.getName());
        File imageFile = new File(character.getSkinPath());
        try {
            FileInputStream fileInputStream = new FileInputStream(imageFile);
            Image characterImage = new Image(fileInputStream);
            PixelReader reader = characterImage.getPixelReader();
            WritableImage headImage = new WritableImage(reader, 32, 0, 64, 64);
            img.setImage(headImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
