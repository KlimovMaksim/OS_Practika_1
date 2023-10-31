package ru.klimov.exercise;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;


public class DiskInfo {
    /*public static void main(String[] args) {
        Iterable<FileStore> fileStores = FileSystems.getDefault().getFileStores();

        for (FileStore fileStore : fileStores) {
            System.out.println("Диск: " + fileStore);
            try {
                //System.out.println("Метка тома: " + new File(fileStores.toString() + "\\\\").getName());
                //FileSystemView
            } catch (Exception e) {
                System.err.println("Ошибка при получении метки тома: " + e.getMessage());
            }
            try {
                System.out.println("Размер: " + fileStore.getTotalSpace() / 1073741824 + " ГБ");
            } catch (Exception e) {
                System.err.println("Ошибка при получении размера: " + e.getMessage());
            }
            try {
                System.out.println("Тип файловой системы: " + fileStore.type());
            } catch (Exception e) {
                System.err.println("Ошибка при получении типа файловой системы: " + e.getMessage());
            }
            System.out.println();
        }
    }*/

    final long BYTES_PER_GIGABYTE = 1024L * 1024L * 1024L;
    final Iterable<FileStore> FILE_STORES = FileSystems.getDefault().getFileStores();


    public void printDiskInfoInConsole(){
        for (FileStore fileStore : FILE_STORES) {
            printDiskNameInConsole(fileStore);
            printVolumeLabelInConsole(fileStore);
            printTotalSpaceInConsole(fileStore);
            printFileSystemTypeInConsole(fileStore);
            System.out.println();
        }
    }

    private void printFileSystemTypeInConsole(FileStore fileStore) {
        try {
            System.out.println("Тип файловой системы: " + fileStore.type());
        } catch (Exception e) {
            System.err.println("Ошибка при получении типа файловой системы: " + e.getMessage());
        }
    }

    private void printTotalSpaceInConsole(FileStore fileStore) {
        try {
            System.out.println("Размер: " + fileStore.getTotalSpace() / BYTES_PER_GIGABYTE + " ГБ");
        } catch (IOException e) {
            System.out.println("Ошибка при получении размера: " + e.getMessage());
        }
    }

    private void printVolumeLabelInConsole(FileStore fileStore) {
        try {
            System.out.println("Метка тома: " + getVolumeLabel(fileStore));
        } catch (Exception e){
            System.out.println("Ошибка при получении метки тома: " + e.getMessage());
        }
    }

    private void printDiskNameInConsole(FileStore fileStore) {
        try {
            System.out.println("Диск: " + fileStore);
        } catch (Exception e) {
            System.out.println("Ошибка при получении имени диска: " + e.getMessage());
        }
    }

    private String getVolumeLabel(FileStore fileStore){
        String fileStoreInString = fileStore.toString();
        int indexOfColon = fileStoreInString.indexOf(":");
        return fileStoreInString.substring(indexOfColon - 1, indexOfColon);
    }
}
