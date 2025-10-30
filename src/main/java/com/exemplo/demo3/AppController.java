package com.exemplo.demo3;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    private Button botaoAdicionar;

    @FXML
    private Button botaoEditar;

    @FXML
    private Button botaoRemover;

    @FXML
    private Button botaoRecarregar;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label nameLabel;

    @FXML
    private ImageView fullBodyImg;

    @FXML
    private Label xpPoints;

    @FXML
    private Label characterLevel;

    @FXML
    private ProgressBar xpBar;

    private CharacterDAO characterDAO = new CharacterDAO();

    private List<Character> characters = new ArrayList<>();

    private MyListener listener;

    public static Character selectedChar = null;

    private List<Character> getCharacters() {
        characters = characterDAO.getAllCharacters();
        return characters;
    }

    private void selectCharacter(Character character) {
        if(character!=null){
            selectedChar = character;
            nameLabel.setText(selectedChar.getName());
            File imageFile = new File(selectedChar.getSkinPath());
            if (!imageFile.exists()) {
                System.out.println("Imagem não encontrada");
                imageFile = new File("src\\main\\resources\\com\\exemplo\\demo3\\catchException.png");
            }
            try {
                FileInputStream fileInputStream = new FileInputStream(imageFile);
                Image characterImage = new Image(fileInputStream);
                fullBodyImg.setImage(characterImage);
                xpPoints.setText(String.format("%d", selectedChar.getXpPoints()));
                characterLevel.setText(String.format("%d", selectedChar.getLevel()));
                xpBar.setProgress((double) (selectedChar.getXpPoints() % 100) / 100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void load() {
        if (!characters.isEmpty()) {
            characters.clear();
            gridPane.getChildren().clear();
        }
        characters.addAll(getCharacters());
        if (!characters.isEmpty()) {
            if(selectedChar==null){
                selectCharacter(characters.getFirst());
            }else{
                selectCharacter(selectedChar);
            }
            listener = new MyListener() {
                @Override
                public void onClickListener(Character character) {
                    selectCharacter(character);
                }
            };
        }
        int column = 0;
        int row = 0;
        try {
            for (Character character : characters) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("character_item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                CharacterItemController itemController = fxmlLoader.getController();
                itemController.setCharacterData(character, listener);
                if (column == 4) {
                    column = 0;
                    row++;
                }
                gridPane.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        characterDAO.createTable();
        load();
    }

    @FXML
    public void deleteCharacter() throws IOException{
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("deleteCharacter.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Deletar Personagem");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setAlwaysOnTop(true);
            stage.initStyle(StageStyle.UTILITY);
            //stage.show();
            stage.showAndWait();
            selectedChar = null;
            load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addCharacter() throws IOException{
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("addCharacter.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Adicionar Personagem");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setAlwaysOnTop(true);
            stage.initStyle(StageStyle.UTILITY);
            //stage.setBehavior(blablabla) --> quando a janela for fechada, chamar load()
            //stage.show();
            stage.showAndWait();
            load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void updateCharacter() throws IOException{
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("updateCharacter.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Editar Personagem (Deixe o campo em branco para não editar)");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setAlwaysOnTop(true);
            stage.initStyle(StageStyle.UTILITY);
            //stage.show();
            stage.showAndWait();
            if (selectedChar != null) {
                selectedChar = characterDAO.getCharacter(selectedChar.getId());
            }
            load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}