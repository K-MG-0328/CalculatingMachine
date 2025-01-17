
## 추가 및 변경사항
### CalculaotrV1
#### BigDecimal 사용
String.valueOf(operand)를 통해 BigDecimal 객체를 만들기 때문에 부동소수점 오차가 없도록 수정했습니다.
#### 0으로 나누기
ArithmeticException이 발생 시, 안내 문구 출력 후 계산 상태를 초기 상태로 돌리도록 변경했습니다.

### CalculatorV2
#### Command, State Enum Class 추가
클래스의 상수를 Enum 객체로 변경했습니다. 
#### UserInputV2 Class 추가
Calculator의 Inner Class UserInput를 분리하였습니다.
#### Output Class 추가 
UserInput에서 결과값에 대한 처리를 Output Class가 처리함으로써 사용 목적을 명확히하였습니다.

### CalculatorV3
#### exp4j 라이브러리를 사용하여 구현
exp4j를 사용하여 입력된 수식과 결과를 콘솔에 출력하도록하였습니다. 기존에 operand와 operator를 각각 입력했던 방식에서 한 번에 입력하는 방식으로 변경


## 계산기 (Calculator)
참조 : https://gist.github.com/nnoco/ec4bd53f750a236a91011c862427ebf6
이 프로젝트는 Java로 작성된 콘솔 기반 사칙연산 계산기입니다.
기존 프로젝트에서 BigDecimal을 사용하여 부동소수점 오류를 최소화하였고, 사용자의 입력에 따라 다양한 상태로 전환되도록 상태 머신 방식으로 구성되어 있습니다.

목차
프로젝트 소개
코드 구조
사용 방법
추가 정보

## 프로젝트 소개
사용자로부터 숫자와 연산자를 순차적으로 입력받아, 사칙연산(+, -, *, /)을 수행하는 간단한 콘솔 계산기입니다.
BigDecimal을 사용하여 연산 결과의 정확도를 높였으며, 0으로 나누는 경우에는 예외 처리를 통해 경고 메시지를 출력합니다.
C를 입력하면 초기화( CLEAR ), Q를 입력하면 **프로그램 종료( QUIT )**가 동작합니다.

## 코드 구조
#### calculator 클래스
상수(STATE_...) 를 통해 계산기의 상태를 관리
start() 메서드에서 메인 루프를 돌면서 상태 전환
readNumber(), readOperator() 등을 통해 입력값 처리
**calculateResult()**에서 BigDecimal 연산 수행

#### UserInput 클래스
사용자의 입력(숫자, 연산자)과 다음 상태 정보를 저장
미리 정의된 static final 필드를 통해 특정 상태(QUIT, CLEAR, ERROR 등)를 공유

## 사용 방법
프로그램 실행 시, 첫 번째 숫자를 입력
예: 10
연산자를 입력
예: +
두 번째 숫자를 입력
예: 5
결과가 출력되며, 다시 연산자를 입력할 수 있는 상태가 됩니다.
예: 결과: 10.0 + 5.0 = 15.0
계속 계산을 원하는 경우, 연산자를 입력 후 새 숫자를 입력하면 됩니다.
초기화(C)
다시 처음 상태로 돌아가며, 결과 및 입력값이 리셋됩니다.
종료(Q)
프로그램이 종료됩니다.

