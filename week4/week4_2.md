# LinkedList 구현하기

### 📖목표

> * LinkedList 공부하기
> * 정수를 저장하는 LinkedList 클래스 구현하기
>   * ListNode add(ListNode head, ListNode nodeToAdd, int position) 구현
>   * ListNode remove(ListNode head, int postionToRemove) 구현
>   * boolean contains(ListNode head, ListNode nodeToCheck) 구현

<br>

## 🎞LinkedList

연결 리스트란, 배열과 비슷하게 데이터들의 묶음을 나타낼 때 사용하며 데이터와 데이터들을 직접적으로 연결하므로써 연관된 데이터들을 묶는다. 즉, 배열은 연속적인 공간 안에 데이터를 저장하고 이들의 위치를 통해 데이터에 접근했다면, 연결 리스트는 하나의 노드 안에 데이터와 그 다음 노드를 가리키는 주소 값을 가지고 있어 다음 노드로 탐색이 가능하다.

<img src="image/linked_list.png">

<p align="center" style="font-weight:bold">[ 연결 리스트 ]</p>

위 그림과 같이 데이터들을 사슬로 연결한 것과 같이 나타낼 수 있다. Data + Next(다음 노드 위치)를 하나로 묶고 이를 Node라고 한다. 즉, 하나의 노드에서 다음 노드의 위치를 또 그 다음 노드의 위치를 표시하여 꼬리에 꼬리를 무는 형태이다.

### 장점/단점

연결 리스트의 큰 장점은 메모리를 미리 할당하지 않아도 된다는 점이다. 즉, 크기를 늘리고 줄이는 부가적인 처리를 하지 않아도 되며, 데이터의 삽입과 삭제시에 `O(1)`이라는 시간에 가능하다. 반대로 임의 접근(Random Access)가 불가능하여 데이터를 검색하는데 `O(n)`이란 시간이 걸리게 된다. 이렇게 연결 리스트만의 장점이 존재하며, 삽입과 삭제가 많은 상황이나 전제적인 데이터의 크기를 파악할 수 없을 때 용이하게 사용할 수 있다.

### 종류

이러한 연결 리스트는 노드를 어떻게 구성하느냐에 따라 여러가지 형태로 만들 수 있다.

* 단순 연결 리스트

  > 위 그림과 같이 노드에서 다음 노드의 위치만 저장하는 형태

* 원형 연결 리스트

  > 마지막 노드의 다음 노드 위치를 `null`이 아닌 맨 처음 노드를 가리키게 하여 원형의 형태

  <img src="image/circular_linked_list.png" width="700px">

* 이중 연결 리스트

  > 하나의 노드에 이전 노드와 다음 노드의 위치를 둘 다 저장하는 형태  
  > 단순 연결 리스트보다 탐색에 조금 더 자유로움

  <img src="image/doubly_linked_list.png" width="700px">

<br>

## 구현

