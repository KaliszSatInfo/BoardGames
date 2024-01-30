public class BoardGame {
    private String name;
    private boolean owned;
    private int score;

    public BoardGame(String name, boolean owned, int score) {
        this.name = name;
        this.owned = owned;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOwned() {
        return owned;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
