# Enum

### 📖 목표

> 자바 열거형에 대해 학습하여라.

<br>

## enum이란?

열거형이라고 불리우는 Enumeration(Enumerated type)은 **일반적으로 상수 역할을 하는 식별자로 서로 관련된 상수들의 집합**이라고 생각하면 된다. 자바에서는 Enum 클래스는 JDK 1.5 부터 추가가 되어 단순히 타입만 정의하는 C언어와는 다르게 타입 비교까지 할 수 있다.

### 정의

Enum 클래스를 정의하는 방법은 class 키워드 대신에 enum 키워드를 사용하면 된다.

```java
public enum Country {
	KOREA,
  USA,
  JAPAN,
  CHINA
}
```

### 사용과 특징

위와 같이 간단하게 설정하면 상수들을 사용가능 하며, 해당 상수는 `Country.KOREA`으로 접근이 가능하다. 바로 스트링 형태로 사용이 가능한데 이는 바이트 코드를 보면 알 수 있다.

```java
// 바이트 코드의 일부
1: public final static enum Lcom/company/Country; KOREA
2:
3: static <clinit>()V
4:  L0
5:    LINENUMBER 12 L0
6:    NEW com/company/Country
7:    DUP
8:    LDC "KOREA"
9:    ICONST_0
10:   INVOKESPECIAL com/company/Country.<init> (Ljava/lang/String;I)V
11:   PUTSTATIC com/company/Country.KOREA : Lcom/company/Country;
```

기본적으로 Enum 클래스의 멤버로 설정한 값은 전부 다 `final static` 키워드가 붙게 된다. 그렇기 때문에 `static` 키워드를 사용하지 않아도 바로 접근이 가능한 것이다.

또, 8번째 줄을 보면 상수 풀에서 `"KOREA"`라는 문자열을 가져와 로드하는 것과 10번째 줄에서는 `String` 클래스를 호출하고 있기 때문에 값을 갖지 않는 상수명은 자신의 이름과 동일한 문자열을 값으로 가지게 된다.

```java
private <init>(Ljava/lang/String;I)V
```

생성자의 접근 제어자는 `private`이기 때문에 접근이 불가능하며 생성자를 정의하여도 `public`으로는 변경이 불가능하다.

그렇다면 Enum 클래스에서 상수에 값을 넣게 된다면 바이트 코드는 어떻게 변하게 될까?

```java
enum Country {
    KOREA("KOR"),
    USA("USA"),
    JAPAN("JPN"),
    CHINA("CHN");

    String CODE;
    Country(String CODE) {
        this.CODE = CODE;
    }
}

LINENUMBER 12 L0
NEW com/company/Country
DUP
LDC "KOREA"
ICONST_0
LDC "KOR"
INVOKESPECIAL com/company/Country.<init> (Ljava/lang/String;ILjava/lang/String;)V
PUTSTATIC com/company/Country.KOREA : Lcom/company/Country;
```

`CODE`로 지정한 문자열이 로드된 것을 볼 수 있다. 그리고 `CODE`는 `Country.KOREA.CODE` 로 접근이 가능하다. 안에 있는 값은 계속해서 늘려갈 수 있다.

### 왜 사용될까?

JDK 1.5 이전에는 Enum 클래스가 존재하지 않았고, Enum 패턴이 존재했었다. Enum 패턴을 잠깐 살펴보면 다음과 같다.

```java
public static final int SEASON_SPRING = 0;
public static final int SEASON_SUMMER = 1;
public static final int SEASON_FALL = 2;
public static final int SEASON_WINTER = 3;
```

이러한 패턴에는 몇가지 문제가 존재한다.

* Not typesafe

  > 위와 같이 정수로만 존재하는 SEASON들은 다른 타입이여도 같은 정수로 설정된 다른 상수와 같을 수 있다.  
  >
  > ```java
  > public static final int DAY_MONTH = 0;	// 이와 같은 상수가 존재
  > 
  > SEASON_SPRING == DAY_MONTH // 다음과 같은 조건식이 존재할 때 true를 반환
  > ```

