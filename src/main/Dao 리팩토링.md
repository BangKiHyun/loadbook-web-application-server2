## Dao 리팩토링

### JDBC 코드의 중복 제거하기

- 중복 코드를 리펙토링하기 전 분리 작업 실시
- 변화가 발생하는 부분(개발자가 구현할 수 밖에 없는 부분)과
  변화가 없는 부분(공통 라이브러리로 분리할 부분)을 분리하자

| 작업             | 공통 라이브러리 | 개발자가 구현할 부분 |
| :--------------- | :-------------: | :------------------: |
| Connection 관리  |        O        |          X           |
| SQL              |        X        |          O           |
| Statement 관리   |        O        |          X           |
| ResultSet 관리   |        O        |          X           |
| Row 데이터 추출  |        X        |          O           |
| 파라미터 선언    |        X        |          O           |
| 파라미터 Setting |        O        |          X           |
| 트랜잭션 관리    |        O        |          X           |



### 리팩토링

1. Insert, Update 쿼리의 중복 제거 작업 실행
   변하는 부분과 변하지 않는 부분을 Extract Method 리팩토링을 통해 분리한다.
   - insert(User) : void
   - setValuesForInsert(User, PreparedStatement) : void
   - createQueryForInsert(User, PreparedStatement) : String
   - update(User) : void
   - setValuesForUpdate0() : void
   - createQueryForUpdate() : String
   - findAll() : List<User>
   - findByUserId(String) User

2. 분리한 메소드 중에서 변화가 발생하지 않는 부분(공통 라이브러리)을 새로운 클래스로 추가한 후 이동한다.
   - InsertJdbcTemplate 클래스
     - insert(User, UserDao) : void
   - UpdateJdbcTemplate 클래스
     - update(User, UserDao) : void

3. InsertJdbcTemplate과 UpdateJdbcTemplate이 UserDao에 대한 의존관계를 끊는다.
   - setValuesForInsert()와 createQueryForInsert()를 추상 메소드로 구현하고
     UserDao의 insert() 메소드에서 이 2개의 추상 메소드를 구현하도록 한다.
   - insert() 메소드에서 *익명 클래스로 구현하도록 한다.

4. InsertJdbcTemplate과 UpdateJdbcTemplate의 구현 부분중 하나를 사용하도록 리펙토링한다.
   - setValues()와 createQuery() 메소드로 Rename 리팩토링한다.
   - 두 클래스는 같기 때문에 둘 중 하나를 삭제하고 클래스 하나만 사용하나
   - JdbcTemplate.class
     - excute(User) : void
     - setValues(User, PreparedStatement)
     - createQuery()

5. JdbcTemplate은 User와 의존관계를 가지기 때문에 다른 DAO 클래스에서 재사용할 수 없다.
   User와 의존관계를 끊는다.
   - User 값은 UserDao에서만 사용되기 때문에 SQL 쿼리와 같이 변경되는 부분을
     추상 메소드가 아닌 메소드의 인자로 전달한다.
   - JdbcTemplate.class
     - excute(String) :  void
     - setValues(PreparedStatement) : void

6. JdbcTemplate와 같은 방법으로 SelectJdbcTemplate을 생성해 반복 코드 분리한다.
   - SelectJdbcTemplate.class
     - query(String) : List
     - queryForObject(String) : Object
     - setValues(PreparedStatement) : void
     - mapRow(ResultSet) : Object
   - findAll() 메소드에서 setValues(PreparedStatement) 는 사용할 필요가 없다.
     위와 같은 상황이 빈번히 발생하게 된다면 분리를 하겠지만
     한 두번 일어나는 상황이라면 throw new UnsupportedOperationException() 예외를 던져 사용하지 못하게 할 것 같다.(이건 그냥 내 의견)

7. JdbcTemplate과 SelectJdbcTemplate의 중복 코드를 한 클래스로 통합하여 중복 제거하기
   - JdbcTemplate.class
     - excute(String) : void
     - query(String) : List
     - queryForObject(String) : Object
     - mapRow(ResultSet) : Object
     - setValues(PreparedStatemnent) : void

8. 7번과 같이 했을때의 문제점
   - 불필요한 mapRow() 와 setValues() 메소드를 반드시 구현하는 부분이 있다.
     2개의 메소드를 분리해 독립적으로 전달할 수 있도록 만들자
   - PreparedStatementSetter(), RowMapper() 인터페이스를 추가해서 구현해라

9. SQLException을 RuntimeException으로 변환해 throw하도록 한다.
   Connection, PreparedStatement 자원 반납을 close() 메소드를 사용하지 말고 *try-with-resources 구문을 적용해 해결한디.

   - RuntimeException을 상속하는 커스텀Exception(DataAccessException)과 같은 이름)을 추가한 후 SQLException을 새로 추가한 커스텀Exception을 반환해 throw 하도록 구현
   - try-with-resources문법을 적용해 finally 절의 복잡도를 낮추도록 리팩토링한다.

10. SELECT문의 경우 조회한 데이터를 Casting하는 부분이 있다. Casting하지 않고 구현하도록 개선한다.

    - Java의 *Generic을 적용해 공통 라이브러리를 개선한다.

      ```
      public interface RowMapper<T> {
          T maprRow(ResulSet rs) throws SQLException;
      }
      ```



### 익명 클래스(Anonymous Class)

- 클래스의 선언과 객체의 생성을 동시에 하기 때문에 단 한 번만 사용될 수 있고, 오직 하나의 객체만 생성할 수 있는 **일회용 클래스**
  즉, 인터페이스를 구현하기 위해 해당 인터페이스를 구현한 **클래스를 생성**할 때 **일회성이고 재사용할 필요가 없을때 사용**한다.
- 이름이 없기 때문에 생성자를 가질수 없다.
- 단 하나의 클래스를 상속받거나 단 하나의 인터페이스만을 구현할 수 있다.
- 하나의 클래스로 상속받는 동시에 인터페이스를 구현하거나 둘 이상의 인터페이스를 구현할 수 없다.

```
interface Test{
    public void go();
}

public class sampleClass{
	Test anonymousTest = new Test(){
        public void go(){
            System.out.println("Anonymous Class!");
        }
    };
    test.go();
}
```



## try-with-resouces

- ,try-with-resouces 문으로 관리되는 객체일 때 close() 메소드가 자동 호출된다.
  리소스를 닫고 기본 리소스를 양도한다.
- close메소드 구현시 구체적인 exception을 throw하고, close동작이 실패할리 없을때는 exception을 던지지 않도록 구현하는 것을 권고한다.

### 사용방법

- try 블록의 소괄호"()" 안에서 close() 메소드 호출이 필요한 객체를 할당해 주면 된다.
- try catch절이 종료되면 객체의 close() 메소드가 자동으로 호출된다.



## Generic 타입

- 일반적인 코드를 작성하고, 이코드를 다양한 타입의 객체에 대하여 재사용하는 프로그래밍 기법
- 클래스에서 사용할 타입을 클래스 외부에서 설정하는 타입
  즉, **해당 객체를 생성할 때 사용할 객체의 타입이 정해진다.**
- T는 Generic 변수를 의미한다.

