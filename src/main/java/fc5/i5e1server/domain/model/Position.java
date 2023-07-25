package fc5.i5e1server.domain.model;

public enum Position {
    STAFF("사원"),
    BOSS("사장");

    private final String type;

    Position(String type) {
        this.type = type;
    }
}
