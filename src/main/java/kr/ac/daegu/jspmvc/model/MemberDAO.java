package kr.ac.daegu.jspmvc.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDAO {
    public MemberDTO getLoginData(String id) throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        pstmt = conn.prepareStatement("select * from member where id = ?");
        pstmt.setString(1, id);
        rs = pstmt.executeQuery();

        MemberDTO memberDTO = new MemberDTO();
        if(rs.next()){
            memberDTO.setId(rs.getString("id"));
            memberDTO.setPassword(rs.getString("password"));
            memberDTO.setSalt(rs.getString("salt"));
        }

        return memberDTO;
    }

    public void postLoginData(String id,
                              String password) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;

        pstmt = conn.prepareStatement("insert into member values (?, ?)");
        pstmt.setString(id);
        pstmt.set
    }
}
