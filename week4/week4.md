# 제어문

### 📖목표

> 자바가 제공하는 제어문을 학습하자.

### 🚧제어문

> Java에서 코드는 위에서 아래 순으로 읽고 실행된다. 모든 일을 순차적으로 실행할 수 있다면 아무런 상관이 없겠지만, 어떤 코드를 반복해야 될 수도 있고 어떤 코드는 건너뛰는 등의 순서를 변경해야 되는 일이 발생한다. 이 때, 제어문을 사용하여 코드 실행 흐름을 제어할 수 있다.이러한 제어문은 선택문(`if-then`, `if-then-else`, `switch`)과, 반복문(`for`, `while`, `do-while`), 분기문(`break`, `continue`, `return`)으로 나뉜다.

## 선택문(Decision-making Statement)

> 조건식의 결과에 의해 실행 될 코드가 달라질 수 있는 구문을 말한다.

### # if-then

`if-then` 문은 가장 기본적인 제어문중 하나로 지정한 조건이 만족할 시에 지정한 블록(`{}`) 안에 있는 코드가 실행된다.

```java
if(조건식) {   // 한 줄일 경우 {} 생략 가능
  조건이 참일 경우 실행되는 코드;
}
```

만약 `if-then`문 안에 코드가 한 줄이라면 `{}`은 생략이 가능하다.

### # if-then-else

기본적인 `if-then` 문에서는 참일 경우만 실행이 됬다면, `if-then-else` 문은 거짓일 때도 실행할 수 있다.  
즉, 조건식이 참일 경우와 거짓일 경우의 실행되는 코드들을 나눌 수 있다는 것이다.

```java
if(조건식) {
  참일 경우;
} else {
  거짓일 경우;
}
```

이렇게 조건이 하나만 존재 할 수 있지만, 여러가지의 조건을 사용해야 할 경우가 생길 수 있다.(예 - 학점)  
이 때는 `else if()`를 사용하여 또 다른 조건식을 사용할 수 있다.

```java
if(조건식 A) {
  조건식 A에 해당하는 경우;
} else if(조건식 B) {
  조건식 B에 해당하는 경우;
} else if(조건식 C) {
  조건식 C에 해당하는 경우;
} else {
  조건식 A, B, C 모두 해당하지 않는 경우;
}
```

### # switch

`switch` 문은 `if-then`과 `if-then-else` 문과 다르게 변수에 대해 평가를 하고 이를 분기할 수 있다.  
평가 당하는 변수는 원시형 타입(`int`, `byte`, `char`...)일 수 있고, `Enum`형 혹은 `String`, `Wrapper`(`Integer`, `Byte`, `Character`...) 클래스도 가능하다.

```java
switch(변수) {
  case 값 A:
    변수가 값 A에 해당하는 경우;
    break;
  case 값 B;
		변수가 값 B에 해당하는 경우;
    break;
  default:
    어떠한 값에도 해당하지 않는 경우;
    break;
}
```

위 예시는 다음과 같이 `if-then-else` 문으로 변경도 가능하다.

```java
if(변수 == 값 A) {
  변수가 값 A에 해당하는 경우;
}else if(변수 == 값 B) {
  변수가 값 B에 해당하는 경우;
}else {
  어떠한 값에도 해당하지 않는 경우;
}
```

<br>

## 반복문(Looping Statement)

> 어떠한 코드가 반복적으로 사용 될 때 사용되는 구문이다.

### # for

프로그래머가 설정한 조건이 만족 될 때까지 지정한 코드 블럭이 계속해서 수행된다.  
카운팅을 할 수도 있겠지만, 배열 혹은 컬렉션 안에 저장되어 있는 값을 순회 할 때도 많이 사용된다.

```java
for(초기식; 조건식; 증감식) {
  반복 될 코드;
}
```

JDK 5.0 이상부터 배열 혹은 컬렉션의 순회시에 다음과 같이 조금 더 향상된 `for` 문을 사용할 수 있다.

```java
for(타입 변수명 : 배열/컬렉션) {
  반복 될 코드;
}
```

### # while

특정 조건이 참일 경우에 지정한 코드 블럭이 계속해서 수행되는 구문이다.

```java
while(조건식) {
  조건식이 참일 경우 반복되는 코드;
}
```

