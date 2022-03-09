package intulda.poly.assignment.domain.board.model;

import java.util.Arrays;

public enum BoardState {
    STABLE("N"), DELETE("Y"), EMPTY("EMPTY");

    private String code;

    BoardState(String code) {
        this.code = code;
    }

    public static BoardState findHasCode(String code) {
        return Arrays.stream(BoardState.values())
                .filter(state -> state.getCode().equals(code))
                .findAny()
                .orElse(EMPTY);
    }

    public String getCode() {
        return this.code;
    }
}
