package com.example.ollympiads;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private CheckBox first, second, third;

    @FXML
    private CheckBox p_1, p_2, p_3;

    @FXML
    private Button loadGiftsButton, calculateButton;

    @FXML
    private Label resultLabel;

    @FXML
    private CheckBox first_1, first_2, DA, NET;

    private final Map<String, Map<String, Integer>> giftsData = new HashMap<>();
    private final Map<String, CheckBox[]> companyGiftCheckBoxes = new HashMap<>();

    @FXML
    private void initialize() {
        // Загрузка данных о подарках
        loadGiftData("Google.txt", "Google");
        loadGiftData("Yandex.txt", "Yandex");
        loadGiftData("Innowise.txt", "Innowise");

        // Связывание компаний с фиксированными чекбоксами
        companyGiftCheckBoxes.put("Google", new CheckBox[]{p_1, p_2, p_3});
        companyGiftCheckBoxes.put("Yandex", new CheckBox[]{p_1, p_2, p_3});
        companyGiftCheckBoxes.put("Innowise", new CheckBox[]{p_1, p_2, p_3});

        resetGiftCheckBoxes(); //скрыть

        // Обработка нажатия кнопки "ПОДАРКИ"
        loadGiftsButton.setOnAction(event -> loadGiftOptions());

        // Обработка нажатия кнопки "Рассчитать сумму"
        calculateButton.setOnAction(event -> calculateTotalCost());
    }



    // Загрузка данных о подарках из файла
    private void loadGiftData(String fileName, String company) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            Map<String, Integer> companyGifts = new HashMap<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); // Формат: "Подарок,Цена"
                if (parts.length == 2) {
                    companyGifts.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
                }
            }
            giftsData.put(company, companyGifts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Отображение подарков для выбранной компании
    private void loadGiftOptions() {
        resetGiftCheckBoxes(); // Очистить чекбоксы

        if (first.isSelected()) loadGiftOptionsForCompany("Google");
        if (second.isSelected()) loadGiftOptionsForCompany("Yandex");
        if (third.isSelected()) loadGiftOptionsForCompany("Innowise");
    }

    // Очистка чекбоксов перед загрузкой новых данных
    private void resetGiftCheckBoxes() {
        p_1.setVisible(false);
        p_2.setVisible(false);
        p_3.setVisible(false);
        p_1.setText("");
        p_2.setText("");
        p_3.setText("");
    }

    // Загрузка подарков в фиксированные чекбоксы
    private void loadGiftOptionsForCompany(String company) {
        CheckBox[] checkBoxes = companyGiftCheckBoxes.get(company);
        Map<String, Integer> companyGifts = giftsData.get(company);

        int i = 0;
        for (String gift : companyGifts.keySet()) {
            if (i < checkBoxes.length) {
                checkBoxes[i].setText(gift);
                checkBoxes[i].setVisible(true);
                i++;
            }
        }
    }

    // Рассчёт общей суммы
    private void calculateTotalCost() {
        int totalCost = 0;

// Рассчёт стоимости подарков
        for (Map.Entry<String, CheckBox[]> entry : companyGiftCheckBoxes.entrySet()) {
            String company = entry.getKey();
            CheckBox[] checkBoxes = entry.getValue();


            Map<String, Integer> companyGifts = giftsData.get(company);
            if (companyGifts != null) {
                for (CheckBox giftCheckBox : checkBoxes) {
                    if (giftCheckBox.isVisible() && giftCheckBox.isSelected()) {
                        String giftName = giftCheckBox.getText();
                        if (companyGifts.containsKey(giftName)) {
                            totalCost += companyGifts.get(giftName);
                        }
                    }
                }
            }
        }

        // Учитываем стоимость концерта, только если выбрано "Да"
        if (first_1.isSelected()) {
            if (first.isSelected()) totalCost += 5000;
            if (second.isSelected()) totalCost += 4000;
            if (third.isSelected()) totalCost += 3000;
        }

        // Если выбрано "Нет" для концерта
        if (first_2.isSelected()) {
          //  welcomeText.setText("Концерт не добавлен в стоимость.");
            totalCost+=0;
        }

        // Применение скидки для постоянного клиента
        if (DA.isSelected()) {
            totalCost *= 0.9; // Скидка 10%
        }

        // Если выбрано "Нет" для постоянного клиента
        if (NET.isSelected()) {
            //welcomeText.setText("Без скидки постоянного клиента.");
            totalCost+=0;
        }

        // Установка итоговой суммы
        resultLabel.setText("Общая стоимость: " + totalCost + " руб.");
    }
}
