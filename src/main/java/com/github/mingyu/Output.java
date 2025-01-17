package com.github.mingyu;

public class Output {
    //다음 상태를 저장함.
    public final State nextState;
    public final double result;

    public Output(State nextState, double result) {
        this.nextState = nextState;
        this.result = result;
    }

    public Output(State nextState) {
        this(nextState, 0);
    }
}
