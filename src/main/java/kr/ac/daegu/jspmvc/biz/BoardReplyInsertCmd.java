package kr.ac.daegu.jspmvc.biz;

import kr.ac.daegu.jspmvc.model.BoardDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

// 답글의 작성
public class BoardReplyInsertCmd implements BoardCmd {
    @Override
    public boolean execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // enduser로부터 입력받은 데이터
        int newId;
        String subject = request.getParameter("subject");
        String author = request.getParameter("author");
        String content = request.getParameter("content");
        String password = request.getParameter("password");

        int replyRootId = Integer.parseInt(request.getParameter("replyRootId")); // 답글들의 root원글
        int depth = Integer.parseInt(request.getParameter("depth"));             // 답글의 대상 글에대한 depth
        int orderNum = Integer.parseInt(request.getParameter("orderNum"));       // 답글의 대상 글에 대한 orderNum

        // enduser로부터 입력받은 데이터 잘 들어왔는지 확인 log
        System.out.println("subject=" + subject);
        System.out.println("author=" + author);
        System.out.println("content=" + content);
        System.out.println("password=" + password);
        System.out.println("replyRootId=" + replyRootId);
        System.out.println("depth=" + depth);
        System.out.println("orderNum=" + orderNum);


        // db에 접근해서 데이터 가져오는 인스턴스
        BoardDAO dao = new BoardDAO();

        try {
            // board 테이블에 들어갈 id값을 가져오기 : board.id중에서 가장 높은 id값 + 1
            newId = dao.getBoardNewId();
            /* depth와 orderNum을 정하는 로직 */
            int minOrderNum = dao.getMinOrderNum(replyRootId, depth, orderNum);
            System.out.println("minOrderNum==" + minOrderNum);

            if(minOrderNum == 0) {
                orderNum = dao.getReplyOrderNum(replyRootId);
                depth = depth + 1;
                dao.insertReplyContent(newId, subject, author, content, password, replyRootId, depth, orderNum);
            } else {

            }


            // dao 기능 호출해서 enduser가 입력한 데이터와 replyRootId, depth, orderNum insert
//            dao.insertReplyContent(newId, subject, author, content, password, replyRootId, depth, orderNum);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

}
