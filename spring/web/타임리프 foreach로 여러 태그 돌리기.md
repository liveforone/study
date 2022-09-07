# div안에 a태그돌리기

<pre>
<th:block th:each="post : ${topicList}">
    <div class="list-group">
        <a th:href="@{'/user/topic/' + ${post.title}}" class="list-group-item list-group-item-action flex-column align-items-start">
            <div class="d-flex w-100 justify-content-between">
                <h5 class="mb-1">
                    <span th:text="${post.title}" th:remove="tag"></span>
                    <small th:text="|좋아요 ${post.good}|"></small>
                </h5>
            </div>
        </a>
    </div>
</th:block>
</pre>
