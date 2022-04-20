package model;


import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 针对 user 表进行增删改查
// 新增用户, 属于 "注册" 需要涉及到的功能. 但是此处咱们并不实现注册功能
// 删除用户, 属于 "删号" 涉及到的功能. 此处咱们也不去实现.
// 修改用户, 属于用户 "个人信息" 的修改功能. 此处咱们也暂时不实现.
// 因此此处主要是实现查找功能.
public class UserDao {

    // 1. 根据用户 id 来查找用户详情 (根据博客表中的 userId 来找到作者的详细信息)
    public User selectById(int userId) {
        Connection connection =null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select * from user where userId = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1,userId);
            resultSet  = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;

            }
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection,statement,resultSet);

        }
        return null;
    }

    // 2. 根据用户名来查找用户详情 (在登录的时候, 就需要根据用户名来找到用户的密码, 进一步进行校验匹配)
    public User selectByName(String usetname) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "select * from user where username = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,usetname);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection,statement,resultSet);

        }
        return null;

    }


    public static void main(String[] args) {
        UserDao userDao = new UserDao();

        // 2、通过UserName 进行查找测试
        User user =userDao.selectByName("admin");
        System.out.println(user);


        // 1、通过userId进行查找测试
//        User user = userDao.selectById(1);
//        System.out.println(user);

    }
}
