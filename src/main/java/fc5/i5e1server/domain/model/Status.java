package fc5.i5e1server.domain.model;

public enum Status {

    REQUESTED("신청 중"),
    APPROVED("승인 완료"),
    CANCELED("취소 완료"),
    REJECTED("요청 반려"),
    COMPLETED("완료");

    private final String description;

    Status(String description) {
        this.description = description;
    }
}