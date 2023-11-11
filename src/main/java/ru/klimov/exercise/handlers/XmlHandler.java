package ru.klimov.exercise.handlers;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ru.klimov.exercise.Person;

import java.io.IOException;
import java.nio.file.Path;

public class XmlHandler extends AbstractHandler{
    XmlMapper mapper = new XmlMapper();

    public XmlHandler() {
        super("src/main/resources/xml", ".xml");
    }

    @Override
    protected void write(Path path, String line) {
        try {
            Person person = new Person(line);
            mapper.writeValue(
                    path.toFile(),
                    person
            );
            System.out.println("В файл добавлен объект " + person.toString());
        } catch (Exception e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());
        }
    }

    @Override
    protected void read(Path path) {
        try {
            Person person = mapper.readValue(
                    path.toFile(),
                    Person.class
            );
            System.out.println("Содержимое файла " + path.getFileName() + ":");
            System.out.println(person);
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла");
        }
    }
}
