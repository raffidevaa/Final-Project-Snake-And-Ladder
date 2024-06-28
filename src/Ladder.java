public class Ladder {
    private int fromPosition;
    private int toPosition;

    public Ladder(int from, int to) {
        this.fromPosition = from;
        this.toPosition = to;
    }

    public int getFromPosition() {
        return this.fromPosition;
    }

    public int getToPosition() {
        return this.toPosition;
    }
}