조건식이 항상 참일 경우에는 계속해서 해당 코드들이 실행되므로 다음 명령들을 수행할 수 없는 상태가 된다.  
따라서 조건식을 잘 유의해서 사용해야 된다.

### #do-while

`while` 문이 조건식을 먼저 판별하고 코드를 수행했다면, `do-while` 문은 먼저 코드블럭을 수행하고 조건을 판별한다.

```java
do {
  조건식이 참일 경우 반복되는 코드;
}while(조건식);
```

<br>

## 분기문(Branching Statement)

> 조건문에서 프로그래머의 의도에 의해 중간 흐름을 바꿀 수 있는 구문

### # break

`break` 문은 두가지 케이스로 나뉘는데, 이는 라벨링이 된 것과 안된 것이다.

먼저 라벨링이 되지 않는 `break` 문은 `switch` 문, 반복문에서 사용될 수 있으며, 해당 구문을 만났을 때 가장 가까운 조건문을 종료한다.

```java
for(int i = 0; i <= 10; i++) {
  if(i == 5) break;
  System.out.println(i);
}
// 출력
// 1
// 2
// 3
// 4
```

다음과 같은 예시가 존재할 때, `i`가 5가 되면 반복문을 종료하고 다음 코드로 진행하게 된다.

라벨링이 된 `break` 문은 똑같이 `break`를 만나면 제어문이 종료되지만, 해당 라벨링이 된 제어문 자체가 종료된다.  
즉, 가장 가까운 제어문뿐만 아니라 자신이 표시한 위치안의 제어문을 종료하는 것이다.

```java
int findIt = 10;
search:
for(int i = 0; i < arr.length; i++) {
  for(int j = 0; j< arr[i].length; j++) {
    if(arr[i][j] == findIt) {
      break search;
    }
  }
}
```

위의 예시에서는 `arr`의 값이 10일 경우, `search:` 안에 있는 두개의 `for` 문 모두 종료된다.

### # continue

`continue` 문은 반복문 안에서 사용되며, 조건에 해당할 경우에만 반복문을 건너뛴다.  
`continue` 문도 `break` 문과 똑같이 라벨링이 된 경우와 안된 경우가 존재하고 똑같은 메커니즘으로 동작한다.

```java
for(int i = 0; i <= 10; i++) {
  if(i == 5) continue;
  System.out.println(i);
}
// 출력
// 1
// 2
// 3
// 4
// 6
// 7
// 8
// 9
// 10
```

위의 예제에서는 `i`가 5일 경우에만 가장 가까운 반복문의 끝으로 건너뛴다.

```java
int findIt = 10;
search:
for(int i = 0; i < arr.length; i++) {
  for(int j = 0; j< arr[i].length; j++) {
    if(arr[i][j] == findIt) {
      continue search;
    }
  }
}
```

위의 예제의 경우, `arr`의 값이 10일 경우, `search:` 의 가장 바깥 `for` 문의 끝으로 건너뛰게 된다.

### # return

`return` 문은 현재 메소드를 종료시키고 해당 메소드를 호출한 위치로 돌아간다.  
이 또한 메소드의 타입에 의해 뒤에 값이 올 수 있고 안올 수 있다.

```java
int getAge(String name) {
  if(name == "jongnan") return 28;
  System.out.println("존재하지 않는 사람!");
  return -1;
}
```

위의 예제는 메소드의 타입이 `int` 이므로 반환 값을 지정해주어야 한다.

```java
void pringAge(String name) {
  if(name == "jongnan") {
    System.out.println("28");
  	return; 
  }
  System.out.println("존재하지 않는 사람!");
  return;
}
```

다음 예제는 메소드 타입이 `void`이므로 반환 값이 존재하지 않아도 된다.  
또한, `name`이 `jongnan` 일때는 28을 출력하고 바로 메소드를 종료한다. 

<br>

---

### Reference

* [Oracle Doc - Java Tutorial](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/flow.html)
* [마로의 Java(자바) 정리 - 7. 제어문(Control Flow Statement)-조건문](https://hoonmaro.tistory.com/17)
* [마로의 Java(자바) 정리 - 7. 제어문(Control Flow Statement)-반복문](https://hoonmaro.tistory.com/18?category=598745)

