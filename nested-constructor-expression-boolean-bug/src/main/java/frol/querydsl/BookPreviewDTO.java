package frol.querydsl;

public class BookPreviewDTO {

    private final Long id;
    private final boolean good;

    public BookPreviewDTO(Long id) {
        this(id, false);
    }

    public BookPreviewDTO(Long id, boolean good) {
        this.id = id;
        this.good = good;
    }

    public boolean isGood() {
        return good;
    }

    public Long getId() {
        return id;
    }

}
