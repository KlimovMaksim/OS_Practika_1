package ru.klimov.exercise.handlers;

import java.io.*;
import java.net.URI;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipHandler extends AbstractHandler{
    public ZipHandler() {
        super("src/main/resources/zip", ".zip");
    }

    @Override
    public void createFile(String fileName) {
        setPath(fileName);
        create(fileName);
    }

    @Override
    public void writeToFile(String zipFileName, String fileToAdd) {
        setPath(zipFileName);
        write(path, fileToAdd);
    }

    @Override
    public void readFileInConsole(String zipFileName) {
        setPath(zipFileName);
        try {
            unzipFile(path);
            path = Paths.get(FILE_ROOT, zipFileName);
            read(path);
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла в консоль: " + e.getMessage());;
        }
    }

    @Override
    protected void read(Path path) {
        File dir = new File(path.toString());
        System.out.print("Содержимое архива ");
        for ( File file : dir.listFiles() ){
            if ( file.isFile() )
                System.out.println(file.getName());
        }
    }

    @Override
    protected void write(Path path, String fileToAdd) {
        Map<String, String> env = new HashMap<>();
        env.put("create", "true");
        URI uri = URI.create("jar:" + path.toUri());
        try (FileSystem fs = FileSystems.newFileSystem(uri, env))
        {
            Path nf = fs.getPath(fileToAdd);
            Files.write(nf, Files.readAllBytes(Paths.get(path.getParent() + "/" + fileToAdd)), StandardOpenOption.CREATE);
            System.out.println("Файл " + fileToAdd + " добавлен к архиву " + path.getFileName());
        } catch (IOException e) {
            System.out.println("Ошибка добавления файла в архив: " + e.getMessage());;
        }
    }

    private void create(String fileName) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(path.toFile());
             ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream))
        {
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOutputStream.putNextEntry(zipEntry);
            System.out.println("Пустой zip-архив создан: " + fileName + fileExtension);
        } catch (IOException e) {
            System.out.println("Ошибка создания пустого zip-архива: " + e.getMessage());
        }
    }

    private void unzipFile(Path zipFilePath) throws IOException {
        String fileZip = zipFilePath.toString();
        File destDir = new File(fileZip.substring(0, fileZip.indexOf(".")));

        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = newFile(destDir, zipEntry);
            if (zipEntry.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {
                // fix for Windows-created archives
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }

                // write file content
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            zipEntry = zis.getNextEntry();
        }

        zis.closeEntry();
        zis.close();

        System.out.println("Zip-архив " + zipFilePath.getFileName() + " был разархивирован в " + destDir);
    }

    private File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }
}
