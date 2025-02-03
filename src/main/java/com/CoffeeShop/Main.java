package com.CoffeeShop;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * This Program will compute all orders of customer and print a receipt
 *finally this program will save the result into a text file.
 */

public class Main {
    private static String[] coffeeMenu = {"Espresso", "Latte", "Cappuccino", "Mocha"};
    private static double[] price = {50.0, 70.0, 65.0, 80.0};
    private static final double VAT_RATE = 0.12;
    private static int[] orderCount;

    public static void main(String[] args) {
        Scanner nk = new Scanner(System.in);
        //Display the menu and price
        System.out.print("""
                ---Coffee Menu---       ---Price---
                1.Espresso                50.0Php
                2.Latte                   70.0Php      
                3.Cappuccino              65.0Php
                4.Mocha                   80.0Php
                0. Finish Order
                """);
        orderCount = new int[coffeeMenu.length];

        //loop to collect orders
        while (true) {
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

                orderCount[choice - 1] += quantity;

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

//compute the subtotal
        double subtotal = 0;
        for (int i = 0; i < coffeeMenu.length; i++) {
            subtotal += orderCount[i] * price[i];
        }
        double vat = subtotal * 0.12;
        double grandTotal = subtotal + vat;
        receipt(coffeeMenu, price, orderCount);

        System.out.printf("Subtotal: %.2f\n", subtotal);
        System.out.printf("VAT (12%%): %.2f\n", vat);
        System.out.printf("Grand Total: %.2f\n", grandTotal);
        System.out.println("-------------------------------");

        saveReceipt(orderCount, subtotal, vat, grandTotal);

        System.out.println("Receipt saved to CoffeeReceipt.txt");
    }
//Receipt format
    public static void receipt(String[] name,double[] price, int [] quantities){
        String header = String.format("""
                ========== Receipt ==========
                """);
        String divider = String.format("""
                =============================
                """);
        System.out.print(header);
        for (int i = 0; i < coffeeMenu.length; i++) {
            if (quantities[i] > 0) {
                System.out.printf("%d x %s @ %.2f each = %.2f\n", quantities[i], name[i], price[i], quantities[i] * price[i]);
            }
        }
        System.out.println(divider);
    }
    public static void saveReceipt(int[] quantities, double subtotal, double vat, double grandTotal) {
        try (FileWriter writer = new FileWriter("CoffeeReceipt.txt")) {
            writer.write("=============== Receipt ===============\n");
            for (int i = 0; i < coffeeMenu.length; i++) {
                if (quantities[i] > 0) {
                    writer.write(String.format("%d x %s @ %.2f each = %.2f\n", quantities[i], coffeeMenu[i], price[i], quantities[i] * price[i]));
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