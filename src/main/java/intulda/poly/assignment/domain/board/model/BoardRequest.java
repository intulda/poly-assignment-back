package intulda.poly.assignment.domain.board.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardRequest {

    private Long boardId;

    private Long accountId;

    private String boardTitle;

    private String boardContents;

    private String boardState;

    private LocalDateTime regDt;

    private LocalDateTime udtDt;

    private BoardState boardDelete;

}
