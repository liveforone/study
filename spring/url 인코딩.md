# url 인코딩

## why? 인코딩 해야하는가
* url을 한글로 넘기는 경우가 많이 발생한다.
* ex : /user/category/뉴스/1
* 이렇게 한글을 넘기게 되면 redirect같은 것을 할때 제대로 적용이되지않는 문제가 발생한다.

## 해결법
<pre>
String URL = "/user/";
String subURL = "한글값";
subURL =  URLEncoder.encode(subURL, "UTF-8");
URL = URL + subURL;
</pre>
