# set
```
Set<T> set = new HashSet<>();
set.add();
set.contains();
headSet(value) // value 보다 작은 요소들을 집합으로 반환
tailSet(value) // 지정된 요소를 포함하여 큰 요소들을 반환
subSet(A, B) // A를 포함하여 A보다 크고 B보다 작은 요소들을 반환
```

# iteratior
```
Iterator<String> it= set.iterator();
while(it.hasNext()){
	String a= it.next();
	System.out.println(a);
}
```
