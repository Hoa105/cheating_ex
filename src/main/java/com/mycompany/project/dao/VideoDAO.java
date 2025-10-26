package com.mycompany.project.dao;

import com.mycompany.project.model.Video;
import com.mycompany.project.model.User;
import com.mycompany.project.util.DB;

import java.sql.*;

public class VideoDAO {

    /**
     * Lấy thông tin video theo ID
     * @param id mã video cần tìm
     * @return đối tượng Video nếu tìm thấy, ngược lại null
     */
    public Video getVideoById(int id) {
        String sql = "SELECT * FROM tblvideo WHERE id = ?";

        try (Connection con = DB.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Video v = new Video();

                    return v;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
