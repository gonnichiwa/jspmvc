package kr.ac.daegu.jspmvc.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// DatabaseAccessObject : 이 객체가 db에 접속해서 쿼리를 날리고 결과를 리턴해주는 책임
public class BoardDAO {
    private static final String DB_URL  = "jdbc:mariadb://localhost:3306/dgd";
    private static final String DB_USER = "root";
    private static final String DB_PW   = "0000";

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        // Connection, PreparedStatement, ResultSet은 interface 객체이다.
        Class.forName("org.mariadb.jdbc.Driver");
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
    }

    public ArrayList<BoardDTO> getBoardList(int pageNum, int pagePerRow) throws ClassNotFoundException, SQLException {
        // Connection, PreparedStatement, ResultSet은 interface 객체이다.
        Connection conn = this.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // 페이징 처리에 따라 rowNum의 시작과 끝값을 변수처리
        int startRowNum;
        int endRowNum = pageNum*pagePerRow;
        if(pageNum == 1){
            startRowNum = 0;
        } else {
            startRowNum = (pagePerRow*(pageNum-1))+1;
        }

        // 쿼리 준비 & db 쿼리
        pstmt = conn.prepareStatement("select * from " +
                "(select board.*, ROW_NUMBER() OVER() as rowNum from board order by replyRootId desc, ordernum asc) tb" +
                " where tb.replyRootId between "+startRowNum+" and "+endRowNum);
        rs = pstmt.executeQuery();

        // 글 목록을 반환할 ArrayList
        ArrayList<BoardDTO> boardRowList = new ArrayList<BoardDTO>();

        // db에서 데이터를 row단위로 가져와서
        // list에 넣는다.
        while(rs.next()) {
            int id = rs.getInt("id");
            String author = rs.getString("author");
            String subject = rs.getString("subject");
            String content = rs.getString("content");
            Date writeDate = rs.getDate("writeDate");
            Time writeTime = rs.getTime("writeTime");
            int readCount = rs.getInt("readCount");
            int commentCount = rs.getInt("commentCount");

            BoardDTO dto = new BoardDTO();
            dto.setId(id);
            dto.setAuthor(author);
            dto.setSubject(subject);
            dto.setContent(content);
            dto.setWriteDate(writeDate);
            dto.setWriteTime(writeTime);
            dto.setReadCount(readCount);
            dto.setCommentCount(commentCount);

            boardRowList.add(dto);
        }

        // db로부터 데이터 잘 들어왔는지 확인 (log 찍어봄)
        for(BoardDTO dto: boardRowList){
            System.out.println(dto.toString());
        }

        return boardRowList;
    }

    public int getBoardNewId() throws ClassNotFoundException, SQLException {
        // Connection, PreparedStatement, ResultSet은 interface 객체이다.
        Connection conn = this.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // newId를 가져오는 쿼리
        pstmt = conn.prepareStatement("select max(id) + 1 AS newId from Board");
        rs = pstmt.executeQuery();

        int newId = 0;
        if(rs.next()){
            newId = rs.getInt("newId");
            return newId;
        }

        // 예외 발생
        throw new SQLException("글 컨텐츠를 새로 입력하기 위한 아이디값 받아오기를 실패하였습니다.");
    }

    public void insertBoardContent(int newId,
                                   String subject,
                                   String author,
                                   String content,
                                   String password) throws ClassNotFoundException, SQLException {
        // Connection, PreparedStatement, ResultSet은 interface 객체이다.
        Connection conn = this.getConnection();
        PreparedStatement pstmt = null;

        // 쿼리 준비 & db 쿼리
        // insert into board values (1, 'testAuthor', 'testSubject', 'testContent', CURDATE(), CURTIME(), 0, 0)
        pstmt = conn.prepareStatement("insert into Board values (?, ?, ?, ?, CURDATE(), CURTIME(), 0, 0, ?, ?, 0, 0)");
        pstmt.setInt(1, newId);
        pstmt.setString(2, author);
        pstmt.setString(3, subject);
        pstmt.setString(4, content);
        pstmt.setString(5, password);
        pstmt.setInt(6, newId);
        pstmt.executeUpdate();

    }

    public BoardDTO getBoardData(int id) throws ClassNotFoundException, SQLException {
        // db에 접속해서
        Connection conn = this.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        // 쿼리 실행시키고
        pstmt = conn.prepareStatement("select * from Board where id = ?");
        pstmt.setInt(1, id);
        rs = pstmt.executeQuery();
        // 반환 데이터를 리턴.
        BoardDTO data = new BoardDTO();
        if(rs.next()){
//            int id = rs.getInt("id");
            String author = rs.getString("author");
            String subject = rs.getString("subject");
            String content = rs.getString("content");
            Date writeDate = rs.getDate("writeDate");
            Time writeTime = rs.getTime("writeTime");
            int readCount = rs.getInt("readCount");
            int commentCount = rs.getInt("commentCount");
            String password = rs.getString("password");
            int replyRootId = rs.getInt("replyRootId");
            int depth = rs.getInt("depth");
            int orderNum = rs.getInt("orderNum");

            data.setId(id);
            data.setAuthor(author);
            data.setSubject(subject);
            data.setContent(content);
            data.setWriteDate(writeDate);
            data.setWriteTime(writeTime);
            data.setReadCount(readCount);
            data.setCommentCount(commentCount);
            data.setPassword(password);
            data.setReplyRootId(replyRootId);
            data.setDepth(depth);
            data.setOrderNum(orderNum);

        }
        return data;
    }

    public void boardRowPlusReadCount(int rowId, int howMuch) throws ClassNotFoundException, SQLException {
        // db에 접속해서
        Connection conn = this.getConnection();
        PreparedStatement pstmt = null;

        // 해당 아이디의 row에서 readCount를 +1 해주는 쿼리 실행
        pstmt = conn.prepareStatement("update Board set readCount=readCount + ? where id = ?");
        pstmt.setInt(1, howMuch);
        pstmt.setInt(2, rowId);
        pstmt.executeUpdate();
    }

    public void updateBoardContent(int id,
                                   String subject,
                                   String content) throws ClassNotFoundException, SQLException {
        // db에 접속해서
        Connection conn = this.getConnection();
        PreparedStatement pstmt = null;
        // 해당 아이디의 row에서 subject와 content를 업데이트
        pstmt = conn.prepareStatement("update Board set subject=?, content=? where id = ?");
        pstmt.setString(1, subject);
        pstmt.setString(2, content);
        pstmt.setInt(3, id);
        pstmt.executeUpdate();

    }

    public void deleteBoardData(int id) throws ClassNotFoundException, SQLException {
        // db에 접속해서
        Connection conn = this.getConnection();
        PreparedStatement pstmt = null;
        // 해당 아이디의 row를 삭제
        pstmt = conn.prepareStatement("delete from board where id = ?");
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }

    public int getBoardTotalRowCount() throws ClassNotFoundException, SQLException {
        // db에 접속해서
        Connection conn = this.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        // board테이블 전체 row 갯수
        pstmt = conn.prepareStatement("select count(*) as count from Board");
        rs = pstmt.executeQuery();

        if(rs.next()){
            return rs.getInt("count");
        }

        throw new SQLException("Board테이블의 전체 갯수를 가지고 올 수 없습니다.");
    }


    public void insertComment(int newCommentId,
                              int boardId,
                              String commentAuthor,
                              String commentContent) throws ClassNotFoundException, SQLException {
        // db에 접속해서
        Connection conn = this.getConnection();
        PreparedStatement pstmt = null;

        // 새로운 댓글을 insert
        pstmt = conn.prepareStatement("insert into comment values (?, ?, ?, ?, CURDATE(), CURTIME())");
        pstmt.setInt(1, newCommentId);
        pstmt.setInt(2, boardId);
        pstmt.setString(3, commentAuthor);
        pstmt.setString(4, commentContent);
        pstmt.executeUpdate();
    }

    public int getCommentNewId() throws ClassNotFoundException, SQLException {
        // Connection, PreparedStatement, ResultSet은 interface 객체이다.
        Connection conn = this.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // newId를 가져오는 쿼리
        pstmt = conn.prepareStatement("select max(cid) + 1 AS newId from Comment");
        rs = pstmt.executeQuery();

        int newId = 0;
        if(rs.next()){
            newId = rs.getInt("newId");
            return newId;
        }

        // 예외 발생
        throw new SQLException("댓글 컨텐츠를 새로 입력하기 위한 아이디값 받아오기를 실패하였습니다.");
    }

    public List<CommentDTO> getCommentList(int boardContentId) throws ClassNotFoundException, SQLException {
        List<CommentDTO> list = new ArrayList<CommentDTO>();
        // Connection, PreparedStatement, ResultSet은 interface 객체이다.
        Connection conn = this.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        pstmt = conn.prepareStatement("select cid as 'commentId', id, author, content, writeDate, writeTime from comment where id = ?");
        pstmt.setInt(1, boardContentId);
        rs = pstmt.executeQuery();

        while(rs.next()){
            CommentDTO dto = new CommentDTO();
            dto.setCid(rs.getInt("commentId"));
            dto.setId(rs.getInt("id"));
            dto.setAuthor(rs.getString("author"));
            dto.setContent(rs.getString("content"));
            dto.setWriteDate(rs.getDate("writeDate"));
            dto.setWriteTime(rs.getTime("writeTime"));

            list.add(dto);
        }

        return list;
    }

    public void updateCommentCount(int boardId) throws ClassNotFoundException, SQLException {
//        throw new SQLException("updateCommentCount 실패");
        // Connection, PreparedStatement, ResultSet은 interface 객체이다.
        Connection conn = this.getConnection();
        PreparedStatement pstmt = null;

        pstmt = conn.prepareStatement("update board set commentCount=commentCount+1 where id = ?");
        pstmt.setInt(1, boardId);
        pstmt.executeUpdate();
    }

    public void insertReplyContent(int newId,
                                   String subject,
                                   String author,
                                   String content,
                                   String password,
                                   int replyRootId,
                                   int depth,
                                   int orderNum) throws ClassNotFoundException, SQLException {
        // Connection, PreparedStatement, ResultSet은 interface 객체이다.
        Connection conn = this.getConnection();
        PreparedStatement pstmt = null;

        pstmt = conn.prepareStatement("insert into Board values (?, ?, ?, ?, CURDATE(), CURTIME(), 0, 0, ?, ?, ?, ?)");
        pstmt.setInt(1, newId);
        pstmt.setString(2, author);
        pstmt.setString(3, subject);
        pstmt.setString(4, content);
        pstmt.setString(5, password);
        pstmt.setInt(6, replyRootId);
        pstmt.setInt(7, depth);
        pstmt.setInt(8, orderNum);
        pstmt.executeUpdate();

    }

    // replyRootId depth기준 minOrderNum을 가져옴
    public int getMinOrderNum(int replyRootId, int depth, int orderNum) throws ClassNotFoundException, SQLException {
        int result;
        // Connection, PreparedStatement, ResultSet은 interface 객체이다.
        Connection conn = this.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        pstmt = conn.prepareStatement("SELECT NVL(MIN(ordernum),0) as minOrderNum FROM board" +
                "   WHERE  replyRootId = ?" +
                "   AND orderNum > ?" +
                "   AND depth <= ?");
        pstmt.setInt(1, replyRootId);
        pstmt.setInt(2, orderNum);
        pstmt.setInt(3, depth);
        rs = pstmt.executeQuery();

        if(rs.next()){
            return rs.getInt("minOrderNum");
        }
        throw new SQLException("failed to get minOrderNum");
    }

    public int getReplyOrderNum(int replyRootId) throws ClassNotFoundException, SQLException {
        int result;
        // Connection, PreparedStatement, ResultSet은 interface 객체이다.
        Connection conn = this.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        pstmt = conn.prepareStatement("SELECT NVL(MAX(orderNum),0) + 1 as orderNumToInsertBoard FROM board" +
                " WHERE replyRootId = ?");
        pstmt.setInt(1, replyRootId);
        rs = pstmt.executeQuery();

        if(rs.next()){
            return rs.getInt("orderNumToInsertBoard");
        }
        throw new SQLException("failed to get orderNumToInsertBoard");
    }

    public void updateOrderNum(int replyRootId, int minOrderNum) throws ClassNotFoundException, SQLException {
        // Connection, PreparedStatement, ResultSet은 interface 객체이다.
        Connection conn = this.getConnection();
        PreparedStatement pstmt = null;

        pstmt = conn.prepareStatement("UPDATE BOARD SET orderNum = orderNum + 1" +
                " WHERE replyRootId = ?  AND orderNum >= ?");
        pstmt.setInt(1, replyRootId);
        pstmt.setInt(2, minOrderNum);
        pstmt.executeUpdate();
    }
}
