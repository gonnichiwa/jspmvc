package kr.ac.daegu.jspmvc.biz;

import kr.ac.daegu.jspmvc.model.BoardDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class BoardDeleteCmd implements BoardCmd {
    @Override
    public boolean execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 정수의 모양을 한 String : "0", "112334"
        // request.getParameter("boardDataId")로 들어오는 값에 문자열이 섞여 있을 경우
        // NullPointerException 발생
        // 좀 더 방어적인 코드 : NPE 발생 가능성 차단
        int id = Integer.parseInt(request.getParameter("boardDataId"));

        // DB 접속해서
        // 글 id 에 해당하는 row 삭제
        BoardDAO dao = new BoardDAO();
        try {
            dao.deleteBoardData(id);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
}
