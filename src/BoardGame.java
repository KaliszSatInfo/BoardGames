public class BoardGame {
    private String name;
    private boolean owned;
    private int rating;

    public BoardGame(String name, boolean owned, int rating) {
        this.name = name;
        this.owned = owned;
        this.rating = rating;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
