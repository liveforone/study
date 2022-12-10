# jpql
## @Query 사용
* 일반적인 sql문에 보다 객체 지향적인 쿼리 보장
## 예시
<pre>
@Query("select i from Item i where i.title like %:title% order by i.good desc")
List<Item> findByItemContaing(@Param("title") String title);
페이징은 Page로 like쿼리는 Containging으로 jp를 활용할 수 있지만 
jpql을 통해 쿼리를 짠다면 위의 코드처럼 짜면된다.
</pre>

## 간단 정리
* like 쿼리는 %키워드% 
* order by 무엇 desc(or asc) 
