package kr.ac.daegu.jspmvc.model;

import java.sql.Time;
import java.util.Date;

// db에서 Board테이블의 컬럼과 row를 정의.
// DataTransferObject : db에서 가져오는 테이블 row 데이터의 묶음.
// ValueObject        : db에서 가져오는 테이블 row 데이터의 묶음. setter가 없다.
public class CommentDTO {
    private int cid;
    private int id;
    private String author;
    private String content;
    private Date writeDate;
    private Time writeTime;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(Date writeDate) {
        this.writeDate = writeDate;
    }

    public Time getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(Time writeTime) {
        this.writeTime = writeTime;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "cid=" + cid +
                ", id=" + id +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", writeDate=" + writeDate +
                ", writeTime=" + writeTime +
                '}';
    }
}
