package model;

public class Comment {
    private int commentID;          
    private int postID;             
    private String writerID;        
    private String content;        
    private Integer parentCommentID; 

    public Comment(int commentID, int postID, String writerID, String content, Integer parentCommentID) {
        this.commentID = commentID;
        this.postID = postID;
        this.writerID = writerID;
        this.content = content;
        this.parentCommentID = parentCommentID;
    }

    // get
    public int getCommentID() {
        return commentID;
    }

    public int getPostID() {
        return postID;
    }

    public String getWriterID() {
        return writerID;
    }

    public String getContent() {
        return content;
    }

    public Integer getParentCommentID() {
        return parentCommentID;
    }

    // set
    public void setContent(String content) {
        this.content = content;
    }

    // 기능 메서드

    public static Comment addComment(int commentID, int postID, String writerID, String content) {
        return new Comment(commentID, postID, writerID, content, null);
    }

    public static Comment addReply(int commentID, int postID, String writerID, String content, int parentCommentID) {
        return new Comment(commentID, postID, writerID, content, parentCommentID);
    }

    public void deleteComment() {
        this.content = "[삭제된 댓글입니다]";
    }
}
