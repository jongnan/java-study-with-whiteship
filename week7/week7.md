# 패키지

### 📖목표

> 자바 패키지에 대해 학습해보자.

<br>

## package 키워드

컴퓨터에서 디렉토리를 통해 파일들을 분리하고 관리하듯 자바에서는 패키지 별로 클래스와 인터페이스들을 분리하여 사용할 수 있다.

패키지를 생성하기 위해서는 작성하고 있는 파일 맨 상단에 `package` 키워드와 해당 패키지의 이름을 작성하면 된다.

```java
package figure;

class Circle {
  ...
}
```

위 ` Circle` 클래스는 `figure` 이란 패키지에 포함되어 있는 것을 의미한다.

### Naming

변수명 혹은 함수명이 중요하듯 패키지명 가독성을 위해 몇가지 규칙을 사용하는 것이 좋다.

* 클래스 또는 인터페이스 이름과 충돌하지 않도록 모두 소문자로 작성

* 회사의 경우 도메인의 역뱡향으로 패키지들을 만들어 사용

   `com.example.mypackage`

* 만약 도메인에 예약어가 들어가 있거나, 숫자로 시작하는 경우, 특수문자가 들어가는 경우 보통 `_`를 사용

  ` aa-bb.example.org`	→	 `org.example.aa_bb`

  `example.int` 				→	`int_.exmaple`

  `123aa.example.com`	→	`com.example._123aa`

* java에서 자체로 사용하는 패키지의 경우 java 혹은 javax로 시작

### 접근

패키지로 묶인 클래스 혹은 인터페이스에 접근하기 위해서는 메소드를 호출하듯 `.`을 사용하여 접근할 수 있다.

```java
public figure.Circle circle = new figure.Circle();
```

<br>

## import 키워드

기본적인 패키지 접근 방식에서 패키지의 이름이 굉장히 길다면 어떻게 될까? 당연하게도 가독성이 굉장히 떨어질 것이다. 이럴때 사용할 수 있는 것이 바로 `import` 키워드이다.

`import` 키워드를 파일 상단(`package` 키워드 아래)에 작성하여 해당 클래스/인터페이스를 불러올 수 있다.

```java
package graphics;

import figure.Circle;

class Painter {
  private Circle circle = new Circle();
  ...
}
```

위와 같이 `graphics` 패키지에서 `figure` 패키지의 `Circle` 클래스를 임포트해서 같은 패키지에서 부르듯 사용할 수 있다.

### 패키지 전체 import

위의 예시의 경우, 하나의 클래스(`Circle`)만 가져온 것이다. 만약 `figure` 패키지에 `Circle` 말고도 `Rectangle`, `Triangle` 등 여러개 클래스를 임포트해야 된다면, `*` 를 사용하여 패키지 내부의 클래스들을 모두 가져올 수 있다.

```java
// figure 패키지
package figure;

class Circle {
  ...
}

class Rectangle {
	... 
}

class Triangle {
  ...
}

// graphics 패키지
package graphics;

import figure.*;

class Painter {
  private Triangle tri = new Triangle();
  private Rectangle rect = new Rectangle();
}
```

### Static import

만약 static 메소드에 접근하거나 상수(`static final`로 선언)의 경우 `import`문 중간에 `static` 키워드를 사용하여 임포트가 가능하다.

```java
import static java.lang.Math.PI;
```

위 예시는 자바에서 기본적으로 제공하는 것으로 PI 값을 상수화하여 `Math` 클래스에 가지고 있는 것을 임포트한 것이다.

### import가 필요 없는 패키지?

기본적으로 같은 패키지 안에 속한다면 `import` 키워드를 사용하지 않아도 접근할 수 있다. 이 외에도 `java.lang` 패키지(`String`) 또한 `import` 하지 않아도 사용이 가능하다.

<br>

## Classpath

Classpath란, 말 그대로 **클래스의 경로를 가리키는 것**이다. 하나의 디렉토리 안에 해당 클래스들만 사용한다면 상관이 없겠지만, 여러가지 디렉토리의 다양한 클래스를 찾아 실행 시키기 위해서 JVM에서는 해당 경로들을 알 필요가 있다. 즉, 편지를 보내고자 하는 사람의 주소를 알고 있어야 보낼 수 있듯 클래스를 사용하기 위해서는 해당 클래스의 경로를 꼭 알고 있어야하며, 이는 Classpath란 경로들의 목록으로 나타낸 것이다.

