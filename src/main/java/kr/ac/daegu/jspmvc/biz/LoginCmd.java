package kr.ac.daegu.jspmvc.biz;

import kr.ac.daegu.jspmvc.model.BoardDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginCmd implements BoardCmd {
    @Override
    public boolean execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String password = request.getParameter("password");

        System.out.println("id = " + id);
        System.out.println("password = " + password);

        // DAO
        MemberDAO memDAO = new MemberDAO();
//        // id 기준으로 로그인 정보를 가져옴.
        MemberDTO member = memDAO.getLoginData(id);
//        // 비밀번호 매칭
        return isPasswordMatch(password, member.getPassword());
    }

    private boolean isPasswordMatch(String inputPassword, String dbPassword) {
        return inputPassword.equals(dbPassword);
    }
}
