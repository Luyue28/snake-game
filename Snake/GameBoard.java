import java.util.Timer;
import java.util.TimerTask;

import static java.lang.System.exit;

public class GameBoard {
    private Square food;
    private Snake snake;
    private boolean gameOver;

    private Timer timer;
    private static final int MOVE_DELAY = 500;

    /**
     * Keep track of the last move so that the Snake cannot do 180 degree turns,
     * only 90 degree turns.
     */
    private Direction movement = Direction.DOWN;
    private Direction lastMove = movement;

    public GameBoard() {
        this.snake = new Snake();
        this.gameOver = false;
        newFood();
        startGameLoop();
    }

    private void startGameLoop() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!gameOver) {
                    update();
                }
            }
        }, 0, MOVE_DELAY);
    }

    public void update() {
        moveSnake();
        checkFoodCollision();
        checkWallCollision();
        checkSelfCollision();
    }

    public void setDirection(Direction newDirection) {
        if (newDirection != lastMove || snake.getSize() == 1) {
            movement = newDirection;
        }
    }

    public void directionLeft() { setDirection(Direction.LEFT); }
    public void directionRight() { setDirection(Direction.RIGHT); }
    public void directionUp() { setDirection(Direction.UP); }
    public void directionDown() { setDirection(Direction.DOWN); }

    private void moveSnake() {
        switch (movement) {
            case LEFT: moveSnakeInDirection(-1, 0); break;
            case RIGHT: moveSnakeInDirection(1, 0); break;
            case UP: moveSnakeInDirection(0, -1); break;
            case DOWN: moveSnakeInDirection(0, 1); break;
        }
    }

    private void moveSnakeInDirection(int xOffset, int yOffset) {
        if (!snake.move(xOffset, yOffset)) {
            exit();
        }
    }

    private void checkFoodCollision() {
        if (snake.getHead().equals(food)) {
            snake.grow();
            newFood();
        }
    }

    private void checkWallCollision() {
        if (snake.getHead().getX() < 0 || snake.getHead().getX() >= Properties.BOARD_SIZE ||
                snake.getHead().getY() < 0 || snake.getHead().getY() >= Properties.BOARD_SIZE) {
            gameOver = true;
        }
    }

    private void checkSelfCollision() {
        if (snake.hasCollidedWithSelf()) {
            gameOver = true;
        }
    }

    private void newFood() {
        int x = (int) (Math.random() * Properties.BOARD_SIZE);
        int y = (int) (Math.random() * Properties.BOARD_SIZE);
        food = new Square(x, y);
    }

    public Snake getSnake() {
        return snake;
    }

    public Square getFood() {
        return food;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void stopGame() {
        timer.cancel();
    }
}
