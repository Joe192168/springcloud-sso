1、授权码模式url
localhost:8082/oauth/authorize?client_id=c1&response_type=code&scope=all&redirect_uri=https://www.baidu.com
2、简化模式url
localhost:8082/oauth/authorize?client_id=c1&response_type=token&scope=all&redirect_uri=https://www.baidu.com
3、密码模式url
localhost:8082/oauth/authorize?client_id=c1&client_secret=123&grant_type=password&username=admin&password=123
4、客户端模式url
localhost:8082/oauth/authorize?client_id=c1&client_secret=123&grant_type=client_credentials


测试
1、申请token请求方式：POST
localhost:8082/oauth/token?username=admin&password=123&grant_type=password&scopes=ROLE_API&client_id=c1&client_secret=123
2、校验token请求方式：POST
http://localhost:8082/oauth/check_token
body 参数名：token = xxx
3、无token请求 请求方式：GET
http://localhost:8081/order/noauth
4、head带上Authorization token Bearer 请求 请求方式：GET
http://localhost:8081/order/r1

