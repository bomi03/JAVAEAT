package model;

public class Comment {
    private int commentID;
    private int postID;
    private String writerID;
    private String content;
    private int parentCommentID;

    public void addComment(String content) {}
    public void addReply(Comment parent, String replyContent) {}
    public void deleteComment() {}
}