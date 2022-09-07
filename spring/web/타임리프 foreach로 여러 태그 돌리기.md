# div안에 a태그돌리기

<pre>
&lt;th:block th:each="post : ${topicList}"&gt;
    &lt;div class="list-group&gt;
        &lt;a th:href="@{'/user/topic/' + ${post.title}}" class="list-group-item list-group-item-action flex-column align-items-start"&gt;
            &lt;div class="d-flex w-100 justify-content-between"&gt;
                &lt;h5 class="mb-1"&gt;
                    &lt;span th:text="${post.title}" th:remove="tag"&gt;&lt;/span&gt;
                    &lt;small th:text="|좋아요 ${post.good}|"&gt;&lt;/small&gt;
                &lt;/h5&gt;
            &lt;/div&gt;
        &lt;/a&gt;
    &lt;/div&gt;
&lt;/th:block&gt;
</pre>

## 설명
* 타임리프 block을 사용하여서 for each를 돌려주면된다.
