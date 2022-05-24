/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.pm.report.generator.csvutils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import pl.pm.report.generator.model.Store;

public class CsvReader {
    public static List<Store> readFromCsvFile(String filePath) throws IOException {
        return Files.lines(Paths.get(filePath))
                .map(l -> {
                    String[] a = l.split(",");
                    return new Store(a[0], Integer.valueOf(a[1]));
                })
                .collect(Collectors.toList());
    }
}
