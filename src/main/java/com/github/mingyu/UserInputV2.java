package com.github.mingyu;

public class UserInputV2 {
    public static final UserInputV2 QUIT = new UserInputV2(State.STATE_QUIT); //프로그램 종료상태
    public static final UserInputV2 CLEAR = new UserInputV2(State.STATE_CLEAR); //초기화 상태
    public static final UserInputV2 READ_NUMBER_ERROR = new UserInputV2(State.STATE_READ_NUMBER_ERROR);
    public static final UserInputV2 READ_OPERATOR_ERROR = new UserInputV2(State.STATE_READ_OPERATOR_ERROR);

    //다음 상태를 저장함.
    public final State nextState;

    //입력받은 숫자를 저장할 멤버 변수
    public final double number;

    //입력받은 연산자를 저장할 멤버 변수
    public final String operator;

    public UserInputV2(State nextState, double number, String operator){
        this.nextState = nextState;
        this.number = number;
        this.operator = operator;
    }

    public UserInputV2(State nextState){
        this(nextState, 0, null);
    }

    public UserInputV2(State nextState, double number){
        this(nextState, number, null);
    }

    public UserInputV2(State nextState, String operator){
        this(nextState, 0, operator);
    }
}
