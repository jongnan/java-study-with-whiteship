<img src="image/junit_5_icon.png">

# JUnit 5

### 📖목표

> JUnit 5가 무엇인지, 어떻게 사용하는 것인지 알아보고 테스트에 익숙해져 보자.

<br>

### 💡들어가기 앞서...

이 글을 보고 있는 여러분은 자신이 짠 로직의 테스트코드를 짜거나, TDD를 해본 경험이 있는가?  
필자는 이전부터 테스트와는 거의 담을 쌓을 정도로 테스트 코드를 작성하지 않았다.  
그렇기에 계속해서 테스트관련 공부를 해보자라고 생각을 했었는데, Java 스터디를 진행하면서 **JUnit 5**에 대해 공부하게 되어 굉장히 다행이라고 생각한다.  
밑에 할 정리는 [Junit 5 공식 문서](https://junit.org/junit5/docs/current/user-guide/#overview)를 보고 함을 미리 알린다.

<br>

## What is JUnit 5?

JUnit 5란 무엇일까? 우선 JUnit 문서를 살펴보자.  
문서에서는 **JUnit 5는 크게 세가지 서브 프로젝트의 여러 모듈로 구성**이 되어있다고 한다.

```
JUnit 5 = JUnit Platform + JUnit Jupiter + JUnit Vintage
```

그렇다면 이 세가지 서브 프로젝트에 대해 간단히만 짚고 넘어가자.

* Platform

  > JVM에서 테스트를 하기 위한 기초적인 역할을 담당한다.
  >
  > * 테스트를 하기 위한 TestEngine API 제공
  > * JUnit 4(하위 버전) 기반 테스트 제공

* Jupiter

  > JUnit 5에서 테스트 및 확장하기 위한 프로그래밍 모델과 확장 모델의 조합이다.  
  > Platform에서 사용하는 TestEngine은 Jupiter를 통해 제공하는 것이다.

* Vintage

  > 하위 버전들(JUnit 3/4) 기반의 테스트를 실행시기키 위해 해당 TestEngine을 제공한다.

문서 설명을 보면 Platform이 가장 상단에서 개발자들에게 JUnit의 전반적인 테스트 API들을 제공하며, 이를 실제적으로 구현한 Jupiter와 Vintage가 존재한다라고 할 수 있을것 같다.

JUnit 5를 사용하기 위해서는 런타임에 Java 8 이상이 필요하나, 이전 버전의 JDK로 컴파일 된 코드들 또한 테스트가 가능하다.

<br>

## Annotations

Jupiter에서는 테스트와 확장된 기능을 제공하기 위해 여러가지 어노테이션들을 제공한다.

| Annotaion              | 설명                                                         |
| ---------------------- | ------------------------------------------------------------ |
| @Test                  | 테스트 메소드임을 알림<br />Junit 4와는 다르게 속성을 정의 X<br />이는 Jupiter에선 이미 해당 어노테이션들이 존재하기 때문 |
| @ParameterizedTest     | 여러가지 매개변수를 통해 다양한 테스트 진행                  |
| @RepeatedTest          | 반복 횟수만큼 테스트를 진행                                  |
| @TestFactory           | 동적 테스트를 위한 테스트 팩토리                             |
| @TestTemplate          | 일반 테스트가 아닌 테스트 케이스의 템플릿                    |
| @TestMethodOrder       | 테스트 메서드의 실행 순서를 구성하는데 사용(Junit 4의 @FixMethodOrder와 유사) |
| @TestInstance          | 테스트 인스턴스 생명주기를 구성하는데 사용                   |
| @DisplayName           | 테스트 클래스 혹은 메소드에 대한 이름을 선언                 |
| @DisplayNameGeneration | 테스트 클래스에 대한 Display name generator를 선언           |
| @BeforeEach            | 현재 클래스에서 @Test, @RepeatedTest, @ParameterizedTest, @TestFactory가 적힌 **각각의** 메소드들 보다 먼저 실행<br />(JUnit 4의 @Before와 동일) |
| @AfterEach             | 현재 클래스에서 @Test, @RepeatedTest, @ParameterizedTest, @TestFactory가 적힌 **각각의** 메소드들 보다 나중에 실행<br />(JUnit 4의 @After와 동일) |
| @BeforeAll             | 현재 클래스에서 @Test, @RepeatedTest, @ParameterizedTest, @TestFactory가 적힌 **모든**  메소드들 보다 먼저 실행<br />(JUnit 4의 @BeforeClass와 동일) |
| @AfterAll              | 현재 클래스에서 @Test, @RepeatedTest, @ParameterizedTest, @TestFactory가 적힌 **모든** 메소드들 보다 나중에 실행<br />(JUnit 4의 @AfterClass와 동일) |
| @Nested                | 중첩된 테스트 클래스임을 알림<br />각 클래스의 테스트 인스턴스 생명주기를 사용하지 않는 한 @BeforeAll과 @AfterAll 메소드는 사용할 수 X |
| @Tag                   | 테스트 필더링을 위한 테그를 선언하는데 사용                  |
| @Disabled              | 테스트 클래스 혹은 메소드를 비활성하는데 사용(JUnit 4의 @Ignore와 유사) |
| @Timeout               | 주어진 시간을 초과할 경우, 테스트 실패를 나타내기 위해 사용  |
| @ExtendWith            | 확장을 선언적으로 등록하는데 사용                            |
| @RegisterExtension     | 필드를 통해 프로그래밍 방식으로 확장을 등록하는데 사용       |
| @TempDir               | 필드 주입 또는 매개변수 주입을 통해 임시 디렉토리를 제공하는데 사용 |

<br>

## Assertions

Assertion을 프로그래밍 관점에서 해석하면 표명, 가정 설정문으로 할 수 있으며, 이를 통해 자신의 로직이 정확한지 테스트 해보는 것이다.  
Jupiter에서는 기존 버전에 존재했던 Assertion 메소드를 포함하고, 람다와 함께 사용하기 적합한 추가적인 메소드를 제공한다.  
모든 Assertion은 정적 메소드로 정의되어 있다.

```java
@Test
void testCalculator() {
  //기본
  assertEquals(2, calculator.add(1,1));
  assertEquals(5, calculator.mul(2,5), "실패 메시지(optional)");
  
  //그룹
  assertAll(() -> assertEquals(3, calculator.sub(5,2)),
            () -> assertEquals(1, claculator.div(5,5)));
  
	//의존
  assertAll(() -> {
             assertAll(() -> assertTrue(),
                       () -> assertTrue());
           },
           () -> {
             assertAll(() -> assertEquals(),
                       () -> assertEquals());
           });
  
  //예외
  Exception e = assertThrows(ArithmeticException.class, () -> calculator.div(1, 0));
  assertEquals("/ by zero", e.getMessage());
  
  //제한시간
  assertTimeout(ofMinutes(2), () -> {
    // 2분 미만의 로직만 통과
  });
}
```

위와 같이 제공되는 Assertion 외에도 여러가지 서드파티 라이브러리를 통해 Assertion도 가능하다.

<br>

## Assumptions

Assumption은 테스트를 진행할지 안할지에 대해 테스터가 작성한 가정을 기반으로 선택된다.  
즉, 설정한 가정이 참일 경우에는 테스트를 실행하고 거짓일 겅우에는 테스트는 실행하지 않는다.  
이러한 Assumption은 조그마한 단위 테스트보다는 통합 테스트에 더 적절히 사용할 수 있다.  
Assertion과 똑같이 정적 메소드로 정의되어 있다.

```java
@Test
void testOnlyOnDev() {
  assumeTrue("DEV".equals(System.getenv("ENV")));
  
  DEV 환경에서만 테스트할 코드;
}

@Test
void testAllEnv() {
  assumeTrue("DEV".equals(System.getenv("ENV")),
             () -> {
               DEV 환경에서 테스트할 코드;
             });

  모든 환경에서 테스트할 코드;
}
```

<br>

---

### Reference

* [Junit 5 공식 문서](https://junit.org/junit5/docs/current/user-guide/#overview)

