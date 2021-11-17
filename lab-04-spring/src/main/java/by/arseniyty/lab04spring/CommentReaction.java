package by.arseniyty.lab04spring;

public enum CommentReaction {
    NONE("None"),
    LIKE("Like"),
    DISLIKE("Dislike");

    private final String state;

    CommentReaction(String state) {
        this.state = state;
    }
}
