package frol.querydsl;

public class VideoPreviewDTO {
    private final Long id;
    private final BookPreviewDTO bookPreviewDTO;


    public VideoPreviewDTO(Long id, BookPreviewDTO bookPreviewDTO) {
        this.id = id;
        this.bookPreviewDTO = bookPreviewDTO;
    }

    public Long getId() {
        return id;
    }

    public BookPreviewDTO getBookPreviewDTO() {
        return bookPreviewDTO;
    }

}
