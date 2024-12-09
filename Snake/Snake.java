import java.util.LinkedList;

public class Snake {
    private LinkedList<Square> body;
    private Direction direction;

    public Snake() {
        body = new LinkedList<>();
        body.add(new Square(5, 5));
        body.add(new Square(5, 4));
        body.add(new Square(5, 3));
        direction = Direction.RIGHT;
    }

    public Square getHead() {
        return body.getFirst();
    }

    public int getSize() {
        return body.size();
    }

    public boolean move(int xOffset, int yOffset) {
        Square head = getHead();
        int oldX = head.getX();
        int oldY = head.getY();
        Square newHead = new Square(oldX + xOffset, oldY + yOffset);

        if (contains(newHead)) return false;

        body.addFirst(newHead);
        removeTail();
        return true;
    }

    private boolean contains(Square square) {
        for (Square segment : body) {
            if (segment.equals(square)) {
                return true;
            }
        }
        return false;
    }

    private void removeTail() {
        body.removeLast();
    }

    public void grow() {
        Square tail = body.getLast();
        body.addLast(new Square(tail.getX(), tail.getY()));
    }

    public boolean hasCollidedWithSelf() {
        Square head = getHead();
        for (int i = 1; i < body.size(); i++) {
            if (body.get(i).equals(head)) {
                return true;
            }
        }
        return false;
    }
}
