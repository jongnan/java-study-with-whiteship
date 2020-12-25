# 상속

### 📖목표

> 자바의 상속에 대해 학습해보자.

<br>

## 자바 상속의 특징

### 상속이 뭐지?

먼저 **상속**이란, 단어를 짚고 넘어가보자.

> 1. 뒤를 이음.
>
> 2. 법률 일정한 친족 관계가 있는 사람 사이에서, 한 사람이 사망한 후에 다른 사람에게 재산에 관한 권리와 의무의 일체를 이어 주거나, 다른 사람이 사망한 사람으로부터 그 권리와 의무의 일체를 이어받는 일.
>
>    상속 - [ NAVER 국어사전 ]

즉, 하위에 있는 사람(상속을 받는 사람)은 상위에 있는 사람(상속하는 사람)의 모든 것을 물려 받는 것을 말한다. 이러한 단어를 객체 지향 프로그래밍에서도 사용하는 것은 객체간에도 비슷한 일이 발생하기 때문이라고 생각한다. 자바는 객체 지향 프로그래밍 언어이므로 객체간의 상속을 정의할 수 있다. 

### 상속하기

그렇다면 프로그래밍에서 객체를 상속하기 위해서는 어떻게 해야되고 무엇을 상속 받는 것일까?  
일단 상속을 하기 위해서는 `extends` 키워드를 사용한다.

```java
class Car {
  // 상위 클래스
  private int maxSpeed;
  public String company;
  
  public void setMaxSpeed(int maxSpeed) {
    this.maxSpeed = maxSpeed;
  }
  
  public int getMaxSpeed() {
    return this.maxSpeed;
  }
}

class K5 extends Car {
  // 하위 클래스
  K5() {
    this.maxSpeed = 200;	// error : private 접근 불가
    this.setMaxSpeed(200);
    this.company = "KIA";
  }
}
```

위와 같이 `extends` 키워드는 상속 받을 클래스 이름 옆에 작성한다. 상위 클래스에서 `private`로 접근이 제한된 멤버 변수 혹은 메소드를 제외하고 모든 것이 접근 가능하다. 또한 객체를 생성할 때 상위 클래스로 데이터 타입을 지정할 수 있다.

```java
K5 myCar = new K5();
Car parentType_K5 = new K5();
K5 childType_K5 = new Car(); //error
```

자바에서는 상속 받은 관계를 `IS-A` 관계라고 말하는데, `K5는 자동차이다.(K5 is a Car)` 라고 말할 수 있는 관계가 바로 `IS-A` 관계이다. 자동차가 더 큰 범주이기 때문에 해당 관계가 성립하지만, 그 반대인 `Car is a K5`는 성립이 불가능하다. 따라서 위에 3번째 줄과 같이 범주가 큰 상위 객체를 하위 데이터 타입으로 지정할 수 없는 것이다.

그렇다면 다음과 같이 큰 범주에 해당하는 데이터 타입에 객체를 할당하고 다시 이를 작은 범주의 데이터 타입에 할당은 될까?

```java
Car myCar = new K5();
K5 friendCar = myCar;
```

아마 해보면 알겠지만, 컴파일 에러가 발생할 것이다. 이는 당연하게도 여러개의 하위 자식들이 존재할 수 있고 어느 자식에게도 속할 수 있기 때문에 해당 클래스가 어떤 하위 클래스에 속해있는지를 명시해주어야 한다.

```java
K5 friendCar = (K5)myCar;
```

하지만 이도 완벽하게 오류가 안난다고 할 수 없다. 만약 `myCar`가 런타임에 다른 하위 클래스로 캐스팅이 될 수 있는 가능성이 있기 때문이다. 이를 막기 위해서 조건문을 다는 것이 하나의 방법이다.

```java
if(myCar instanceof K5) {  // instanceof 키워드를 사용하여 특정 객체인지 판별이 가능
  K5 friendCar = (K5)myCar
}
```

### 왜 사용해?

