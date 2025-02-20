package com.CoffeeShop;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

    /**
     * This Program will compute all orders of customers and print a receipt.
     * Finally, this program will save the result into a text file.
     */
    public class CoffeeShopArray {

        private static String[] coffeeMenu = {"Espresso", "Latte", "Cappuccino", "Mocha"};
        private static double[] price = {50.0, 70.0, 65.0, 80.0};
        private static final double VAT_RATE = 0.12;
        private static String[][] coffeeData;

        public static void main(String[] args) {
            Scanner nk = new Scanner(System.in);

            coffeeData = new String[coffeeMenu.length][3];

            // Fill the array with initial data
            for (int i = 0; i < coffeeMenu.length; i++) {
                coffeeData[i][0] = coffeeMenu[i];
                coffeeData[i][1] = String.valueOf(price[i]);
                coffeeData[i][2] = "0";
            }

            // Loop for ordering coffee
            while (true) {
                System.out.print("""
                    ---Coffee Menu---       ---Price---
                    1.Espresso                50.0Php
                    2.Latte                   70.0Php      
                    3.Cappuccino              65.0Php
                    4.Mocha                   80.0Php
                    0. Finish Order
                    """);

                System.out.print("Choose your coffee (1-" + coffeeMenu.length + ", or 0 to finish): ");
                int choice;

                try {
                    choice = Integer.parseInt(nk.nextLine());
                    if (choice == 0) {
                        break;
                    }
                    if (choice < 1 || choice > coffeeMenu.length) {
                        System.out.println("Invalid choice. Please try again.");
                        continue;
                    }

                    System.out.print("Enter quantity: ");
                    int quantity = Integer.parseInt(nk.nextLine());
                    if (quantity < 1) {
                        System.out.println("Quantity must be at least 1. Please try again.");
                        continue;
                    }

                    // Update the quantity in the 2D array
                    int previousQuantity = Integer.parseInt(coffeeData[choice - 1][2]);
                    coffeeData[choice - 1][2] = String.valueOf(previousQuantity + quantity);

                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }

            double subtotal = 0;
            // Loop through the coffeeData array to calculate the subtotal
            for (int i = 0; i < coffeeData.length; i++) {
                int quantity = Integer.parseInt(coffeeData[i][2]);
                double coffeePrice = Double.parseDouble(coffeeData[i][1]);
                subtotal += quantity * coffeePrice;
            }
            double vat = subtotal * VAT_RATE;
            double grandTotal = subtotal + vat;
            receipt(coffeeData);

            System.out.printf("Subtotal: %.2f\n", subtotal);
            System.out.printf("VAT (12%%): %.2f\n", vat);
            System.out.printf("Grand Total: %.2f\n", grandTotal);
            System.out.println("-------------------------------");

            saveReceipt(coffeeData, subtotal, vat, grandTotal);

            System.out.println("Receipt saved to CoffeeReceipt.txt");
        }

        public static void receipt(String[][] coffeeData) {
            String header = String.format("========== Receipt ==========");
            String divider = String.format("=============================");
            System.out.println(header);
            for (int i = 0; i < coffeeData.length; i++) {
                int quantity = Integer.parseInt(coffeeData[i][2]);
                if (quantity > 0) {
                    double coffeePrice = Double.parseDouble(coffeeData[i][1]);
                    System.out.printf("%d x %s @ %.2f each = %.2f\n", quantity, coffeeData[i][0], coffeePrice, quantity * coffeePrice);
                }
            }
            System.out.println(divider);
        }

        public static void saveReceipt(String[][] coffeeData, double subtotal, double vat, double grandTotal) {
            try (FileWriter writer = new FileWriter("CoffeeReceipt.txt")) {
                writer.write("=============== Receipt ===============\n");
                for (int i = 0; i < coffeeData.length; i++) {
                    int quantity = Integer.parseInt(coffeeData[i][2]);
                    if (quantity > 0) {
                        double coffeePrice = Double.parseDouble(coffeeData[i][1]);
                        writer.write(String.format("%d x %s @ %.2f each = %.2f\n", quantity, coffeeData[i][0], coffeePrice, quantity * coffeePrice));
                    }
                }
                writer.write("========================================\n");
                writer.write(String.format("Subtotal: %.2f\n", subtotal));
                writer.write(String.format("VAT (12%%): %.2f\n", vat));
                writer.write(String.format("Grand Total: %.2f\n", grandTotal));
                writer.write("-------------------------------\n");
            } catch (IOException e) {
                System.out.println("Error saving receipt: " + e.getMessage());
            }
        }
    }