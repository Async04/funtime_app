package com.example.funtime_app.services;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class PasswordGenerator {

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "@$!%*?&";
    private static final String ALL_CHARACTERS = LOWERCASE + UPPERCASE + DIGITS + SPECIAL_CHARACTERS;
    private static final int PASSWORD_LENGTH = 12;

    private static final SecureRandom random = new SecureRandom();

    public String generateRandomPassword() {
        StringBuilder password = new StringBuilder();

        // Kamida bitta kichik harf qo'shish
        password.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));

        // Kamida bitta katta harf qo'shish
        password.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));

        // Kamida bitta raqam qo'shish
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));

        // Kamida bitta maxsus belgi qo'shish
        password.append(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));

        // Qolgan belgilarni tasodifiy qo'shish
        for (int i = 4; i < PASSWORD_LENGTH; i++) {
            password.append(ALL_CHARACTERS.charAt(random.nextInt(ALL_CHARACTERS.length())));
        }

        // Parolni aralashtirish
        return shuffleString(password.toString());
    }

    private String shuffleString(String input) {
        char[] characters = input.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            int randomIndex = random.nextInt(characters.length);
            char temp = characters[i];
            characters[i] = characters[randomIndex];
            characters[randomIndex] = temp;
        }
        return new String(characters);
    }

    public  void main(String[] args) {
        // Test uchun tasodifiy parol yaratish
        String randomPassword = generateRandomPassword();
        System.out.println("Tasodifiy kuchli parol: " + randomPassword);
    }
}
