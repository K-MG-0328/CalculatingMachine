package com.github.mingyu;


import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;

import java.util.Scanner;

public class CalculatorV3 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String quit = Command.QUIT.getCommand();
        double result = 0.0;

        boolean isContinue = true;

        while (isContinue) {

            System.out.println("식을 입력해주세요(종료 Q)");
            String text = sc.nextLine();

            if(quit.equals(text)) {
                isContinue = false;
                break;
            }

            Expression expression;
            try {
                expression = new ExpressionBuilder(text).build();
            }catch (IllegalArgumentException e) {
                System.out.println("올바른 표현식을 작성해주세요.");
                continue;
            }

            // 유효성 검증
            ValidationResult validationResult = expression.validate();

            if(validationResult.isValid()){
                result = expression.evaluate();
                System.out.printf("%s = %f\n",text, result);
            }else{
                System.out.println("올바른 표현식을 작성해주세요.");
            }
        }
    }
}
