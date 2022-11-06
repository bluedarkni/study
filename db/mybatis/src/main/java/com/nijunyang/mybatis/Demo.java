package com.nijunyang.mybatis;

import com.nijunyang.mybatis.mapper.StudentMapper;
import com.nijunyang.mybatis.mapper.UserMapper;
import com.nijunyang.mybatis.model.Student;
import com.nijunyang.mybatis.model.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author: create by nijunyang
 * @date:2019/7/3
 */
public class Demo {

    @Test
    public void testManyToMany() throws Exception{
        String resource = "src/main/resources/mybatis-config-t.xml";
        InputStream inputStream = new FileInputStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession(true);
        StudentMapper studentMapper = session.getMapper(StudentMapper.class);
        List<Student> studentList = studentMapper.findStudentWithTeacher(1);
        System.out.println(studentList);

    }



    @Test
    public void main() throws IOException {
//        String resource = "mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
        String resource = "src/main/resources/mybatis-config.xml";
        InputStream inputStream = new FileInputStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession(true);
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = new User();
        user.setUserName("王五");
        user.setUserCard("5111292");
        user.setAge(17);
        user.setCity("成都");
        mapper.insertUser(user);

    }



}
