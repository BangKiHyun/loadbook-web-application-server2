## 요구사항

#### MVC 패턴을 지원하는 프레임워크 구현하기

- MVC 패터은 기본적으로 사용자의 최초 진입 지점이 Controller가 된다.
- View에 직접 접근하는 것을 막고 항상 Controller를 통해 접근하도록 해야 한다.
- 현재 코드는 회원가입(/user/form.jsp), 로그인(/user/login.jsp)과 같은 JSP로 직접 접근하고 있다.
- 이를 수정하라



#### 구현 방식

1. 모든 요청을 하나의 서블릿이 받은 후 요청 URL에 따라 분기 처리하는 방식으로 구현
2. 클라이언트 요청은 먼저 DispatcherServlet이 받은 후
   요청 URL에 따라 해당 Controller에 작업을 위엄하도록 구현
3. css, js, 이미지와 같은 정적인 자원은 굳이 Controller가 필요가 없다.
   *서블릿 필터는 ResourceFilter에 구현되어 있다.



#### 힌트

- 모든 요청을 하나의 서블릿(DispatcherServlet)이 받을 수 있도록 URL 매핑한다.
- Controller 인터페이스를 추가한다.
  반환값이 String인 메소드가 필요하다.
- RequestMapping 클래스를 추가해 요청 URL과 컨트롤러 매핑을 설정한다.





#### 서블릿 필터

- 필터는 데이터를 가로채서 처리를 한다고 생각하면 된다.
- 하나의 작업에서 다른 작업으로 넘어갈 때나 어떤 작업이 또 다른 작업으로 넘어갈 때 데이터를 가로채서 처리할 수 있다. 
- 필터 클래스의 메서드는 요청 객체(Request)와 응답 객체(Response)를 매개변수로 가진다.
- 추가적으로 FillterChain 객체를 매개 변수 갖는데, 이유는 필터 기능 자체가 페이지의 분기점에 있기 때문이다
  따라서, FilterChain 객체는 필터 기능이 완료되고 다음 페이지로 연결되는 기능에 사용된다.

#### 주요 메소드

init(FilterCOnfig)

- 필터 객체가 생성될 때 호출되는 메소드이다.
- 웹 서버가 시작될 때 생성되어 한번만 호출된다. 그래서 주로 초기화 기능을 구현하는데 사용한다.

doFilter(HttpServletRequeset req, HttpServletResponse resp)

- doFilter() 메소드는 필터링 설정한 서블릿을 실행할 때 마다 호출되는 메소드로서 실제 필터링 기능을 구현한다.

destroy()

- 필터 객체가 삭제 될 때 호출되는 메소드로 주로 자원 해제 기능을 구현한다.