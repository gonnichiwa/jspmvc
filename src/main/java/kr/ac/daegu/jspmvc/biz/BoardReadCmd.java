package kr.ac.daegu.jspmvc.biz;

import kr.ac.daegu.jspmvc.model.BoardDAO;
import kr.ac.daegu.jspmvc.model.BoardDTO;
import kr.ac.daegu.jspmvc.model.CommentDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardReadCmd implements BoardCmd {
    @Override
    public boolean execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // parsing
        // 데이터의 형변환(참조형 -> 기본형)
        // java wrapper class
        int boardContentId = Integer.parseInt(request.getParameter("id"));
        // db에 접근해서 데이터 가져오는 인스턴스
        BoardDAO dao = new BoardDAO();

        // dao의 기능중 id값으로 한건의 데이터를 담는 객체 준비.
        BoardDTO boardData = new BoardDTO();
        // 댓글 데이터를 담는 컬렉션 리스트 객체 준비
        List<CommentDTO> commentList = new ArrayList<CommentDTO>();

        try {
            // 조회수 +1
            // boardRowPlusReadCount() : readCount 업데이트 해라, boardContentId : 어느 row의 id값인지?, 1 : 얼마나 업데이트 할건지?
            dao.boardRowPlusReadCount(boardContentId, 1);
            boardData = dao.getBoardData(boardContentId);
            // ? 번 글에 해당하는 댓글 데이터를 가져옴
            commentList = dao.getCommentList(boardContentId);
            System.out.println("commentListCount=" + commentList.size());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        // boardRead.jsp에 보여줄 데이터를 셋
        request.setAttribute("boardData", boardData);

        return true;
    }
}
