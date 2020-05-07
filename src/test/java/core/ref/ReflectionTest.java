package core.ref;

import next.model.Question;
import next.model.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());

        Field[] fields = clazz.getDeclaredFields();
        Constructor[] constructors = clazz.getConstructors();
        Method[] methods = clazz.getMethods();

        for (Field field : fields) {
            logger.debug("declared Field : {}", field.getName());
        }

        for (Constructor constructor : constructors) {
            logger.debug("declared Constructor : {}", constructor);
        }

        for (Method method : methods) {
            logger.debug("declared Method : {}", method);
        }
    }

    @Test
    public void newInstanceWithConstructorArgs() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());

        Constructor[] constructors = clazz.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            User user = (User) constructor.newInstance("bkh", "1234", "bang", "kkk@kkk.k");
            logger.debug("userId : {}, password : {}, name : {}, email : {}", user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
        }
    }

    @Test
    public void privateFieldAccess() throws Exception {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        Student student = new Student();
        Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);
        name.set(student, "Bang");

        Field age = clazz.getDeclaredField("age");
        age.setAccessible(true);
        age.setInt(student, 26);

        logger.debug("name : {}, age : {}", student.getName(), student.getAge());
    }
}
