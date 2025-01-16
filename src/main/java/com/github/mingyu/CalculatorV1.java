package com.github.mingyu;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class CalculatorV1 {

    //초기상태(첫번째 입력값을 받을 차례)
    public static final int STATE_INITIAL = 0;

    //숫자를 읽을 상태(두 번째 숫자)
    public static final int STATE_READ_NUMBER=1;

    //숫자를 정상적으로 읽지 못한 경우
    public static final int STATE_READ_NUMBER_ERROR=2;

    //연산자를 읽을 상태
    public static final int STATE_READ_OPERATOR =3;

    //잘못된 연산자를 입력한 경우
    public static final int STATE_READ_OPERATOR_ERROR=4;

    //두 번째 숫자를 입력한 후 결과를 출력하기 위한 상태
    public static final int STATE_PRINT_RESULT=5;

    //초기화를 하기 위한 상태(초기화 명령을 입력한 경우)
    public static final int STATE_CLEAR=6;

    //프로그램을 종료하기 위한 상태
    public static final int STATE_QUIT=7;

    public Set<String> validOperators;
    private Scanner sc;

    public CalculatorV1(){
        sc = new Scanner(System.in);

        //연산자 모음
        validOperators = new HashSet<>(Arrays.asList("+","-","*","/"));
    }

    public void start(){
        boolean isContinue = true;
        double operand1 = 0, operand2 = 0;
        String operator = null;
        int state = STATE_INITIAL;
        UserInput result = null;
        UserInput input = null;

        //프로그램 종료시(Q입력 시) false로 바뀌고 루프를 종료
        while(isContinue){
            switch(state){
                //초기에 첫번째 숫자를 입력하는 상태
                case STATE_INITIAL:
                    input = readNumber(state);
                    operand1 = input.number;
                    state = input.nextState;
                    break;
                //숫자를 읽는 상태에서의 처리
                case STATE_READ_NUMBER:
                    input = readNumber(state);
                    //읽어온 숫자를  operand2 에 저장(두 번째 숫자)
                    operand2 = input.number;
                    state = input.nextState;
                    break;
                // 결과를 출력하는 상태의 처리
                case STATE_PRINT_RESULT:
                    result = calculateResult(operand1, operand2, operator); // 변경 부분
                    if(result.nextState != STATE_INITIAL) System.out.printf("결과: %s %s %s = %s\n", operand1, operator, operand2, result.number);
                    operand1 = result.number;
                    state = result.nextState;
                    operator = null;
                    break;
                // 잘못된 숫자를 입력한 경우
                case STATE_READ_NUMBER_ERROR:
                    System.out.println("잘못된 숫자입니다. 올바른 숫자를 입력해주십시오.");
                    // 연산자 입력여부로 첫 번째 숫자인지, 두 번째 숫자인지를 구분함
                    // 연산자가 null인 경우 첫 번째 숫자를 입력 중 오류가 났던 것을 알 수 있다.
                    state = operator == null ? STATE_INITIAL : STATE_READ_NUMBER;
                    break;
                //연산자를 읽을 상태에서의 처리
                case STATE_READ_OPERATOR:
                    input = readOperator(STATE_READ_OPERATOR);
                    //operator 변수에 읽은 연산자를 저장
                    operator = input.operator;
                    state = input.nextState;
                    break;
                //잘못된 연산자를 입력한 경우의 처리
                case STATE_READ_OPERATOR_ERROR:
                    System.out.println("잘못된 연산자입니다.");
                    state = STATE_READ_OPERATOR;
                    break;
                //초기화를 하는 경우의 처리
                case STATE_CLEAR:
                    //초기 상태로 돌아감
                    state = STATE_INITIAL;
                    // 입력받은 연산자를 null로 초기화
                    operator = null;
                    System.out.println("초기화 되었습니다.");
                    break;
                //프로그램을 종료할 때의 처리
                case STATE_QUIT:
                    System.out.println("프로그램을 종료합니다.");
                    //루프의 조건인 isContinue를 false로 변경
                    isContinue = false;
                    break;
            }
        }
    }

    private UserInput readNumber(int state){
        return readInput("숫자를 입력해주세요(초기화 C, 종료 Q) : ", state);
    }

    private UserInput readOperator(int state){
        return readInput("연산자를 입력해주세요(초기화 C, 종료 Q) : ", state);
    }

    private UserInput readInput(String msg, int state){
        System.out.println(msg);
        String input = sc.nextLine();

        if("C".equals(input)){ //초기화 여부 확인
            return UserInput.CLEAR;
        }else if("Q".equals(input)){ // 종료 여부 확인
            return UserInput.QUIT;
        }else { //종료나 초기화가 아닌 경우
            if(state == STATE_READ_NUMBER){ //초기값 이후의 값일 경우
                return checkNumber(input, STATE_PRINT_RESULT);
            }else if (state == STATE_INITIAL){ //초기값인 경우
                return checkNumber(input, STATE_READ_OPERATOR);
            }else{ //연산자 일 경우
                return checkOperator(input);
            }
        }
    }

    private UserInput checkNumber(String input, int state){
        try {
            double number = Double.parseDouble(input);
            return new UserInput(state, number);
        }catch (NumberFormatException e){
            return UserInput.READ_NUMBER_ERROR;
        }
    }

    private UserInput checkOperator(String input){
        if(validOperators.contains(input)){
            return new UserInput(STATE_READ_NUMBER, input);
        }else{
            return UserInput.READ_OPERATOR_ERROR;
        }
    }

    private UserInput calculateResult(double operand1, double operand2, String operator) {

        BigDecimal op1 = new BigDecimal(String.valueOf(operand1));
        BigDecimal op2 = new BigDecimal(String.valueOf(operand2));
        BigDecimal result = null;
        try { //0으로 나눴을 때에 예외 체크
            switch(operator) {
                case "+": result = op1.add(op2); break;
                case "-": result = op1.subtract(op2); break;
                case "*": result = op1.multiply(op2); break;
                case "/": result = op1.divide(op2); break;
            }
        }catch (ArithmeticException e){
            System.out.println("0으로 나눌 수 없습니다.");
            return new UserInput(STATE_INITIAL);
        }

        return new UserInput(STATE_READ_OPERATOR, result.doubleValue());
    }

    public static void main(String[] args) {
        new CalculatorV1().start();
    }
}

//사용자 입력 정보 저장을 위한 클래스
class UserInput{
    public static final UserInput QUIT = new UserInput(CalculatorV1.STATE_QUIT); //프로그램 종료상태
    public static final UserInput CLEAR = new UserInput(CalculatorV1.STATE_CLEAR); //초기화 상태
    public static final UserInput READ_NUMBER_ERROR = new UserInput(CalculatorV1.STATE_READ_NUMBER_ERROR);
    public static final UserInput READ_OPERATOR_ERROR = new UserInput(CalculatorV1.STATE_READ_OPERATOR_ERROR);

    //다음 상태를 저장함.
    public final int nextState;

    //입력받은 숫자를 저장할 멤버 변수
    public final double number;

    //입력받은 연산자를 저장할 멤버 변수
    public final String operator;

    public UserInput(int nextState, double number, String operator){
        this.nextState = nextState;
        this.number = number;
        this.operator = operator;
    }

    public UserInput(int nextState){
        this(nextState, 0, null);
    }

    public UserInput(int nextState, double number){
        this(nextState, number, null);
    }

    public UserInput(int nextState, String operator){
        this(nextState, 0, operator);
    }
}