package main.models;

public class Book {

    private Author author;
    private String title;
    private short released;
    private String subTitle;
    private String comment;
    private boolean alreadyRead;
    private boolean markedForRead;
    private boolean isOwned;

    public Book() {}

    // this constructor is for testing
    public Book(Author author, String title, short released, String subTitle, String comment,
                boolean alreadyRead, boolean markedForRead, boolean isOwned) {
        this.author = author;
        this.title = title;
        this.released = released;
        this.subTitle = subTitle;
        this.comment = comment;
        this.alreadyRead = alreadyRead;
        this.markedForRead = markedForRead;
        this.isOwned = isOwned;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public short getReleased() {
        return released;
    }

    public void setReleased(short released) {
        this.released = released;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isAlreadyRead() {
        return alreadyRead;
    }

    public void setAlreadyRead(boolean alreadyRead) {
        this.alreadyRead = alreadyRead;
    }

    public boolean isMarkedForRead() {
        return markedForRead;
    }

    public void setMarkedForRead(boolean markedForRead) {
        this.markedForRead = markedForRead;
    }

    public boolean isOwned() {
        return isOwned;
    }

    public void setOwned(boolean owned) {
        isOwned = owned;
    }

    @Override
    public String toString() {
        String baseText = author.toString() + " - " + title + " (" + released + ")";
        String text = addSubtitleAndComment(baseText);

        if (isOwned) {
            text = "[+] " + text;
        }

        if (alreadyRead) {
            text = "(olvasott) " + text;
        }

        if (markedForRead) {
            text = "(!) " + text;
        }

        return text;
    }

    private String addSubtitleAndComment(String baseText) {
        String text = baseText;

        if (subTitle != null) {
            text += " - " + subTitle;
        }
        if (comment != null) {
            text += " /*" + comment + "*/";
        }

        return text;
    }

}
