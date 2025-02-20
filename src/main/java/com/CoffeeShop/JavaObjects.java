package com.CoffeeShop;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

    /**
     * This Program will compute all orders of customer and print a receipt
     * finally, this program will save the result into a text file.
     */
    public class JavaObjects {

        static class Coffee {
            String name;
            double price;

            Coffee(String name, double price) {
                this.name = name;
                this.price = price;
            }
        }

        static class CoffeeOrder {
            Coffee coffee;
            int quantity;

            CoffeeOrder(Coffee coffee, int quantity) {
                this.coffee = coffee;
                this.quantity = quantity;
            }

            double getTotalPrice() {
                return coffee.price * quantity;
            }
        }

        private static Coffee[] coffeeMenu = {
                new Coffee("Espresso", 50.0),
                new Coffee("Latte", 70.0),
                new Coffee("Cappuccino", 65.0),
                new Coffee("Mocha", 80.0)
        };

        private static final double VAT_RATE = 0.12;

        public static void main(String[] args) {
            Scanner nk = new Scanner(System.in);
            // Display the menu and price
            System.out.print("""
                ---Coffee Menu---       ---Price---
                1. Espresso                50.0Php
                2. Latte                   70.0Php      
                3. Cappuccino              65.0Php
                4. Mocha                   80.0Php
                0. Finish Order
                """);

            CoffeeOrder[] orders = new CoffeeOrder[coffeeMenu.length];
            int orderIndex = 0;

            // Loop to orders
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

                    // Create CoffeeOrder and store it
                    orders[orderIndex++] = new CoffeeOrder(coffeeMenu[choice - 1], quantity);

                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }

            // Compute the subtotal, VAT, and grand total
            double subtotal = 0;
            for (int i = 0; i < orderIndex; i++) {
                subtotal += orders[i].getTotalPrice();
            }

            double vat = subtotal * VAT_RATE;
            double grandTotal = subtotal + vat;

            // Print the receipt
            receipt(orders, orderIndex);

            System.out.printf("Subtotal: %.2f\n", subtotal);
            System.out.printf("VAT (12%%): %.2f\n", vat);
            System.out.printf("Grand Total: %.2f\n", grandTotal);
            System.out.println("-------------------------------");

            // Save the receipt to a file
            saveReceipt(orders, orderIndex, subtotal, vat, grandTotal);

            System.out.println("Receipt saved to CoffeeReceipt.txt");
        }

        // Receipt format
        public static void receipt(CoffeeOrder[] orders, int orderCount) {
            String header = String.format("========== Receipt ==========\n");
            String divider = String.format("============================\n");
            System.out.print(header);
            for (int i = 0; i < orderCount; i++) {
                CoffeeOrder order = orders[i];
                System.out.printf("%d x %s @ %.2f each = %.2f\n", order.quantity, order.coffee.name, order.coffee.price, order.getTotalPrice());
            }
            System.out.println(divider);
        }

        public static void saveReceipt(CoffeeOrder[] orders, int orderCount, double subtotal, double vat, double grandTotal) {
            try (FileWriter writer = new FileWriter("CoffeeReceipt.txt")) {
                writer.write("=============== Receipt ===============\n");
                for (int i = 0; i < orderCount; i++) {
                    CoffeeOrder order = orders[i];
                    writer.write(String.format("%d x %s @ %.2f each = %.2f\n", order.quantity, order.coffee.name, order.coffee.price, order.getTotalPrice()));
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

