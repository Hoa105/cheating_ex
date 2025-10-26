package com.mycompany.project.controller;

import com.mycompany.project.dao.UserDAO;
import com.mycompany.project.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.Date;

public class RegisterController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmPassword;
    @FXML private TextField txtName;
    @FXML private TextField txtEmail;
    @FXML private TextField txtPhone;
    @FXML private TextField txtAddress;
    @FXML private DatePicker dpBirthday;
    @FXML private Label lblStatus;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    void onRegister(ActionEvent event) {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String address = txtAddress.getText().trim();
        Date birthday = (dpBirthday.getValue() != null)
                ? Date.valueOf(dpBirthday.getValue())
                : null;

        // Kiểm tra đầu vào
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            lblStatus.setText("Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            lblStatus.setText("Mật khẩu xác nhận không khớp!");
            return;
        }

        // Tạo đối tượng User
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);
        user.setBirthday(birthday);

        // Gọi DAO để đăng ký
        boolean success = userDAO.register(user);
        if (success) {
            lblStatus.setText("Đăng ký thành công! Vui lòng đăng nhập.");
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Đăng nhập");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            lblStatus.setText("Đăng ký thất bại! Vui lòng thử lại.");
        }
    }

    @FXML
    void onGoLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Đăng nhập");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
