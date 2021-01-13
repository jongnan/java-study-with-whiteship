 

# 예외 처리

### 📖목표

> 자바의 예외 처리에 대해 학습하여라

<br>

## 예외란?

일상생활에서 예외적 상황과 같이 프로그래밍을 하면서도 우리가 의도치 않은 상황들이 일어날 때가 존재한다. 간단한 예를들면 배열에서 인덱스를 통해 참조하고자 할 때, 범위를 벗어난 곳을 참조하는 경우이다. 즉, **프로그램 명령의 정상적인 흐름을 방해하는 요소**들 모두 예외라고 할 수 있다.

### Exception Object

자바에서는 예외가 발생했을 때, 한 객체를 생성하는데 이것이 예외 객체(Exception Object)이다. 이곳에는 에러 정보나 프로그램의 상태나 타입들이 포함되어 있다. 이렇게 생성된 예외 객체를 시스템에 전달하는 것을 **예외 발생(throwing an exception)**이라고 한다.

### 예외 처리

예외 발생이 일어나면 시스템에서는 이를 처리하기 위한 행동을 한다. 먼저 예외가 발생한 지점의 메소드부터 이를 호출한 메소드들의 리스트를 가져오게 되는데 우리가 잘 아고 있는 "call stack"이다. 현재 메소드부터 예외 객체의 유형과 일치하는 처리 로직을 찾게 되고 없을시에 상위 메소드로 넘어가 이를 반복한다. 이렇게 예외 처리 로직을 찾는 것을 **예외 잡기(catch the exception)**라고 하고 잡은 예외 로직은 시스템으로 넘겨 실행 후 종료하게 된다.

<br>

## 예외 계층 구조

자바에서의 모든 예외 클래스들은 모두 Throwable 클래스를 직/간접적으로 상속하고 있다. 이를 큰 두가지 분류로 나눌 수 있는데 바로 **Exception 클래스**와 **Error 클래스**이다. 이들은 Throwable 클래스의 직접적인 서브 클래스이다.

### Exception

복구가 가능한 예외로 프로그램의 비정상적인 종료를 막을 수 있다. 이는 확인이 가능한 예외와 확인이 불가능한 예외로 나뉠 수 있다.

* **확인이 가능한 예외(Checked Exception)**

  > 개발자가 미리 발견할 수 있는 예외로 반드시 예외 처리를 해야한다. 그렇지 않으면 컴파일이 되지 않는다. **RuntimeException 클래스와 이를 직/간접적으로 상속한 클래스들을 제외한 모든 클래스**들이 이에 속한다.

* **확인 불가능한 예외(Unchcecked Exception)**

  > 컴파일 때 체크되지 않는 에외들로 런타임에서 발생할 수 있다. 이들은 **모두 RuntimeException 클래스를 직/간접적으로 상속**받고 있다.  대표적인 예시로 NullPointerException(Null 참조 시)이나 IndexOutOfBoundException(벗어난 인덱스 참조)이 존재한다.

### Error

치명적인 오류로 복구가 불가능한 예외들을 말한다. 컴파일 시점에서 체크할 수 없으며(Unchecked Exception에 속함) 오류가 발생하면 시스템은 비정상적인 종료를 하게된다.

### 계층 구조

