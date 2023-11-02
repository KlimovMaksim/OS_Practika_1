package ru.klimov.exercise;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class FileHandler {
    final String FILE_ROOT = "src/main/resources/text";
    Path path;

    public void createFile(String fileName){
        setPath(fileName);
        create(path);
    }

    public void writeToFile(String fileName, String line){
        setPath(fileName);
        write(path, line);
    }

    public void readFileInConsole(String fileName){
        setPath(fileName);
        read(path);
    }

    public void deleteFile(String fileName){
        setPath(fileName);
        delete(path);
    }

    private void setPath(String fileName) {
        path = Paths.get(FILE_ROOT, fileName);
    }

    private void delete(Path path) {
        try {
            Files.delete(path);
            System.out.println("Файл удален: " + path.getFileName());
        } catch (IOException e) {
            System.out.println("Ошибка удаления файла: " + e.getMessage());
        }
    }

    private void read(Path path) {
        try {
            byte[] bytes = Files.readAllBytes(path);
            System.out.println("Содержимое файла " + path.getFileName() + ":");
            System.out.println(new String(bytes, StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла");
        }
    }

    private void write(Path path, String line) {
        try {
            Files.write(path, line.getBytes(), StandardOpenOption.APPEND);
            System.out.println("В файл добавлена строка: " + line);
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());;
        }
    }

    private void create(Path path) {
        try {
            Files.createFile(path);
            System.out.println("Файл создан: " + path.getFileName());
        }catch (IOException e) {
            System.out.println("Ошибка создания файла: " + e.getMessage());;
        }
    }
}
