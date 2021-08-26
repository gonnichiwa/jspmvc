package kr.ac.daegu.jspmvc.biz;

import kr.ac.daegu.jspmvc.model.BoardDAO;
import kr.ac.daegu.jspmvc.model.BoardDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class BoardReadCmd implements BoardCmd {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // db에 접근해서 데이터 가져오는 인스턴스
        BoardDAO dao = new BoardDAO();

        // dao의 기능중 id값으로 한건의 데이터를 담는 객체 준비.
        BoardDTO boardData = new BoardDTO();
        // parsing
        // 데이터의 형변환(참조형 -> 기본형)
        // java wrapper class
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            boardData = dao.getBoardData(id);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        // boardRead.jsp에 보여줄 데이터를 셋
        request.setAttribute("boardData", boardData);

    }
}
