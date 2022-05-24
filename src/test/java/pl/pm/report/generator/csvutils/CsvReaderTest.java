package pl.pm.report.generator.csvutils;

import org.junit.jupiter.api.Test;
import pl.pm.report.generator.model.Store;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvReaderTest {
    @Test
    public void readFromCsvFile_emptyFile() throws IOException {
        Path resourceDirectory = Paths.get("src", "test", "resources", "testDataEmpty.csv");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        List<Store> stores = CsvReader.readFromCsvFile(absolutePath);
        assertNotNull(stores);
        assertTrue(stores.isEmpty());
    }
    @Test
    public void readFromCsvFile_corruptedFile() {
        Path resourceDirectory = Paths.get("src", "test", "resources", "testDataCorrupted.csv");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> CsvReader.readFromCsvFile(absolutePath));;
    }

    @Test
    public void readFromCsvFile_notExist() {
        Path resourceDirectory = Paths.get("src", "test", "resources", "testData2.csv");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        assertThrows(NoSuchFileException.class, () -> CsvReader.readFromCsvFile(absolutePath));
    }
}