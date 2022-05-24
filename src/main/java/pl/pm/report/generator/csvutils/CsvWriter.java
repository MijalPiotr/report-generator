/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.pm.report.generator.csvutils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CsvWriter {
    public static void write(final String raport, final String path) throws IOException {
            FileWriter fw = new FileWriter(path, true);
            BufferedWriter bw = new BufferedWriter(fw);
        try (PrintWriter pw = new PrintWriter(bw)) {
            pw.println(raport);
            pw.flush();
        }
    }

    public static String generate(int supply, int buy, int result){
        Date date = new Date();
        StringBuilder sb = new StringBuilder();
        sb.append("Raport z dnia,").append(new SimpleDateFormat("dd-MM-yyyy").format(date)).append("\n")
                .append("supply,").append(supply).append("\n")
                .append("buy,").append(buy).append("\n")
                .append("result,").append(result).append("\n");
        return sb.toString();
    }
}

