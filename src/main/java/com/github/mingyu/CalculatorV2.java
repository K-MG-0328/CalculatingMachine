package com.github.mingyu;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class CalculatorV2 {

    public Set<String> validOperators;
    private Scanner sc;

    public CalculatorV2(){
        sc = new Scanner(System.in);

        //연산자 모음
        validOperators = new HashSet<>(Arrays.asList("+","-","*","/"));
    }

    public void start(){
        boolean isContinue = true;
        double operand1 = 0, operand2 = 0;
        String operator = null;
        State state = State.STATE_INITIAL;
        Output output = null;
        UserInputV2 input = null;

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
                    output = calculateOperand(operand1, operand2, operator);
                    if(output.nextState != State.STATE_INITIAL) System.out.printf("결과: %s %s %s = %s\n", operand1, operator, operand2, output.result);
                    operand1 = output.result;
                    state = output.nextState;
                    operator = null;
                    break;
                // 잘못된 숫자를 입력한 경우
                case STATE_READ_NUMBER_ERROR:
                    System.out.println("잘못된 숫자입니다. 올바른 숫자를 입력해주십시오.");
                    // 연산자 입력여부로 첫 번째 숫자인지, 두 번째 숫자인지를 구분함
                    // 연산자가 null인 경우 첫 번째 숫자를 입력 중 오류가 났던 것을 알 수 있다.
                    state = operator == null ? State.STATE_INITIAL : State.STATE_READ_NUMBER;
                    break;
                //연산자를 읽을 상태에서의 처리
                case STATE_READ_OPERATOR:
                    input = readOperator(State.STATE_READ_OPERATOR);
                    //operator 변수에 읽은 연산자를 저장
                    operator = input.operator;
                    state = input.nextState;
                    break;
                //잘못된 연산자를 입력한 경우의 처리
                case STATE_READ_OPERATOR_ERROR:
                    System.out.println("잘못된 연산자입니다.");
                    state = State.STATE_READ_OPERATOR;
                    break;
                //초기화를 하는 경우의 처리
                case STATE_CLEAR:
                    //초기 상태로 돌아감
                    state = State.STATE_INITIAL;
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

    private UserInputV2 readNumber(State state){
        return readInput("숫자를 입력해주세요(초기화 C, 종료 Q) : ", state);
    }

    private UserInputV2 readOperator(State state){
        return readInput("연산자를 입력해주세요(초기화 C, 종료 Q) : ", state);
    }

    private UserInputV2 readInput(String msg, State state) {
        System.out.println(msg);
        String input = sc.nextLine();

        String cancle = Command.CANCLE.getCommand();
        String quit = Command.QUIT.getCommand();

        if (cancle.equals(input)) { //초기화 여부 확인
            return UserInputV2.CLEAR;
        }

        if (quit.equals(input)) { // 종료 여부 확인
            return UserInputV2.QUIT;
        }

        if (state == State.STATE_READ_NUMBER) { //초기값 이후의 값일 경우
            return parseNumber(input, State.STATE_PRINT_RESULT);
        }

        if (state == State.STATE_INITIAL) { //초기값인 경우
            return parseNumber(input, State.STATE_READ_OPERATOR);
        }

        return validateOperator(input);
    }

    private UserInputV2 parseNumber(String input, State state){
        try {
            double number = Double.parseDouble(input);
            return new UserInputV2(state, number);
        }catch (NumberFormatException e){
            return UserInputV2.READ_NUMBER_ERROR;
        }
    }

    private UserInputV2 validateOperator(String input){
        if(validOperators.contains(input)){
            return new UserInputV2(State.STATE_READ_NUMBER, input);
        }else{
            return UserInputV2.READ_OPERATOR_ERROR;
        }
    }

    private Output calculateOperand(double operand1, double operand2, String operator) {

        BigDecimal op1 = new BigDecimal(String.valueOf(operand1));
        BigDecimal op2 = new BigDecimal(String.valueOf(operand2));
        BigDecimal result = null;

            switch(operator) {
                case "+" -> result = op1.add(op2);
                case "-" -> result = op1.subtract(op2);
                case "*" -> result = op1.multiply(op2);
                case "/" -> {
                    try { //0으로 나눴을 때에 예외 체크
                        result = op1.divide(op2);
                    }catch (ArithmeticException e){
                        System.out.println("0으로 나눌 수가 없습니다.");
                        return new Output(State.STATE_INITIAL);
                    }
                }
            }
        return new Output(State.STATE_READ_OPERATOR, result.doubleValue());
    }

    public static void main(String[] args) {
        new CalculatorV2().start();
    }
}
