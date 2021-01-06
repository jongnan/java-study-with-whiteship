# 인터페이스

### 📖목표

> 자바의 인터페이스에 대해 학습하여라.
<br>

## 인터페이스란?

인터페이스란, 어떠한 기능을 사용하기 위해 **일종의 약속** 맺은 것과 같다. 즉, 사용자는 인터페이스에 적혀 있는대로만 사용할 수 있으며, 기능을 제공하는 자는 이 기능들을 꼭 구현해야만 하는 것이다. 사용자는 제공자가 어떻게 구현했는지에 대해 상세히 알 필요가 없어 결합도가 낮아지며 모듈화가 가능해진다. 또한, 제공자는 자신의 코드를 노출하지 않아도 된다는 장점도 가지고 있다.

<br>

## 인터페이스 정의하기

Java에서 인터페이스는 클래스와 같이 레퍼런스 타입에 속하며, 오직 상수, 메소드 시그니처, default 메소드, static 메소드 그리고 중첩된 타입만 포함할 수 있다.

인터페이스를 정의하기 위해서는 `interface` 키워드를 사용하면 된다.

```java
interface 인터페이스_이름 {
  int a = 10;		// -> public static final int a = 10;
  void 메서드_A(매개변수);	// -> public abstract void 메서드_A(매개변수);
  int 메서드_B();		// -> public abstract int 메서드_B();
}
```

인터페이스에서는 필드의 경우 `public static final`, 메소드의 경우 `public abstract` 를 생략해도 컴파일러가 자동으로 이를 추가한다.

<br>

## 인터페이스 구현하기

인터페이스를 구현하기 위해서는 `implements` 키워드를 사용하는데, 상속과 똑같은 위치에 작성한다.

```java
class 구현체 implements 인터페이스_이름 {
  @Override
  public void 메서드_A(매개변수) {
    ...
  }
  
  @Override
  public int 메서드_B() {
    ...
  }
}
```

인터페이스에 정의한 추상 메소드는 무조건 구현해야하며, 만약 일부만 구현해야 된다면 `abstract` 키워드를 사용하여 추상 클래스로 만들어야 한다.

<br>

## 인터페이스 레퍼런스를 통해 구현체를 사용하기

인터페이스의 경우 내부 추상 메서드가 정의 되어있지 않으므로 `new` 키워드를 통해 인스턴스화 할 수 없으며 이를 구현한 구현체를 사용하여 사용할 수 있다. 상속과 마찬가지로 인터페이스와 이를 구현한 구현체에서 다형성이 적용되어질 수 있기에 인터페이스 자체를 타입으로 지정할 수 있다.

```java
interface Human {
  void say()
}

class User implements Human {
  @Override
  public void say() {
    System.out.println("Hi!")
  }
}
```

위와 같이 `Human`이란 인터페이스와 그에 대한 구현체가 존재할 때, 다음과 같이 생성하여 사용할 수 있다.

```java
class App {
  public static void main(String[] args) {
    Human jongnan = new User();
    jongnan.say();
  }
}
```

<br>

## 인터페이스 상속

인터페이스 또한 인터페이스로부터 `extends` 키워드를 사용하여 상속을 받을 수 있다. 클래스 상속과는 다르게 상속에 있어 애매함이 없기에 다중 상속이 된다.

```java
interface Human {
  void move();
  void say();
}

interface Weapon {
  void attack();
}

interface Citizen extends Human {
}

interface Soldier extends Human, Weapon {
}
```

상속을 받은 인터페이스의 구현체는 상위 인터페이스의 추상 메소드까지 구현을 해야된다.

<br>

## 인터페이스의 default 메소드(Java 8)

Java 8에서부터 인터페이스에 추가된 default 메소드는 추상 메소드가 아닌 기본 메소드를 넣을 수 있게 해주는 것이다. 기본적으로 인터페이스의 메소드는 추상 메소드였지만, 이를 추가한 이유는 **효율적인 유지보수**를 위해서이다. 기존 인터페이스에 하나의 메소드를 추가한다면, 이를 구현한 구현체 모두 추가해야하는 번거로움이 생긴다. 하지만 이를 인터페이스 단에서 구현한다면 한 곳만 손보면 된다.

```java
interface Eng {
  void move();
  default void say() {
    System.out.println("Hi!");
  }
}
```

위와 같이 `default` 키워드를 통해 선언 할 수 있으며 접근제어자는 `public`으로 생략이 가능하다.

하지만 여기서 의문이 하나 생긴다. 클래스에서도 이와 같은 문제로 다중 상속이 불가능 했는데, 이렇게 한다면 인터페이스도 다중 상속이 불가능한거 아닌가?

```java
interface Eng {
  default void say() {
    System.out.println("Hi!");
  }
}

interface Kor {
  default void say() {
    System.out.println("안녕!");
  }
}

class User implements Eng, Kor {
  // 컴파일 에러 발생!
}
```

직접 코딩해보면 알겠지만, 이렇게 애매한 상황에서는 컴파일 에러가 발생한다. 즉, 다중 상속을 막아 놓았지만, 이를 회필 할 수 있는 방법은 존재한다. 바로 애매한 메소드를 재정의하면 된다.

```java
class User implements Eng, Kor {
  @Override
  public void say() {
    Eng.super.say();
  }
}
```

위와 같이 `say` 메소드를 재정의한다면 컴파일 에러가 발생하지 않으며, 원하는 인터페이스의 default 메소드를 선택하여 사용할 수 있다.

<br>

## 인터페이스의 static 메소드(Java 8)

Java 8에 또한 static 메소드를 정의할 수 있다. `static` 키워드를 사용하여 정의할 수 있으며 구현체에서는 이를 재정의 할 수 없다.

```java
interface User {
  static String getType() {
    return "Human";
  }
  
  void say();
}

class American implements User {
  public void say() {
    System.out.println("Hi!");
  }
}

class Korean implements User {
  public void say() {
    System.out.println("안녕!");
  }
}

class App {
  public static void main(String[] args) {
    User kor = new Korean();
    String userType = User.getType();
    String korType = kor.getType();	// 에러발생
  }
}
```

위와 같이 인터페이스에 바로 접근하여 사용하여야 하며 그렇지 않을 시에는 에러가 발생한다.

<br>

## 인터페이스의 private 메소드(Java 9)

Java 9부터 **중복적인 코드를 제거하고 캡슐화를 위해** private 메소드를 사용할 수 있게 되었다. 8에서 default와 static 메소드를 사용하는데 있어 중복적인 코드가 존재할 수 있다. 이를 없애기 위해서는 똑같이 default와 static 메소드를 사용하여 없앨 수 있지만, 외부에서 접근이 가능하다. 이러한 점을 보완하기 위해 `private` 키워드를 사용하여 메소드를 정의할 수 있다. 

``` java
interface User {
  default void walk() {
    printMove();
  }
  
  default void run() {
    printMove();
  }
  
  default void stop() {
    System.out.println("멈춤!");
  }
  
  private void printMove() {
    System.out.println("움직임 시작!");
  }
}
```

static 메소드에서는 `private static` 키워드를 함께 사용하면 된다.

<br>

---

### Reference

* [interfaces - java oracle doc](https://docs.oracle.com/javase/tutorial/java/IandI/createinterface.html)
* [Static and Default Methods in Interfaces in Java - Baeldung](https://www.baeldung.com/java-static-default-methods)
* [Private Methods in Java 9 Interfaces - GeeksforGeeks](https://www.geeksforgeeks.org/private-methods-java-9-interfaces/)

