package com.mycompany.project.controller;

import com.mycompany.project.app.App;
import com.mycompany.project.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomeController {

    private User currentUser; // Lưu thông tin người dùng đăng nhập hiện tại

    public void setUser(User user) {
        this.currentUser = user;
    }

    @FXML
    void onLogout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Đăng nhập hệ thống");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onViewHistory(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/history.fxml"));
            Parent root = loader.load();

            // Lấy controller của history.fxml
            HistoryController controller = loader.getController();

            // Truyền user hiện tại
            controller.setUser(currentUser);

            // Chuyển scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Lịch sử hoạt động - " + currentUser.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
