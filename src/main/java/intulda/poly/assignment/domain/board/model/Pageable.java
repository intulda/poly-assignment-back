package intulda.poly.assignment.domain.board.model;

import lombok.Data;

@Data
public class Pageable {

    private Long offset;

    private Long size;

    public Pageable() {
        this.offset = 1L;
        this.size = 10L;
    }
}
