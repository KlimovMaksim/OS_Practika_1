package ru.klimov.exercise.handlers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class AbstractHandler {
    final String FILE_ROOT;
    Path path;
    String fileExtension;

    public AbstractHandler(String FILE_ROOT,String fileExtension) {
        this.FILE_ROOT = FILE_ROOT;
        this.fileExtension = fileExtension;
    }

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

    protected abstract void write(Path path, String line);

    protected abstract void read(Path path);

    protected void setPath(String fileName) {
        path = Paths.get(FILE_ROOT, fileName + fileExtension);
    }

    private void delete(Path path) {
        try {
            Files.delete(path);
            System.out.println("Файл удален: " + path.getFileName());
        } catch (IOException e) {
            System.out.println("Ошибка удаления файла: " + e.getMessage());
        }
    }

    private void create(Path path) {
        try {
            Files.createFile(path);
            System.out.println("Файл создан: " + path.getFileName());
        }catch (IOException e) {
            System.out.println("Ошибка создания файла: " + e.getMessage());
        }
    }
}
