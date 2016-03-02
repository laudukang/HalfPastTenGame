package me.laudukang.util;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/2
 * <p>Time: 16:51
 * <p>Version: 1.0
 */
public enum Suits {
    CLUBS("梅花"),
    HEARTS("红心"),
    SPADES("黑桃"),
    DIAMONDS("方块");

    private String description;

    private Suits(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
