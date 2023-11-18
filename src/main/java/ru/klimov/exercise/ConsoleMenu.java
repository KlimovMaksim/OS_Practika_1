package ru.klimov.exercise;

import ru.klimov.exercise.handlers.*;

import java.util.Scanner;

public class ConsoleMenu {
    AbstractHandler handler;
    Scanner scanner;

    public ConsoleMenu() {
        scanner = new Scanner(System.in);
    }

    public void mainLoop(){
        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Вывести информацию о дисках");
            System.out.println("2. Работа с файлами");
            System.out.println("3. Работа с Json");
            System.out.println("4. Работа с XML");
            System.out.println("5. Работа с Zip-архивами");
            System.out.println("0. Выход");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.println();
                    DiskInfo diskInfo = new DiskInfo();
                    diskInfo.printDiskInfoInConsole();
                    System.out.println();
                    break;
                case "2":
                    workWithHandlers("файл");
                    break;
                case "3":
                    workWithHandlers("Json");
                    break;
                case "4":
                    workWithHandlers("XML");
                    break;
                case "5":
                    workWithHandlers("zip-архив");
                    break;
                case "0":
                    System.out.println("Выход из программы");
                    return;
                default:
                    System.out.println("Неверный выбор, попробуйте еще раз");
            }
        }
    }

    private void workWithHandlers(String name){
        chooseHandler(name);
        String fileName;
        String inputData;
        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Создать " + name);
            System.out.println("2. Записать в " + name);
            System.out.println("3. Прочитать " + name);
            System.out.println("4. Удалить " + name);
            System.out.println("0. Выход");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Введите название:");
                    fileName = scanner.nextLine();
                    handler.createFile(fileName);
                    break;
                case "2":
                    System.out.println("Введите название:");
                    fileName = scanner.nextLine();
                    inputDataDescription(name);
                    inputData = scanner.nextLine();
                    handler.writeToFile(fileName, inputData);
                    break;
                case "3":
                    System.out.println("Введите название:");
                    fileName = scanner.nextLine();
                    handler.readFileInConsole(fileName);
                    break;
                case "4":
                    System.out.println("Введите название:");
                    fileName = scanner.nextLine();
                    handler.deleteFile(fileName);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Неверный выбор, попробуйте еще раз");
            }
        }
    }

    private void inputDataDescription(String name) {
        switch (name){
            case "файл":
                System.out.println("Введите строку, которую хотите добавить в файл: ");
                break;
            case "Json":
            case "XML":
                System.out.println("Введите данные для объекта Person через пробел (имя фамилия возраст): ");
                break;
            case "zip-архив":
                System.out.println("Введите название файла с расширением, который нужно добавить к архиву (file.txt): ");
                break;
        }
    }

    private void chooseHandler(String name) {
        switch (name){
            case "файл":
                handler = new FileHandler();
                break;
            case "Json":
                handler = new JsonHandler();
                break;
            case "XML":
                handler = new XmlHandler();
                break;
            case "zip-архив":
                handler = new ZipHandler();
                break;
        }
    }
}
