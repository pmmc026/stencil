package com.exemplo.demo3;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ActionController {
    
    @FXML
    private TextField nameTxtField;

    @FXML
    private File imageFile;

    @FXML
    private Label imagePath;

    @FXML
    private Button selectImage;

    @FXML
    private TextField xpTxtField;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button updateButton;

    @FXML
    private ChoiceBox<String> choiceBox = new ChoiceBox<>();

    private CharacterDAO db = new CharacterDAO();
    private String name, skinPath;
    private int xp;
    private JFrame jf = new JFrame();

    public void initialize() {
        choiceBox.getItems().addAll(db.getAllCharacterNames());
        if (AppController.selectedChar != null) {
            choiceBox.setValue(AppController.selectedChar.getName());
        }
    }

    public void addCharacter(ActionEvent event){
        if (nameTxtField.getText().isEmpty() || xpTxtField.getText().isEmpty() || imageFile == null) {
            jf.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(jf, "Preencha todos os campos.", "ERRO", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                name = nameTxtField.getText();
                skinPath = imageFile.getPath();
                xp = Integer.parseInt(xpTxtField.getText());

                if(name!=null && skinPath!=null && xp>=0){
                    Character c = new Character(name, skinPath, xp);
                    db.addCharacter(c);
                    c.setId(db.getCharacterID(name));
                    Stage stage = (Stage) addButton.getScene().getWindow();
                    stage.close();
                    //JOptionPane.showMessageDialog(jf, "Alterações salvas com sucesso. Recarregue para vizualizar as alterações.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch(NumberFormatException e){
                jf.setAlwaysOnTop(true);
                JOptionPane.showMessageDialog(jf, "Favor digitar apenas números no campo XP.", "Erro de Entrada", JOptionPane.WARNING_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void selectImage (ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escolha uma imagem");
        fileChooser.setInitialDirectory(new File("C:\\"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".png", "*.png"));
        File selectedFile = fileChooser.showOpenDialog(selectImage.getScene().getWindow());
        if (selectedFile != null) {
            imageFile = selectedFile;
            imagePath.setText(imageFile.getName());
        }
    }

    public void updateCharacter(ActionEvent event){
        String selectedCharacter = choiceBox.getValue();
        if (selectedCharacter == null) {
            jf.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(jf, "Selecione um personagem para editar.", "ERRO", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                int charId = db.getCharacterID(selectedCharacter);
                Character c = new Character(charId, null, null, 0);
                if (nameTxtField.getText().isEmpty()) {
                    c.setName(db.getCharacter(charId).getName());
                } else {
                    c.setName(nameTxtField.getText());
                }
                if (imageFile == null) {
                    c.setSkinPath(db.getCharacter(charId).getSkinPath());
                } else {
                    c.setSkinPath(imageFile.getPath());
                }
                if (!xpTxtField.getText().isEmpty()) {
                    c.setXpPoints(db.getCharacter(charId).getXpPoints() + Integer.parseInt(xpTxtField.getText()));
                } else {
                    c.setXpPoints(db.getCharacter(charId).getXpPoints());
                }
                db.updateCharacter(charId, c);
                Stage stage = (Stage) updateButton.getScene().getWindow();
                stage.close();
                //JOptionPane.showMessageDialog(jf, "Alterações salvas com sucesso. Recarregue para visualizar as alterações.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                jf.setAlwaysOnTop(true);
                JOptionPane.showMessageDialog(jf, "Favor digitar apenas números no campo XP.", "Erro de Entrada", JOptionPane.WARNING_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteCharacter(ActionEvent event){
        String selectedCharacter = choiceBox.getValue();
        if (selectedCharacter == null) {
            jf.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(jf, "Selecione um personagem para remover.", "ERRO", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                int charId = db.getCharacterID(selectedCharacter);
                if(db.getCharacter(charId)!=null){
                    db.deleteCharacter(charId);
                    if(db.getCharacter(charId)==null){
                        Stage stage = (Stage) deleteButton.getScene().getWindow();
                        stage.close();
                        //JOptionPane.showMessageDialog(jf, "Alterações salvas com sucesso. Recarregue para visualizar as alterações.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    jf.setAlwaysOnTop(true);
                    JOptionPane.showMessageDialog(jf, "Personagem não encontrado.", "ERRO", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