이러한 상속을 왜 사용이 되는 것일까? 위에 예시로 든 차로 생각을 해보자. 세상에 존재하는 차들을 일일히 정의한다면 각 차들 마다의 중복된 행동들이 존재할 것이다. 예를들면 출발을 한다든지, 속도를 올린다던지, 시동을 킨다던지 등 자동차가 해야할 역할들이 모두 중복인 셈이다. 따라서 중복적인 코드들을 한데 모으고 이를 사용한다면, **코드의 재사용율이 높아지고 유지보수가 쉬워지기에** 상위 클래스를 두고(중복적인 코드의 집합소) 이를 하위 클래스들이 상속하여 사용하는 것이다.

### 다중 상속

결론부터 얘기하자면 **자바에서는 다중 상속은 불가능**하다. 예시를 보면서 왜 그런지 알아보자.

```java
class Kor {
  void say() {
    System.out.println("안녕하세요!");
  }
}

class Eng {
  void say() {
    System.out.println("Hello!");
  }
}

class Human extends Kor, Eng {
  void greeting() {
    this.say();
  }
}
```

위와 같이 `Kor` 와 `Eng`를 상속하는 `Human`은 과연 누구의 `say`메소드를 호출하는 것일까? 다중 상속이 가능하다면 이러한 문제점이 발생할 확률이 굉장히 높다. 따라서 자바에서는 이를 애초에 방지함으로써 해당 문제를 야기시키지 않는다. 여담으로 다중 상속이 가능한 언어들은 우선 순위를 적용하여 해결한다고 한다.

### Interface

그렇다면 자바에서는 다중 상속이 불가능한 것인가..? 이에 대답은 **No!** 이다. 자바에서는 **인터페이스라는 추상 자료형을 사용하여 클래스가 구현해야되는 메소드들을 정의**해 놓을 수 있다. 인터페이스는 하위 클래스에서 구현해야할 메소드들을 선언해 두고 있어 반강제적으로 구현을 해야된다. 그렇기 때문에 다중의 인터페이스를 가지더라도 하위 클래스에서 어떻게 사용하냐에 따라 구현이 달라지기 때문에 위에 나온 다중 상속의 애매함이 사라진다.

> 상속 - 상위 클래스에서 정의한 메소드를 호출해서 사용
>
> 구현 - 인터페이스에 정의된 메소드들을 하위 클래스에서 따로 정의하여 사용

```java
interface Kor {
  void say();
}

interface Eng {
  void say();
}

class Human implements Kor, Eng {
  private String country;
  
  Human(String country) {
    this.country = country;
  }
  
  @Override
  public void say() {
    if(this.country == "KOR") {
      System.out.println("안녕하세요!");
    }else {
     	System.out.println("Hello!"); 
    }
  }
}
```

위와 같이 `Kor` 과 `Eng`의 `say` 메소드를 `Human`에서 따로 구현하므로 어떤 인터페이스의 `say`인지 알 필요가 없다. 따라서 인터페이스를 통한 다중 상속은 가능한 것이다. 

> 인터페이스는 8주차 주제로 있으므로 간단하게만 설명하고 넘어가겠다.

<br>

## super 키워드

`super` 키워드는 상위 클래스로 참조하기 위해 사용한다. 즉, 상속 받은 클래스(하위 클래스)가 상속한 클래스(상위 클래스)의 필드 혹은 메소드에 접근하기 위한 방법중 하나이다.

```java
class User {
  private String name;
  
  User(String name) {
    this.name = name;
  }
}
```

위와 같이 클래스의 인스턴스 변수와 내부 변수의 이름이 같을 때 `this` 키워드를 통해 구분할 수 있었는데, `super` 키워드도 이와 마찬가지로 현재 클래스(하위 클래스)와 상속한 클래스(상위 클래스)를 구분 지을때 사용한다.

```java
class Human {
  String name;
}

class User extends Human {
  String name;
  
  User(String name) {
    this.name = name;       // 인스턴스 변수와 내부 변수의 구분
    super.name = this.name; // 상위 클래스와 하위 클래스의 구분
  }
}
```

