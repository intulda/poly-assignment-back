package intulda.poly.assignment.domain.board.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardRequest {

    @ApiModelProperty(hidden = true)
    private Long boardId;

    @ApiModelProperty(hidden = true)
    private Long accountId;

    private String boardTitle;

    private String boardContents;

    @ApiModelProperty(hidden = true)
    private String boardState;

    @ApiModelProperty(hidden = true)
    private LocalDateTime regDt;

    @ApiModelProperty(hidden = true)
    private LocalDateTime udtDt;

    @ApiModelProperty(hidden = true)
    private BoardState boardDelete;

}
