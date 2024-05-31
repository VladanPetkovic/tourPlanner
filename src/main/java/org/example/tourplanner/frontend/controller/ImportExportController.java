package org.example.tourplanner.frontend.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.example.tourplanner.Main;
import org.example.tourplanner.frontend.viewModel.ImportExportViewModel;

import java.io.File;

public class ImportExportController {
    private final ImportExportViewModel viewModel;
    public Label selectedFolderLabel;
    public Label selectedFileLabel;
    public Label folderNotSelectedLabel;
    public Label fileNotSelectedLabel;
    public Label successImportLabel;
    public Label successExportLabel;

    public ImportExportController(ImportExportViewModel importExportViewModel) {
        this.viewModel = importExportViewModel;
    }

    public void onImportBtnClick(ActionEvent actionEvent) {
        if (!checkInput(selectedFileLabel, fileNotSelectedLabel)) {
            fileNotSelectedLabel.setText("File not selected!");
            successImportLabel.setVisible(false);
            return;
        }

        boolean hasImported = this.viewModel.importTours(selectedFileLabel.getText());
        if (hasImported) {
            successImportLabel.setVisible(true);
            selectedFileLabel.setText("\"\"");
        } else {
            fileNotSelectedLabel.setText("An error has occurred!");
            fileNotSelectedLabel.setVisible(true);
            successImportLabel.setVisible(false);
        }
    }

    public void onExportBtnClick(ActionEvent actionEvent) {
        if (!checkInput(selectedFolderLabel, folderNotSelectedLabel)) {
            folderNotSelectedLabel.setText("Folder not selected!");
            successExportLabel.setVisible(false);
            return;
        }

        boolean hasExported = this.viewModel.exportTours(selectedFolderLabel.getText());
        if (hasExported) {
            successExportLabel.setVisible(true);
            selectedFolderLabel.setText("\"\"");
        } else {
            folderNotSelectedLabel.setText("An error has occurred!");
            folderNotSelectedLabel.setVisible(true);
            successExportLabel.setVisible(false);
        }
    }

    public void onSelectFileBtnClick(ActionEvent actionEvent) {
        chooseFile(selectedFileLabel);
    }

    public void onSelectFolderBtnClick(ActionEvent actionEvent) {
        chooseDirectory(selectedFolderLabel);
    }

    private void chooseFile(Label label_selectedFile) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a CSV File");

        // Set extension filter to only allow CSV files
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            label_selectedFile.setText(file.toString());
        }
    }

    private void chooseDirectory(Label label_selectedDirectory) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select Folder to Save File");
        if (!label_selectedDirectory.getText().equals("\"\"")) {
            chooser.setInitialDirectory(new File(label_selectedDirectory.getText()));
        }
        File directory = chooser.showDialog(Main.stage);
        if (directory != null) {
            label_selectedDirectory.setText(directory.toString());
        }
    }

    private boolean checkInput(Label labelToCheck, Label notSelectedLabel) {
        if (labelToCheck.getText().equals("\"\"")) {
            notSelectedLabel.setVisible(true);
            return false;
        }

        notSelectedLabel.setVisible(false);
        return true;
    }
}
