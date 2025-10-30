package com.exemplo.demo3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CharacterDAO {

    Connection conn = SQLConnection.connectDB();
    PreparedStatement p = null;

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS characters (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "name TEXT NOT NULL UNIQUE," +
                            "xp INTEGER NOT NULL," +
                            "skin TEXT NOT NULL)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCharacter(Character character) {
        String sql = "INSERT INTO characters(name, xp, skin) VALUES(?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            //pstmt.setInt(2, character.getId());
            pstmt.setString(1, character.getName());
            pstmt.setInt(2, character.getXpPoints());
            pstmt.setString(3, character.getSkinPath());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCharacter(int id) {
        String sql = "DELETE FROM characters WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCharacter(int id, Character updatedCharacter) {
        String sql = "UPDATE characters SET name = ?, xp = ?, skin = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, updatedCharacter.getName());
            pstmt.setInt(2, updatedCharacter.getXpPoints());
            pstmt.setString(3, updatedCharacter.getSkinPath());
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Character getCharacter(int id) {
        String sql = "SELECT id, name, xp, skin FROM characters WHERE id = ?";
        Character character = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                character =  new Character (
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("skin"),
                        rs.getInt("xp")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return character;
    }

    public List<Character> getAllCharacters() {
        String sql = "SELECT id, name, skin, xp FROM characters";
        List<Character> charactersList = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                charactersList.add(new Character(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("skin"),
                        rs.getInt("xp")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return charactersList;
    }

    public List<String> getAllCharacterNames() {
        String sql = "SELECT name FROM characters";
        List<String> namesList = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                namesList.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return namesList;
    }

    public int getCharacterID(String name) {
        String sql = "SELECT id FROM characters WHERE name = ?";
        int id = -1;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

}
