package pl.pm.report.generator;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import pl.pm.report.generator.csvutils.CsvReader;
import pl.pm.report.generator.csvutils.CsvWriter;
import pl.pm.report.generator.db.service.StoreService;
import pl.pm.report.generator.model.Store;

public class Controller {

    private final List<File> files = new ArrayList<>();
    private static final FileChooser fileChooser = new FileChooser();
    private static final StoreService storeService = new StoreService();
    @FXML
    private Label filesList;
    @FXML
    private Label raport;
    @FXML
    private AnchorPane ap;
    @FXML
    private Button generateButton;

    public void initialize() {
        generateButton.disableProperty().bind(
                Bindings.isEmpty(filesList.textProperty())
        );
    }

    @FXML
    protected void addFile() {
        File file = fileChooser.showOpenDialog(ap.getScene().getWindow());
        if (file != null) {
            files.add(file);
            setFilesLabel();
        }
    }

    private void setFilesLabel() {
        if (!files.isEmpty()) {
            StringBuilder label = new StringBuilder();
            files.forEach(file -> label.append(file.getName()).append("\n"));
            filesList.setText(label.toString());
        }
    }

    @FXML
    protected void generateReport() throws SQLException {
        List<Store> storeList = files.stream().map(x -> {
            try {
                return CsvReader.readFromCsvFile(x.getPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).flatMap(Collection::stream).collect(Collectors.toList());
        storeService.persistAll(storeList);
        int buySum = storeService.selectSumOfOperationBuy();
        int supplySum = storeService.selectSumOfOperationSupply();
        raport.setText(CsvWriter.generate(supplySum, buySum, supplySum - buySum));
        files.clear();
        filesList.setText("");
    }

    @FXML
    protected void saveToFile() throws IOException {
        File file = fileChooser.showSaveDialog(ap.getScene().getWindow());
        if (file != null) {
            CsvWriter.write(raport.getText(), file.getAbsolutePath());
        }
    }
}