![](https://lh5.googleusercontent.com/WqqNoyFEkZXfmZBBQjgIutY72_BUV6_By_BAe7Ih9u36HfelS3nTWQEYtdRUkQS32Tuhg9P9CUXo-jgvOpkO84vLm2viI4Od0BNustwONdMm7DKZnKC6kyVHyRJbsESLIPV4uBU)

출처 :  https://www.javamadesoeasy.com/2015/05/exception-handling-exception-hierarchy.html

<br>

## 예외 처리 방법

Java에서 예외에 대해 이론적으로 어느정도 알게 되었으니 실제로 어떻게 사용되는지 알아보도록 하자.

예외 처리 방법의 경우 크게 해당 위치에서 바로 처리를 하는 경우와 상위 메소드로 넘기는 경우가 존재한다. 전자의 경우, 많이 알고있는 `try-catch-finally` 구문이 이에 해당하며 후자의 경우, `throw`와 `throws` 키워드를 사용하여 예외를 발생시키거나 위임한다.

### 문법

```java
try {
  // 예외가 발생할 수 있는 코드
}catch(예외 타입1 e1) {
	// "e1"란 예외가 발생했을 때 처리할 코드
}catch(예외 타입2 e2) {
	// "e2"란 예외가 발생했을 때 처리할 코드
}finally {
	// 예외와 상관없이 무조건 실행될 코드
}
```

`try` 블럭을 실행하던 도중 예외가 발생한다면 다음 코드는 실행되지 않고 바로 해당 `catch` 블럭으로 넘어가게 된다. 이후, `finally` 블럭의 코드를 실행하게 된다. 여기서 `catch` 블럭과 `finally` 블럭은 선택적으로 사용될 수 있지만, `try` 블럭만 단독적으로 사용되지는 못한다. 즉, `try-catch`, `try-finally`, `try-catch-finally` 등 여러가지 형태로 사용이 가능하다.

### Multi catch

`catch` 블럭의 경우 여러가지 타입의 예외를 처리하기 위해서 다중으로 선언이 가능한 것을 볼 수 있다. Java 7 이후에서는 이를 하나로 뭉쳐서 사용이 가능하다.

```java
try {
  ...
}catch(예외 타입1 | 예외 타입 2 err) {
  ...
}
```

하지만 여기서 주의할 점은 타입들이 부모 자식 관계로 있으면 안되고 동등한 위치에 있어야 한다는 점이다.

### throw, throws

비슷하게 생긴 두 키워드는 다른 용도로 사용된다. 

* **throw**

  Exception을 발생시키고 싶을 때 사용하는 키워드로 `try` 블럭 안에 존재할 경우 바로 `catch` 블럭으로 넘어간다.

  ```java
  try {
    if(number == null) throw new NullPoninterException();
  }catch(NullPointerException e) {
    number = 1;
  }
  ```

* **throws**

  메소드를 정의할 때 사용되는 키워드로 해당 메소드 안에서 정의한 예외가 발생할 수 있다는 것을 알리기 위해 사용된다. 즉, 이 메소드를 사용하는 상위 메소드에 이를 알려 처리할 수 있도록 한다.(예외 처리를 위임)

  ```java
  public int unWrappingInteger(Integer number) throws NullPointerException {
    if(number == null) throw new NullPoninterException();
    return number;
  }
  ```

<br>

## 예외를 직접 만들어 보기

모든 예외 클래스의 경우 Throwable 클래스를 상속하고 있다고 언급했었다. 커스텀한 예외를 만들 경우에도 이 Throwable 클래스 혹은 자식 클래스(Exception, Error, 그 아래 자식도 가능)를 상속받아 구현해도 된다. Throwable 혹은 Exception 클래스를 상속 받았다면, Checked Exception이 되어 바로 예외 처리를 해주어야 한다. Unchecked Exception으로 만들기 위해서는 RuntimeException 혹은 Error 클래스를 상속받으면 된다.

```java
public CustomException extends Throwable {
  CusomException() {
    super("error message")
  }
}
```

`CustomException`의 생성자에서 `super` 키워드를 통해 `Throwable` 클래스를 호출하는 것을 볼 수 있다. 호출시 에러 메시지와 원인을 인자로 넘겨 해당 예외에 맞는 예외 객체를 만들 수 있으며, `getMessage` 나 `getStackTrace`와 같은 메소드로 에러 정보를 얻을 수 있다. Throwable 클래스에 대해 자세히 알고 싶다면 [여기](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Throwable.html)를 보아라.

```java 
try {
  throw new CustomException();
}catch(CustomException e) {
  System.out.println(e.getMessage());	// error message 출력
}
```

<br>

---

### Reference

* [Exceptions - Oracle Doc Java Tutorial](https://docs.oracle.com/javase/tutorial/essential/exceptions/index.html)
* [Exceptions - Oracle Doc](https://docs.oracle.com/javase/specs/jls/se11/html/jls-11.html)
* [Throwable Class - Oracle Doc](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Throwable.html)
* [예외처리(throwable, excetpion, error, throws) : 빨간색코딩](https://sjh836.tistory.com/122)
* [Java - throw와 throws의 차이점 : chacha](https://codechacha.com/ko/java-throw-and-throws/)

