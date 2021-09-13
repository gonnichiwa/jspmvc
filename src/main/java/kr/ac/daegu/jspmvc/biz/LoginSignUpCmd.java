package kr.ac.daegu.jspmvc.biz;

import kr.ac.daegu.jspmvc.common.PasswordEncoder;
import kr.ac.daegu.jspmvc.model.MemberDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class LoginSignUpCmd implements BoardCmd {
    @Override
    public boolean execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String password = request.getParameter("password");

        // 0. password encoding (hashing)
        String generatedSalt = String.valueOf(UUID.randomUUID());
        String encodedPassword = PasswordEncoder.getEncodedPassword(password, generatedSalt);

        MemberDAO memberDAO = new MemberDAO();
        // 1. id가 unique한지 아닌지 검사

        // 2. member insert를 위한 최고 id값 을 가져와야함.

        // 3. mId, id, encodedPassword insert
        memberDAO.postLoginData(id, encodedPassword);
        return false;
    }
}
