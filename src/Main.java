import java.util.*;

class Main {

        static Store store = new Store();
        static Scanner scanner = new Scanner(System.in);
        static Inventory inventory = new Inventory();

        static void main(String[] args) {

            Car car1 = new Car("Volkswagen", "Golf", 2025, "Turbo", 440000.00);
            inventory.addCarInventory(car1);
            Car car2 = new Car("Porsche", "911 GT3 RS", 2024, "NA", 2100000.00);
            inventory.addCarInventory(car2);
            Car car3 = new Car("BMW", "M3 Competition", 2025, "Twin-turbo", 1125000.00);
            inventory.addCarInventory(car3);
            Car car4 = new Car("Honda", "Civic Type R", 2023, "Turbo", 450000.00);
            inventory.addCarInventory(car4);

            int mainMenuIndex = -1;
            int inventoryManagerIndex = -1;
            int salesManagerIndex = -1;
            int salesCarSelectorIndex = -1;
            int financeMenuIndex = -1;

            double aliquotaImposto = 0.18;

            do {
                System.out.println("[  Dealer Management System  ]\n");
                System.out.println("1. Manage inventory (Inventory & sales)\n2. Finance (Customers & sales)\n0. Quit\n");

                try {
                    mainMenuIndex = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("\nError: Invalid input.");
                }

                scanner.nextLine();
                System.out.println();

                switch (mainMenuIndex) {
                    case 1:
                        do {
                            System.out.println("[  Inventory manager  ]\n");
                            System.out.println("1. Check inventory\n2. Make sale\n3. Add vehicle\n0. Main menu\n");

                            try {
                                inventoryManagerIndex = scanner.nextInt();
                            } catch (InputMismatchException e) {
                                System.out.println("\nError: Invalid input.");
                            }

                            scanner.nextLine();
                            System.out.println();

                            switch (inventoryManagerIndex) {
                                case 1:
                                    inventory.showInventory(Inventory.inventory);
                                    break;
                                case 2:
                                    do {
                                        System.out.println("[  Sales manager  ]\n");
                                        System.out.println("1. Start sale\n0. Back\n");

                                        try {
                                            salesManagerIndex = scanner.nextInt();
                                        } catch (InputMismatchException e) {
                                            System.out.println("\nError: Invalid input.");
                                        }

                                        scanner.nextLine();
                                        System.out.println();

                                        String salesConfirmSale = "";
                                        String salesBuyersName = "";
                                        String salesBuyersCpf = "";

                                        switch (salesManagerIndex) {
                                            case 1:
                                                System.out.println("Select the car by its index:\n");
                                                inventory.showInventory(Inventory.inventory);

                                                try {
                                                    salesCarSelectorIndex = scanner.nextInt();
                                                } catch (InputMismatchException e) {
                                                    System.out.println("\nError: Invalid input.");
                                                    break;
                                                }

                                                scanner.nextLine();
                                                System.out.println();
                                                String salesCarSelected = Inventory.inventory.get(salesCarSelectorIndex - 1).make + " " +  Inventory.inventory.get(salesCarSelectorIndex - 1).model + " " + Inventory.inventory.get(salesCarSelectorIndex - 1).year;
                                                System.out.println("Selected car: " + salesCarSelected);
                                                double price = Inventory.inventory.get(salesCarSelectorIndex - 1).price;
                                                System.out.println("Price (before taxes): R$ " + price);
                                                System.out.println("Price (after taxes: R$ " + (price + (price*aliquotaImposto)));
                                                System.out.println();

                                                System.out.print("Type in the customer's full name: ");

                                                try {
                                                    salesBuyersName = scanner.nextLine();
                                                } catch (NoSuchElementException e) {
                                                    System.out.println("Error: sale must be associated to a customer.");
                                                    break;
                                                }

                                                System.out.println();

                                                System.out.print("Type in the customer's CPF (no dots or dashes): ");

                                                try {
                                                    salesBuyersCpf = scanner.nextLine();
                                                } catch (NoSuchElementException e) {
                                                    System.out.println("Error: customer must have a CPF.");
                                                    break;
                                                }

                                                System.out.println();

                                                System.out.println(salesCarSelected);
                                                System.out.println("Total: R$ " + (price + (price * aliquotaImposto)));
                                                System.out.print("Confirm sale? (yes/no): ");

                                                try {
                                                    salesConfirmSale = scanner.nextLine();
                                                } catch (NoSuchElementException e) {
                                                    System.out.println("Error: must confirm sale.");
                                                }

                                                System.out.println();

                                                switch (salesConfirmSale) {
                                                    case "yes":

                                                        if(store.customers.isEmpty()) {

                                                            // System.out.println("\nCUSTOMER LIST IS EMPTY.\n");

                                                            Customer firstCustomer = new Customer(salesBuyersName, salesBuyersCpf);
                                                            store.addCustomer(firstCustomer);
                                                            firstCustomer.cars.add(Inventory.inventory.get(salesCarSelectorIndex - 1));
                                                            System.out.println("[  Sale confirmed  ]");
                                                            Date saleDate = new Date();
                                                            Sale sale = new Sale(salesBuyersName, salesBuyersCpf, Inventory.inventory.get(salesCarSelectorIndex - 1), saleDate);
                                                            inventory.removeCarInventory(Inventory.inventory.get(salesCarSelectorIndex - 1));
                                                            store.newSale(sale);
                                                            System.out.println("Customer: " + firstCustomer.name + "\nCar: " + salesCarSelected);
                                                            System.out.println("Total: R$ " + (price + (price * aliquotaImposto)));
                                                            System.out.println(saleDate + "\n");
                                                            break;
                                                        } else {
                                                            boolean clientExists = false;
                                                            Customer existingCustomer = null;
                                                            // System.out.println("\nCUSTOMER LIST IS NOT EMPTY. VERIFYING IF BUYER IS ON CUSTOMER LIST.\n");

                                                            for (Customer customer : store.customers) {
                                                                if (salesBuyersCpf.equals(customer.cpf)) {
                                                                    existingCustomer = customer;
                                                                    clientExists = true;
                                                                    break;
                                                                }
                                                            }

                                                            if (clientExists) {
                                                                // System.out.println("\BUYER IS ON CUSTOMER LIST.\n");

                                                                System.out.println("Customer already in database\n");
                                                                Date saleDate = new Date();
                                                                Sale sale = new Sale(salesBuyersName, salesBuyersCpf, Inventory.inventory.get(salesCarSelectorIndex - 1), saleDate);
                                                                store.newSale(sale);
                                                                existingCustomer.cars.add(Inventory.inventory.get(salesCarSelectorIndex - 1));
                                                                inventory.removeCarInventory(Inventory.inventory.get(salesCarSelectorIndex - 1));
                                                                System.out.println("[  Sale confirmed  ]");
                                                                System.out.println("Customer: " + existingCustomer.name + "\nCar: " + salesCarSelected);
                                                                System.out.println("Total: R$ " + (price + (price * aliquotaImposto)));
                                                                System.out.println(saleDate + "\n");
                                                                break;
                                                            } else {
                                                                // System.out.println("\nCUSTOMER LIST IS NOT EMPTY BUT BUYER IS NOT ON THE LIST.\n");

                                                                Customer newCustomer = new Customer(salesBuyersName, salesBuyersCpf);
                                                                store.addCustomer(newCustomer);
                                                                newCustomer.cars.add(Inventory.inventory.get(salesCarSelectorIndex - 1));
                                                                System.out.println("[  Sale confirmed  ]");
                                                                Date saleDate = new Date();
                                                                Sale sale = new Sale(salesBuyersName, salesBuyersCpf, Inventory.inventory.get(salesCarSelectorIndex - 1), saleDate);
                                                                inventory.removeCarInventory(Inventory.inventory.get(salesCarSelectorIndex - 1));
                                                                store.newSale(sale);
                                                                System.out.println("Customer: " + newCustomer.name + "\nCar: " + salesCarSelected);
                                                                System.out.println("Total: R$ " + (price + (price * aliquotaImposto)));
                                                                System.out.println(saleDate + "\n");
                                                                break;
                                                            }
                                                        }
                                                    case "no":
                                                        break;
                                                }
                                            case 0:
                                                salesManagerIndex = 0;
                                                break;
                                        }
                                    } while (salesManagerIndex != 0);
                                break;
                            case 3:
                                System.out.println("[  Inventory manager  ]\n");

                                System.out.print("Make: ");
                                String make = scanner.nextLine();
                                System.out.println();

                                System.out.print("Model: ");
                                String model = scanner.nextLine();
                                System.out.println();

                                System.out.print("Year: ");
                                int year = scanner.nextInt();
                                scanner.nextLine();
                                System.out.println();

                                System.out.print("Engine aspiration: ");
                                String engineAspiration = scanner.nextLine();
                                System.out.println();

                                System.out.print("Price (before taxes): ");
                                double price = scanner.nextDouble();
                                scanner.nextLine();
                                System.out.println();

                                Car car = new Car(make, model, year, engineAspiration, price);

                                inventory.addCarInventory(car);
                                inventory.showInventory(Inventory.inventory);

                                break;
                            case 0:
                                break;
                            }
                        } while(inventoryManagerIndex != 0);
                        break;

                    case 2:
                        do {
                            System.out.println("[  Finances  ]\n");
                            System.out.println("1. View all sales\n2. View customer database\n0. Back");
                            System.out.println();

                            try {
                                financeMenuIndex = scanner.nextInt();
                            } catch (InputMismatchException e) {
                                System.out.println("\nError: Invalid input. You must select the index of the action you want to perform.");
                            }
                            System.out.println();
                            scanner.nextLine();

                            switch(financeMenuIndex) {
                                case 1:
                                    if(store.sales.isEmpty()) {
                                        System.out.println("\nNo sales recorded.\n");
                                        break;
                                    } else {
                                        System.out.println("[  All sales  ]\n");

                                        double totalSales = 0;

                                        for(Sale sales : store.sales) {
                                            System.out.println("Customer: " + sales.buyersName + ", CPF: " + sales.buyersCpf);
                                            System.out.println("Car: " + sales.car.make + " " + sales.car.model + " " + sales.car.year + " " + "Price: " + sales.car.price);
                                            System.out.println("Date: " + sales.date);
                                            System.out.println();
                                            totalSales = totalSales + (sales.car.price + (sales.car.price*aliquotaImposto));
                                        }

                                        System.out.println("Total in sales: R$ " + totalSales + "\n");

                                        break;
                                    }

                                case 2:
                                    System.out.println("[  Customer database  ]\n");

                                    String customersFirstName = "";
                                    String customersSecondName = "";
                                    String customersCpf = "";
                                    int secondNameStarts = -1;

                                    for(Customer customer : store.customers) {
                                        for(int i = 0; i < customer.name.length(); i++) {
                                            if(customer.name.substring(i, i + 1).equals(" ")) {
                                                secondNameStarts = i;
                                                break;
                                            } else {
                                                customersFirstName = customersFirstName + customer.name.charAt(i);
                                            }
                                        }
                                        customersSecondName = customer.name.substring(secondNameStarts + 1, customer.name.length());

                                        // 000.000.000-00
                                        for(int i = 0; i < customer.cpf.length(); i++) {
                                            if(i == 3 || i == 6) {
                                                customersCpf = customersCpf + ".";
                                            }
                                            if(i == 9) {
                                                customersCpf = customersCpf + "-";
                                            }
                                            customersCpf = customersCpf + customer.cpf.charAt(i);
                                        }

                                        System.out.println("First name: " + customersFirstName + "\nSecond name: " + customersSecondName + "\nCPF: " + customersCpf);
                                        System.out.println("Cars bought: " + customer.cars.size());
                                        customersFirstName = "";
                                        customersSecondName = "";
                                        customersCpf = "";
                                        System.out.println();
                                    }

                                    break;
                                case 0:
                                    break;
                            }
                        } while (financeMenuIndex != 0);
                        break;

                    case 0:
                        break;
                }
            } while (mainMenuIndex != 0);
        }
}