### 클래스가 실행되는 과정

1. 소스코드(.java) → 바이트코드(.class)
2. 바이트코드를 실행하기 위해 classpath에서 경로 검색
3. classpath는 바이트코드(.class) 파일의 경로를 디렉토리를 포함하여 콜론(`:`, window 에서는 세미콜론(`;`))으로 구분
4. classpath에서 첫번째로 찾은 파일을 실행

### Classpath 설정 방법

* 환경변수 설정
* 실행 시 `-classpath` 옵션 사용

### Classpath 설정 값

* `/aaa/bbb/ccc/java/classes` (디렉토리)
* `myclasses.zip` (zip 파일 → 압축된 클래스 파일 세트) 
* `myclasses.jar` (jar 파일 → 압축된 클래스 파일 세트)

<br>

## CLASSPATH 환경변수

환경변수란, OS에서 참고하고 있는 변수라고 생각하면 좋다. 프로그래밍에서 변수를 사용하여 동적인 동작을 할 수 있듯 프로세스는 환경변수를 보고 자신이 해야할 동작을 결정한다.

이러한 환경변수들 중에 `CLASSPATH` 라는 변수를 설정할 수 있으며 이는 JVM이 구동될 때 클래스 로더가 이 변수를 호출하여 해당 경로 안에 클래스들을 로드한다. 환경변수를 설정하면 실행 시에 따로 설정을 하지 않아도 된다는 장점이 존재한다.

이 값을 설정하는 방법은 OS별로 다르며, 구글링을 통해 쉽게 얻을 수 있으니 건너 뛰도록 하겠다.

<br>

## -classpath 옵션

환경변수를 설정하는 방법 외에도 해당 클래스를 실행할 때 `-classpath` 라는 옵션을 사용하는 방법이 존재한다.

```bash
java -classpath ".:lib" MyClass
```

위의 예시와 같이 `MyClass` 파일을 실행할 때, 현재 디렉토리 혹은 `lib`라는 디렉토리에서 클래스를 찾을 것을 설정해논 것이다.

<br>

<br>

## 접근지시자

**접근 지시자**는 **접근 제어 지시자**와 같은 말이며 **접근 제어자**라고도 불린다. 이는 우리가 프로그래밍할 때 자주 보는 `public`, `private` 등이다. 접근 지시자는 말 그대로 해당 변수, 메소드 혹은 클래스에 접근을 설정하는 것이다. 자바에서는 4개의 접근 지시자가 존재하며 범위는 다음과 같다.

* public

  > 어떤 클래스에서 접근 가능

* protected

  > 동일 패키지 내의 클래스 혹은 상속받은 외부 패키지의 클래스에서만 접근이 가능

* default(no modifier)

  > 접근 지시자를 별도로 사용하지 않으면 해당 패키지 내에서만 접근이 가능

* private

  > 해당 클래스에서만 접근이 가능

|               | 같으 클래스 | 같은 패키지 | 상속받은 클래스 | 전체 |
| :-----------: | :---------: | :---------: | :-------------: | :--: |
|  **public**   |      O      |      O      |        O        |  O   |
| **protected** |      O      |      O      |        O        |  ✕   |
|  **default**  |      O      |      O      |        ✕        |  ✕   |
|  **private**  |      O      |      ✕      |        ✕        |  ✕   |

<br>

---

### Reference

* [Packages - Oracle java doc](https://docs.oracle.com/javase/tutorial/java/package/index.html)

* [패키지 - TCPSchool](http://www.tcpschool.com/java/java_usingClass_package)
* [자바 클래스패스(classpath)란?](https://effectivesquid.tistory.com/entry/%EC%9E%90%EB%B0%94-%ED%81%B4%EB%9E%98%EC%8A%A4%ED%8C%A8%EC%8A%A4classpath%EB%9E%80)
* [[JAVA] Java 환경변수(path) 설정하기](https://blog.opid.kr/62)
* [클래스 패스 - 생활코딩](https://opentutorials.org/course/1223/5527)
* [07-2 접근제어자 (Access Modifier) - 점프 투 자바](https://wikidocs.net/232)

