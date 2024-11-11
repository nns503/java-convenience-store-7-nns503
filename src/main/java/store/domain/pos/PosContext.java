package store.domain.pos;

public enum PosContext {
    INSTANCE;

    private Pos pos;

    public void init(Pos pos) {
        this.pos = pos;
    }

    public Pos getPos() {
        return pos;
    }
}
