public class Square {
    private int x, y;

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() { return x; }
    public int getY() { return y; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Square square = (Square) obj;
        return x == square.x && y == square.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }
}
