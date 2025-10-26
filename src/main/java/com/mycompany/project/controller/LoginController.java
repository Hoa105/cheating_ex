package com.mycompany.project.controller;

import com.mycompany.project.dao.UserDAO;
import com.mycompany.project.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblStatus;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    void onLogin(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        User user = userDAO.login(username, password);

        if (user != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
                Parent root = loader.load();

// Truyền user đăng nhập qua
                HomeController controller = loader.getController();
                controller.setUser(user);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Trang chủ - " + username);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            lblStatus.setText("Sai tên đăng nhập hoặc mật khẩu!");
        }
    }

    @FXML
    void onGoRegister(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Đăng ký");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