또, `this` 키워드랑 마찬가지로 `super` 키워드도 클래스 변수/메소드(`static`)은 접근이 불가능하다.

### super 메소드

`this()` 메소드가 해당 클래스의 다른 생성자를 호출할 때 사용하듯 `super()` 메소드 또한 상위 클래스의 생성자를 호출 할 때 사용한다. 해당 메소드를 사용하지 않아도 알아서 상위 클래스가 생성되는 것은 컴파일러가 자식 클래스의 생성자에 자동으로 추가해주기 때문이다.

```java
class Parent {
  int num;
  Parent(int num) {
    this.num = num;
  } 
}

class Child extends Parent{
}
```

만약 다음과 같이 클래스가 정의 되어있다면 컴파일 에러가 발생할 것이다. 왜 그럴까? 바로 상위 클래스의 기본 생성자는 없고 인자를 받는 생성자만 존재한다. 그렇기 때문에 **컴파일러가 자동으로 기본 생성자를 넣어주지 않는다.** 따라서 하위 클래스에서는 상위 클래스를 생성할 수 없는 것이다. 

이러할 때는 두가지 방법이 존재한다.

1. 상위 클래스의 기본 생성자를 넣어준다.
2. 하위 클래스를 생성할 때 `super()` 메소드를 사용하여 정의되어있는 생성자를 호출한다.

```java
class Parent {
  int num;
  Parent() {}       // 1번 방법 : 컴파일러가 자동으로 상위 클래스의 생성자를 호출
  Parent(int num) {
    this.num = num;
  } 
}

class Child extends Parent{
  Child() {
    super(5);       // 2번 방법 : 작성자가 임의적으로 상위 클래스의 생성자를 호출
  }
}
```

<br>

## Overloading VS Overriding

자바를 사용하면서 메소드 **오버라이딩**과 **오버로딩**을 많이 들어봤을 것이다. 이 둘의 특징과 차이점을 알기 위해서는 먼저 메소드 시그니처란 것을 알아야한다.

### 메소드 시그니처(method signature)

메소드 시그니처란, 단어 그대로 메소드들을 구분지을 수 있는 잣대를 말한다. 자바에서 여러 메소드들이 존재할 때, 같은 메소드란 것을 어떻게 알 수 있을까? 이는 바로 **메소드 명**, **인자 타입/순서**를 통해 알 수 있다.

```java
public void methodA() {}
public void methodA(int a) {}
```

위 두개의 메소드는 이름은 같지만, 인자들이 다르기 때문에 다른 메소드라고 판단한다.

### 오버로딩

여기서 오버로딩의 개념이 나오게 된다. 오버로딩은 **같은 이름의 메소드를 중복으로 사용하여 여러가지 종류의 메소드를 정의하는 것**을 말한다. 인자 타입이나 순서가 다르기 때문에 이름이 중복으로 사용할 수 있다.

```java
public void print() {
  System.out.println("default");
}

public void print(int n) {
  System.out.println(n);
}
```

### 오버라이딩

메소드 오버라이딩은 오버로딩과는 다르게 **상위 클래스에 존재하는 메소드를 하위 클래스에서 재정의 하는 것**을 말한다. 이때 주의해야 될 점은 기존 메소드 시그니처와 완전히 동일해야 되며, 접근 제어자를 좁은 범위로 바꾸거나 더 큰 범위의 예외처리는 할 수 없다.

```java
class Parent {
  public int num = 10;
  public void print() {
    System.out.println(num);
  }
}

class Child extends Parent{
  @Override
  public void print() {
    System.out.println(num + " 입니다.");
  }
}
```

<br>

## 다이나믹 메소드 디스패치(Dynamic Method Dispatch)

### 다형성

앞서 상속에 대해 언급하였을 때, 상위 클래스의 데이터 타입으로 하위 클래스의 객체를 만들 수 있다고 하였다(업캐스팅). 또한, 그 연장선상에서 하위 클래스에서 상위 클래스의 메소드를 재작성할 수도 있었다(오버라이딩). 자바에서는 왜 이런 특징을 가지고 있는 것일까? 바로 "**다형성**"을 보장하기 위해서라고 생각한다.

