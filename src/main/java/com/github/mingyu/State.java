package com.github.mingyu;

public enum State {
     STATE_INITIAL              //초기상태(첫번째 입력값을 받을 차례)
    ,STATE_READ_NUMBER          //숫자를 읽을 상태(두 번째 숫자)
    ,STATE_READ_NUMBER_ERROR    //숫자를 정상적으로 읽지 못한 경우
    ,STATE_READ_OPERATOR        //연산자를 읽을 상태
    ,STATE_READ_OPERATOR_ERROR  //잘못된 연산자를 입력한 경우
    ,STATE_PRINT_RESULT         //두 번째 숫자를 입력한 후 결과를 출력하기 위한 상태
    ,STATE_CLEAR                //초기화를 하기 위한 상태(초기화 명령을 입력한 경우)
    ,STATE_QUIT                 //프로그램을 종료하기 위한 상태
}