* No namespace

  > 다른 Enum 패턴들과 충돌을 피하기 위해 SEASON과 같은 접두사를 붙혀야 한다.

* Brittleness(취성)

  > int형 enum의 경우 컴파일 타임 상수이기 때문에 변경된다면 다시 컴파일을 해야 된다.

* Printed values are uninformative

  > Enum 패턴으로만 정의한다면 단순 정수형 타입이기 때문에 얻을 수 있는 정보는 단순 정수 값 밖에 없다.(타입 정보 X)

이러한 이유들로 자바에서는 Enum 클래스를 도입하게 되었다.

<br>

## values()와 valueOf()

바이트코드로 변환된 것을 보면 정의하지 않은 메소드 두개가 보인다.

```java
public static values()[Lcom/company/Country;
public static valueOf(Ljava/lang/String;)Lcom/company/Country;
```

바로 values() 와 valueOf() 메소드이다. 간단한 예제로 알아보도록 하자.

* values()

  > 해당 열거형에 존재하는 상수들을 배열 형태로 반환한다.
  >
  > ```java
  > Country[] countries = Country.values();
  > for(Country c : countries) {
  > 	System.out.println(c);
  > }
  > // ==출력== 
  > // KOREA 
  > // USA 
  > // JAPAN
  > // CHINA
  > ```

* valueOf(String name)

  > 특정 타입 이름을 파라미터로 넘기면 해당하는 상수를 반환한다.
  >
  > ```java
  > System.out.println(Country.valueOf("KOREA"));
  > // ==출력== 
  > // KOREA
  > ```

두 메소드의 코드는 어디서 뿅하고 나타났을까?

바로 `java.lang.Enum` 이라는 추상 클래스에서 답을 찾을 수 있다. 우리가 정의한 Enum들은 모두 이 추상 클래스를 상속하여 사용하게 된다. 따라서 정의한 Enum은 다른  클래스를 상속 받을 수 없다. 근데 신기한 점은 `valueOf()`는 정의되어 있으나, `values()` 메소드는 

```
All the constants of an enum type can be obtained by calling the implicit {@code public static T[] values()} method of that type.
```

다음과 같은 주석만 볼 수 있다. 그렇다면 `java.lang.Enum`에 대해 더욱 자세하게 알아보자.

<br>

## java.lang.Enum

Enum 클래스는 모든 Enum들의 상위 클래스로 `Comparable`과 `Serializable`을 구현하고 있다. 또한 **추상 클래스이기 때문에 new를 사용하여 인스턴스화 할 수 없다.**

### 필드

Enum 클래스 필드에는 `name`과 `ordinal`이 존재한다. 

* name 

  > 정의한 상수의 이름을 나타낸다(위 예제에서는 "KOREA"에 해당).

* ordinal

  > 정의한 상수의 순서를 나타내며, 이는 일반적인 이유로 사용되기 보다는 EnumSet과 EnumMap과 같은 특수한 데이터 구조에서 사용된다.

### 메소드

정의된 메소드는 다음과 같다.

* compareTo(E o)

  > 파라미터로 들어온 타입과 ordinal을 기준으로 비교한 값을 반환한다.(자신의 ordinal - 비교의 ordinal)

* equals(Object other)

  > 파라미터로 들어온 객체와 상수와 같으면 true를 반환한다.

* getDeclaringClass()

  > 해당 열거형에 해당하는 클래스를 반환한다.(위의 예제에서는 Country를 반환) 

* hashCode()

  > 상수의 해시 코드를 반환한다.

* name()

  > 선언된대로 상수의 이름을 반환한다.

* ordinal()

  > 상수의 선언된 순서를 반환하며, 이 순서는 0부터 시작한다.

* toString()

  > name() 메서드와 동일하게 상수의 이름을 반환하지만, 다른 것이 존재한다. name()는 final로 선언이 되어있기 때문에 재정의를 할 수 없다. 따라서 자신이 원하는 문자열로 반환하고 싶다면 toString() 메서드를 재정의하여 사용하는 것이 바람직하다.

