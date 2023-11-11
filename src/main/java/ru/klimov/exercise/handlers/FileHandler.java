package ru.klimov.exercise.handlers;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class FileHandler extends AbstractHandler{
    public FileHandler() {
        super("src/main/resources/text", ".txt");
    }

    @Override
    protected void write(Path path, String line) {
        try {
            Files.write(path, line.getBytes(), StandardOpenOption.APPEND);
            System.out.println("В файл добавлена строка: " + line);
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());;
        }
    }

    @Override
    protected void read(Path path) {
        try {
            byte[] bytes = Files.readAllBytes(path);
            System.out.println("Содержимое файла " + path.getFileName() + ":");
            System.out.println(new String(bytes, StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла");
        }
    }
}
