package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// 这个方法是用来真对blog表进行 增删查改 的操作
// 这里暂时不考虑修改
// 纯纯的jdbc操作
public class BlogDao {

    // 1、新增加博客
    public void insert(Blog blog) {
        // 放在外面方便DBUtil关闭资源
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // 1、建立数据库的连接
            connection = DBUtil.getConnection();
            // 2、构造sql语句
            String sql = "insert into blog values(null,?,?,?,?)";
            statement = connection.prepareStatement(sql);

            statement.setString(1, blog.getTitle());
            statement.setString(2, blog.getContent());
            statement.setTimestamp(3, blog.getPostTime());
            statement.setInt(4, blog.getUserId());

            // 3、执行sql
            int ret = statement.executeUpdate();
            if (ret != 1) {
                System.out.println("插入博客失败");
            } else {
                System.out.println("插入博客成功");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 4、关闭资源
            DBUtil.close(connection, statement, null);
        }
    }


    // 2、查找博客列表 => 博客列表页
    public List<Blog> selectAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Blog> blogs = new ArrayList<>();
        try {
            connection = DBUtil.getConnection();
            String sql = "select * from blog order by postTime desc";
            statement = connection.prepareStatement(sql);
            // 执行sql
            // 执行完的结果是包含一组结果集合，是临时集合表，所以需要些额外的代码，获取到临时表里面 需要用到 ResultSet;
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Blog blog = new Blog();
                // 从blogId取出一个int值赋值给SetBlogID里面
                blog.setBlogId(resultSet.getInt("blogId"));
                blog.setTitle(resultSet.getString("title"));
                String content = resultSet.getString("content");
                if (content.length() > 60) {
                    content = content.substring(0,60) +"...";
                }
                blog.setContent(content);
                blog.setPostTime(resultSet.getTimestamp("postTime"));
                blog.setUserId(resultSet.getInt("userId"));
                blogs.add(blog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, resultSet);
        }
        return blogs;
    }
    // 3、查找博客详情 => 博客详情页
    public Blog selectOne(int blogId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "select * from blog where blogId = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1,blogId);
            resultSet = statement.executeQuery();

            // 由于此处是按照 blogId 查找, 并且 blogId 是自增主键(不能重复), 此时的查找结果, 要么是一个记录, 要么是空的结果, 不存在多条记录的可能
            // 既然如此, 此处就不必写循环了
            if (resultSet.next()) {
                Blog blog = new Blog();
                blog.setBlogId(resultSet.getInt("blogId"));
                blog.setTitle(resultSet.getString("title"));
                blog.setContent(resultSet.getString("content"));
                blog.setPostTime(resultSet.getTimestamp("postTime"));
                blog.setUserId(resultSet.getInt("userId"));

                return blog;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection,statement,resultSet);
        }
        return null;
    }

    // 4、删除一个博客
    public void delete(int blogId) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "delete from blog where blogId= ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1,blogId);
            int ret = statement.executeUpdate();
            if (ret != 1) {
                System.out.println("删除失败");
            } else {
                System.out.println("删除成功");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection,statement,null);
        }
    }



    public static void main(String[] args) {
        BlogDao blogDao = new BlogDao();
        // 进行单元测试，所谓的单元测试就是针对一个类/方法进行单元测试

        // 4、测试删除操作
//        blogDao.delete(4);

        // 3、查找详情页
//        Blog blog = blogDao.selectOne(3);
//        System.out.println(blog);

        // 2、查找博客列表
//        List<Blog> blogs = blogDao.selectAll();
//        System.out.println(blogs);


        // 1、插入测试
//        Blog blog = new Blog();
//        blog.setTitle("四级英语");
//        blog.setContent("be intested in.......");
//        blog.setPostTime(new Timestamp(System.currentTimeMillis()));
//        blog.setUserId(1);
//        blogDao.insert(blog);

    }

}
