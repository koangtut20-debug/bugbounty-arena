package com.example.bugbounty.dto;

public class LeaderboardUser {

    private String name;
    private int points;

    public LeaderboardUser() {
    }

    public LeaderboardUser(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
