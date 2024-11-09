package store.domain;

public enum Agree {
    YES("Y"), NO("N"),
    ;

    private final String value;

    Agree(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
