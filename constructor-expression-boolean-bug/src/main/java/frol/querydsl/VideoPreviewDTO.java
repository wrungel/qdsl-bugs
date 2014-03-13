package frol.querydsl;

public class VideoPreviewDTO {
    private final Long id;
    private final boolean goodBook;
    private final String bookTitle;


    public VideoPreviewDTO(Long id, boolean goodBook, String bookTitle) {
        this.id = id;
        this.goodBook = goodBook;
        this.bookTitle = bookTitle;
    }

    public VideoPreviewDTO(Long id, String bookTitle) {
        this(id, false, bookTitle);
    }

    public VideoPreviewDTO(Long id, boolean goodBook) {
        this(id, goodBook, null);
    }


    public VideoPreviewDTO(Long id) {
        this(id, false, null);

    }

    public Long getId() {
        return id;
    }


    public boolean isGoodBook() {
        return goodBook;
    }

    public String getBookTitle() {
        return bookTitle;
    }
}
