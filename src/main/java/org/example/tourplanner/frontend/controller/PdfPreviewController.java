package org.example.tourplanner.frontend.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import org.example.tourplanner.Main;
import org.example.tourplanner.frontend.viewModel.PdfPreviewViewModel;
import org.example.tourplanner.frontend.viewModel.TourViewModel;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static org.example.tourplanner.frontend.controller.SwitchScene.switchScene;

public class PdfPreviewController implements Initializable {
    public Label selectedFolderLabel;
    public Label FolderNotSelectedLabel;
    public VBox vbox_previewPane;
    private final PdfPreviewViewModel viewModel;
    private final TourViewModel tourViewModel;


    public PdfPreviewController(PdfPreviewViewModel pdfPreviewViewModel, TourViewModel tourViewModel) {
        this.viewModel = pdfPreviewViewModel;
        this.tourViewModel = tourViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.viewModel.initializeReport(this.tourViewModel.isReportType(), this.tourViewModel.getSelectedTour());
    }

    public void onShowPreviewBtnClick(ActionEvent actionEvent) throws IOException {
        showPreview(this.viewModel.getPreviewImages());
    }

    /**
     * Showing the preview of the test with the selected parameters.
     * @param images ArrayList of java.lang.image or so --> every image is one page
     */
    private void showPreview(ArrayList<Image> images) {
        // removing previous images
        this.vbox_previewPane.getChildren().clear();

        double targetWidth = this.vbox_previewPane.getWidth();

        for (int i = 0; i < images.size(); i++) {
            ImageView imageView = new ImageView(images.get(i));

            // Set the fit width and preserve the aspect ratio
            imageView.setFitWidth(targetWidth);
            imageView.setPreserveRatio(true);

            this.vbox_previewPane.getChildren().add(imageView);

            // adding a black line between pages
            if (i < images.size() - 1) {
                this.vbox_previewPane.getChildren().add(createBlackSeparator());
            }
        }
    }

    private Region createBlackSeparator() {
        // creating a black separator
        Region separator = new Region();
        separator.setMinHeight(5);
        separator.setBackground(javafx.scene.layout.Background.EMPTY);
        separator.setStyle("-fx-background-color: black;");
        return separator;
    }

    public void onGoBackBtnClick(ActionEvent actionEvent) throws IOException {
        this.tourViewModel.setSelectedTour(null);
        switchScene("sites/tours.fxml");
    }

    public void onSelectFolderBtnClick(ActionEvent actionEvent) {
        chooseDirectory(selectedFolderLabel);
    }

    /**
     * This function opens a new Dialog to get the destination folder for saving the export-file.
     * If a folder was chosen previously, then it will set the previous choice as default.
     * @param label_selectedDirectory Label, which shows the selected directory.
     */
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

    public void onExportBtnClick(ActionEvent actionEvent) throws IOException {
        if (!checkInput()) {
            return;
        }

        this.viewModel.exportReport(selectedFolderLabel.getText());
        this.tourViewModel.setSelectedTour(null);
        switchScene("sites/tours.fxml");
    }

    private boolean checkInput() {
        if (selectedFolderLabel.getText().equals("\"\"")) {
            FolderNotSelectedLabel.setVisible(true);
            return false;
        }

        FolderNotSelectedLabel.setVisible(false);
        return true;
    }
}