다형성이란, **하나의 큰 데이터 타입에 여러가지 객체를 매핑시켜 여러가지 동작을 할 수 있게하는 성질**을 말한다. 이러한 다형성은 자바에서 굉장히 중요한 개념으로 배우곤한다. 다형성을 통해 얻을 수 있는건 무엇일까? 바로 "**객체의 부품화**"이다. 여러가지 부품을 만들어 놓고 상황에 맞는 부품만 갈아끼우며 코드의 재사용성을 높히고 유지보수를 용이하게 할 수 있다.

### 다이나믹..메소드.. 디스패치..?

> **들어가기 전에!!** 
>
> 자바를 깊게 접해보지 못해봤던 필자는 이 단어를 스터디를 통해 처음 알게 되었다. 바로 앞에 다형성이 왜 중요한지에 대해 잠깐 언급을 하고 갔는데 자바를 공부하며 꼭 알아야 할 개념이고 다이나믹 메소드 디스패치와도 관계가 있기에 언급을 하게 되었다.

이름만 들어보면 엄청난 기술일 것 같지만 위에서 말한 것들로 구현이 가능하다.

```java
class Parent {
  public void method() {
    System.out.println("Parent");
  }
}

class A extends Parent{
  @Override
  public void method() {
    System.out.println("A");
  }
}

class B extends Parent{
  @Override
  public void method() {
    System.out.println("B");
  }
}

class C extends Parent{
  @Override
  public void method() {
    System.out.println("C");
  }
}
```

이와 같이 여러개의 클래스가 하나의 클래스의 메소드를 재정의 하고 있다고 했을 때, 상위 클래스의 데이터 타입으로 하나의 변수를 정의한다면 여러가지 하위 클래스의 객체를 그 변수의 매핑이 가능하다.

```java
public static void main(String args[]) {
  Parent parent = new Parent();
  A a = new A();
  B b = new B();
  C c = new C();
  Parent dmd;
  
  dmd = parent;
  dmd.method();		// "Parent"
  
  dmd = a;
  dmd.method();		// "A"
  
  dmd = b;
  dmd.method();		// "B"
  
  dmd = c;
  dmd.method();		// "C"
}
```

그 매핑이 가능하다는 것은 `dmd.method()` 와 같이 하나의 코드로 여러가지 행위가 가능하다는 것이다.  
즉, 다이나믹 메소드 디스패치는 **다형성이란 특징을 이용하여 여러가지 행동을 하나의 변수에 원하는 시점(런타임)에서 매핑할 수 있는 매커니즘**이다.

<br>

## 추상 클래스

자바에서 추상 클래스란, 추상 메소드를 포함하거나 포함하지 않는 클래스를 추상 클래스라고 한다. 이러한 추상 클래스는 `abstract`라는 키워드를 사용하여 선언할 수 있으나 인스턴스로 생성할 수는 없다. 즉, 상속을 하기위한 전용 클래스인 셈이다.

```java
abstract class [클래스 이름] {}
```

### 추상 메소드

추상 메소드는 단어 그대로 추상적인, 안에 구체적인 내용이 없는 메소드를 말한다.

```java
abstract [반환 타입] [메소드 이름]();
```

추상 메소드는 위와 같이 선언할 수 있으며 선언부가 없기 때문에 괄호 다음 바로 `;`를 추가한다. 이러한 추상 메소드는 구현부가 없기 때문에 반드시 하위 클래스에서 오버라이딩을 사용해야 한다.

### 추상 클래스 VS 인터페이스

추상 메소드를 포함한 추상 클래스는 앞서 언급한 인터페이스와 굉장히 닮아있다. 상위 클래스 혼자만 인스턴스화 될 수 없고, 상위 클래스에서는 선언만 하며, 이를 하위 클래스에서 구현하도록 강제할 수 있다는 점이 바로 그 점이다. 그렇다면 무엇이 다를까?

