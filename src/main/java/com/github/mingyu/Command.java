package com.github.mingyu;

public enum Command {
    CANCLE("C"),
    QUIT("Q");

    private String command;

    Command(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
