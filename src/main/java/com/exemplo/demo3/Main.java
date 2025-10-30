package com.exemplo.demo3;

public class Main {
    public static void main(String[] args) {
        
        CharacterDAO characterDAO = new CharacterDAO();
        characterDAO.createTable();

        for (String name : characterDAO.getAllCharacterNames()) {
            System.out.println(name);
            System.out.println();
        }

    }
}