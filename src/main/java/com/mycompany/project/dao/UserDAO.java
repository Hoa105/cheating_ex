package com.mycompany.project.dao;

import com.mycompany.project.model.User;
import com.mycompany.project.util.DB;

import java.sql.*;
import java.util.Optional;

public class UserDAO {

    public boolean register(User u) {
        String sql = "INSERT INTO tbluser(username, password, birthday, address, phone, name, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DB.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setDate(3, u.getBirthday());
            ps.setString(4, u.getAddress());
            ps.setString(5, u.getPhone());
            ps.setString(6, u.getName());
            ps.setString(7, u.getEmail());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public User login(String username, String password) {
        String sql = "SELECT * FROM tbluser WHERE username=? AND password=?";
        try (Connection con = DB.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User u = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getDate("birthday"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("name"),
                        rs.getString("email")
                );
                return u;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