* valueOf()

  > 위 설명 참고

<br>

## EnumSet

EnumSet은 **Set이란 자료구조에서 Enum 타입을 사용하기 위한 특수한 구현체**이다. EnumSet의 집합을 만들기 위해서는 명시/암시적으로 지정한 단일 Enum 타입에서 가져와야 한다(`Null`은 넣을 수 없음).

EnumSet의 큰 장점은 내부적으로 비트 벡터를 사용한다. 비트 벡터를 사용하므로써 메모리 사용량을 크게 줄일 수 있으며, HashSet보다 더 큰 퍼포먼스를 낼 수 있다.

이러한 EnumSet은 추상 클래스로 정의된 static 메서드를 통해 EnumSet을 반환해 사용할 수 있다. 왜 이렇게 만든 것일까? 직접 코드를 살펴보러 가보자.

### 내부

```java
public static <E extends Enum<E>> EnumSet<E> allOf(Class<E> elementType) {
	EnumSet<E> result = noneOf(elementType);
	result.addAll();
	return result;
}
```

해당 메소드는 Enum 클래스를 받아 이를 EnumSet으로 만들고 이를 반환한다. 여기서 `noneof()` 메서드를 봐야 더욱 자세하게 알것 같으니 이를 봐보자.

```java
public static <E extends Enum<E>> EnumSet<E> noneOf(Class<E> elementType) {
	Enum<?>[] universe = getUniverse(elementType);
	if (universe == null)
		throw new ClassCastException(elementType + " not an enum");
  if (universe.length <= 64)
		return new RegularEnumSet<>(elementType, universe);
	else
		return new JumboEnumSet<>(elementType, universe);
}
```

파라미터로 넘긴 Enum에서 `universe` 라는 값을 가져오고 이 길이를 기반으로 `RegularEnumSet` 과 `JumboEnumSet` 을 선택적으로 생성하여 반환하고 있는 구조이다.

즉, 개발자가 직접 선택하여 사용하는 것이 아닌 EnumSet에서 객체를 인스턴스화 한다. 이 덕분에 개발자는 내부 구조에 대해 깊게 보지 않아도 쉽게 EnumSet을 사용할 수 있다. 또, 내부 구조가 감춰져 있기에 내부 로직이 변경되도 기존에 사용하던 EnumSet의 로직은 바꾸지 않아도 된다.

<br>

## EnumMap

EnumMap은 EnumSet과 비슷하게 **Map이란 자료구조에서 키 값을 Enum 타입으로 사용하는 특수한 구현체**이다. EnumMap의 모든 키의 경우 생성될 때, 명시/암시적으로 지정된 단일 Enum 타입에서 가져와야 한다. 키의 순서는 Enum 타입 안에서 선언된 순서로 유지된다.

이전에 Enum에서 ordinal이라는 값으로 정의한 상수별로 순서를 미리 정해 놓을 수 있었다. **이 순서대로 기억만 하고 있으면 되므로 기존 HashMap에서 해싱을하고 충돌을 방지하는 작업을 하지 않아도 되며, TreeMap과 같이 순서를 정렬할 필요가 없기 때문에 다른 Map에 비해 더 높은 성능을 기대**할 수 있다.

<br>

<br>

---

### Reference

* [Enums - Java 1.5 Oracle doc](https://docs.oracle.com/javase/1.5.0/docs/guide/language/enums.html)
* [[JAVA] EnumMap 을 사용합시다.](https://www.manty.co.kr/bbs/detail/develop?id=61)
* [EnumMap(EnumSet) 쓰면 좋을까? (vs HashMap)](https://siyoon210.tistory.com/142)
* [EnumSet이 new 연산자를 사용하지 않는 이유](https://siyoon210.tistory.com/152)
* [java.lang.Enum - java 11 doc](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Enum.html)
* [java.lang.EnumSet - java 11 doc](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/EnumSet.html)
* [java.lang.EnumMap - java 11 doc](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/EnumMap.html)

