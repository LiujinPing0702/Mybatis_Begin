package test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import mapper.UserMapper;
import model.User;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InputStream in=null;
		SqlSessionFactory sessionFactory =null;
		SqlSession session=null;
		UserMapper userMapper=null;
		try {
			in = Resources.getResourceAsStream("xml/mybatis-config.xml");
			sessionFactory = new SqlSessionFactoryBuilder().build(in);
			session = sessionFactory.openSession();
			//采用原始的方式不需要编写Mapper接口
			//采用原始的方式查询单条数据
			User user = session.selectOne("mapper.UserMapper.findById", 1);//selectOne(映射文件+方法名,方法的参数);
			System.out.println("user[" + user.getId() + "," +user.getName() + "," +user.getAge()+"]");
			//采用原始的方式更新数据
			User userUpdate = new User(3,"小刘",35);
			session.update("mapper.UserMapper.update", userUpdate);
			
			//使用动态代理的方式（底层的实现还是采用原始的方式）
			//需要编写Mapper接口
			//底层的实现中是自动去寻找有没有namespace为UserMapper.class的全限定名(即Mapper.UserMapper)的mapper.xml文件,如果找到了该
			//	mapper文件，再去根据UserMapper.class接口下的方法名去自动生成session的原始方法(例如：selectOne、update、delete、insert等方法)
			//  所以在使用动态代理的方式的时候，要求Mapper.xml中的<mapper>便签里面的namespace为对应接口类的全限定名，并且接口里面的方法必须与
			//  mapper.xml文件中的MappedStatement对象的id保持相同(包括方法的参数和返回值都要相同)
			userMapper = session.getMapper(UserMapper.class);//UserMapper.class是Mapper接口
			
			//插入数据
			User userInsert = new User(8,"小六",26);
			userMapper.save(userInsert);
			
			//更新数据
			//User userUpdate = new User(3,"小刘",28);
			//userMapper.update(userUpdate);
			/*
			//删除数据
			User userDelete = new User(2,"小刘",25);
			userMapper.delete(userDelete);
			*/
			/*
			//查询所有数据
			List<User> userlist = userMapper.findAll();
			for (User user2 : userlist) {
				System.out.println("user[" + user2.getId() + "," +user2.getName() + "," +user2.getAge()+"]");
			}
			*/
			/*
			//查询指定id的数据
			User user = userMapper.findById(1);
			System.out.println("user[" + user.getId() + "," +user.getName() + "," +user.getAge()+"]");
			*/
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			session.commit();
			session.close();
		}
	}

}
