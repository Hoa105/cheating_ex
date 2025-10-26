package com.mycompany.project.controller;

import com.mycompany.project.dao.ResultDAO;
import com.mycompany.project.model.Result;
import com.mycompany.project.model.User;
import com.mycompany.project.app.App;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.*;
import javafx.stage.Stage;

public class HistoryController implements Initializable {

    @FXML
    private TableView<Result> resultTable;

    @FXML
    private TableColumn<Result, String> colImage;

    @FXML
    private TableColumn<Result, String> colDescription;

    @FXML
    private TableColumn<Result, String> colDate;


    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;

    private User currentUser;
    private final ResultDAO resultDAO = new ResultDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colImage.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getImage()));
        colDescription.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDescription()));
        colDate.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDate().toString()));
        
    }

    public void setUser(User user) {
        this.currentUser = user;
    }

    @FXML
    private void onFilterHistory() {
        LocalDate fromLocal = fromDatePicker.getValue();
        LocalDate toLocal = toDatePicker.getValue();

        if (fromLocal == null || toLocal == null) {
            showAlert("Vui lòng chọn đủ cả hai ngày.");
            return;
        }

        Date fromDate = Date.valueOf(fromLocal);
        Date toDate = Date.valueOf(toLocal);

        List<Result> results = resultDAO.getResultByDate(fromDate, toDate, currentUser.getId());
        resultTable.setItems(FXCollections.observableArrayList(results));

        if (results.isEmpty()) {
            showAlert("Không có kết quả nào trong khoảng thời gian này.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void onBackHome() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/home.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) resultTable.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
