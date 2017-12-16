package net.maidsafe.model;

public enum OpenMode {
    OVERWRITE(1),
    APPEND(2);

    private int value;

    OpenMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
