package com.mycompany.project.dao;

import com.mycompany.project.model.Result;
import com.mycompany.project.model.Video;
import com.mycompany.project.util.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResultDAO {

    public List<Result> getResultByDate(Date fromDate, Date toDate, int userId) {
        List<Result> list = new ArrayList<>();
        String sql = """
                SELECT r.id, r.date, r.description, r.image, r.Videoid
                FROM tblresult AS r
                JOIN tblvideo AS v ON r.Videoid = v.id
                WHERE v.Userid = ? AND r.date BETWEEN ? AND ?
                ORDER BY r.date DESC
                """;

        try (Connection con = DB.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setDate(2, fromDate);
            ps.setDate(3, toDate);

            try (ResultSet rs = ps.executeQuery()) {
                VideoDAO vdao = new VideoDAO();

                while (rs.next()) {
                    Result r = new Result();
                    r.setId(rs.getInt("id"));
                    r.setDate(rs.getDate("date"));
                    r.setDescription(rs.getString("description"));
                    r.setImage(rs.getString("image"));

                    // Lấy video tương ứng theo ID
                    Video video = vdao.getVideoById(rs.getInt("Videoid"));
                    r.setVideo(video);

                    list.add(r);
                }
            }

            System.out.println("Found " + list.size() + " results.");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
