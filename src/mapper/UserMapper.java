package mapper;

import java.util.List;

import model.User;

public interface UserMapper {
	void save(User user);
	void update(User user);
	void delete(User user);
	User findById(int id);
	List<User> findAll();
}
