package pl.pm.report.generator.csvutils;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CsvWriterTest {
    @Test
    public void test_generate_success() {
        String report = CsvWriter.generate(1, 2, 3);

        assertNotNull(report);
        assertEquals(generateTestReport(1, 2, 3), report);
    }

    @Test
    public void test_write_success() throws IOException {
        String contentToWrite = generateTestReport(1, 2, 3).trim();
        Path resourceDirectory = Paths.get("src", "test", "resources", "temp", "test_write_success.csv");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        CsvWriter.write(contentToWrite, absolutePath);

        File file = new File(absolutePath);
        String fileContent = readFile(absolutePath).trim();

        assertNotNull(file);
        assertTrue(file.delete());
        assertEquals(contentToWrite, fileContent);
    }

    private String generateTestReport(int supply, int buy, int result) {
        StringBuilder sb = new StringBuilder();
        sb.append("Raport z dnia,").append(new SimpleDateFormat("dd-MM-yyyy").format(new Date())).append("\n")
                .append("supply,").append(supply).append("\n")
                .append("buy,").append(buy).append("\n")
                .append("result,").append(result).append("\n");
        return sb.toString();
    }

    private String readFile(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream
                     = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }
}