|            | 추상 클래스                                         | 인터페이스                                            |
| ---------- | :-------------------------------------------------- | ----------------------------------------------------- |
| 필드       | `static`, `final` 이거나 아닌 필드 선언             | 모든 필드는 자동으로 `public`, `static`, `final` 선언 |
| 메소드     | 여러가지 접근 제어자 사용 가능                      | 자동으로 `public`으로 선언                            |
| 서브클래스 | 밀접하게 관련된 여러 클래스의 코드를 공유할 때 사용 | 서로 관련이 없는 클래스들도 사용 가능                 |
| 다중 상속  | 불가능                                              | 가능                                                  |

위에 특징들을 보면, `static` 혹은 `final`이 아닌 필드를 선언하거나 여러가지 접근 제어자를 사용하기 위해서는 추상 클래스를, 다중 상속을 하거나 상속 받는 클래스들이 전혀 상관이 없다면 인터페이스를 사용하는 것이 좋을것 같다.

<br>

## final 키워드

가금 구현을 하다 필드에 `final`을 붙힌것을 볼 수 있을 것이다. `final` 키워드는 변수, 메소드, 클래스에도 사용이 가능하며 각각 하는 역할은 비슷하게 무언가를 막는 역할을 하기 위해서 선언한다.

* 변수

  > 다른 값으로 대입하는 것을 막음

* 메소드

  > 하위 클래스에서 재정의 하는 것을 막음

* 클래스

  > 상속을 막음

<br>

## Object 클래스

모든 클래스들이 이를 직/간접적으로 이 Object 클래스를 상속하고 있으며, 11개의 메소드로 구성되어 있다.(필드 X)

| 메소드                        | 설명                                                         |
| ----------------------------- | ------------------------------------------------------------ |
| clone()                       | 객체의 복제본을 생성하여 반환                                |
| equals(Object obj)            | 객체와 전달 받은 객체가 같은지 여부를 반환                   |
| finalize()                    | 객체의 리소스를 정리하기 위해 호출                           |
| getClass()                    | 객체의 클래스 타입을 반환                                    |
| hashCode()                    | 객체의 해시 코드값을 반환                                    |
| notify()                      | 객체의 대기중인 하나의 스레드를 다시 실행할 때 호출          |
| notifyAll()                   | 객체의 대기중인 모든 스레드를 다시 실행할 때 호출            |
| toString()                    | 객체의 정보를 문자열로 반환                                  |
| wait()                        | 객체의 현재 스레드를 notify, notifyAll을 호출할 때까지 대기 시킬 때 호출 |
| wait(long timeout)            | 객체의 현재 스레드를 notify, notifyAll을 호출할 때 혹은 전달 받은 시간만큼만 대기 시킬 때 호출 |
| wait(long timeout, int nanos) | 객체의 현재 스레드를 notify, notifyAll을 호출할 때 혹은 전달 받은 시간만큼 대기하거나 다른 스레드가 현재 스레드를 인터럽트할 때까지 대기시킬 때 호출 |

<br>

---

### Reference

* [Inheritance - Oracle Java doc](https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html)
* [05-4 상속 - 점프 투 자바](https://wikidocs.net/280)
* [Polymorphism - Oracle Java doc](https://docs.oracle.com/javase/tutorial/java/IandI/polymorphism.html)
* [[JAVA/자바] 다형성(polymorphism)의 개념/의미/예제](https://m.blog.naver.com/PostView.nhn?blogId=heartflow89&logNo=220979244668&proxyReferer=https:%2F%2Fwww.google.com%2F)
* [Dynamic Method Dispatch or Runtime Polymorphism in Java - GeeksforGeeks](https://www.geeksforgeeks.org/dynamic-method-dispatch-runtime-polymorphism-java/)
* [Abstract Methods and Classes - Oracle Java doc](https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html)
* [Object as a Superclass - Oracle Java doc](https://docs.oracle.com/javase/tutorial/java/IandI/objectclass.html)

