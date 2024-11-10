package store.domain.pos;

public class PosContext {

    private Pos pos;

    public void init(Pos pos) {
        this.pos = pos;
    }

    public Pos getPos() {
        return pos;
    }
}
