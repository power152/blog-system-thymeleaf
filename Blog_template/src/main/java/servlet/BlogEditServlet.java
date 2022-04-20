package servlet;

import model.Blog;
import model.BlogDao;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * 1、先判定用户是否是登录的状态，如未登录跳转到登录页面
 * 2、从请求中读取博客标题和正文，并且进行校验
 * 3、构造一个Blog对象，插入到数据库中
 * 4、跳转到博客列表
 */


@WebServlet("/blog_edit")
public class BlogEditServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf8");
        resp.setContentType("text/html;charset=utf8");
        // 1.
        User user = Util.checkLogin(req);
        if (user == null) {
            // 当前用户未登录，跳转到登录页面
            resp.sendRedirect("blog_login.html");
            return;
        }
        // 2、
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        if (title == null || "".equals(title) || content == null || "".equals(content)) {
            String html = "<h3>标题或者正文缺失!</h3>";
            resp.getWriter().write(html);
            return;
        }
        // 3、
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setContent(content);
        // 设置博客的作者，博客是谁交的，作者就是谁，当前登录的用户，正式此人作者
        blog.setUserId(user.getUserId());
        // 设置博客的发布时间，发布时间就是现在
        blog.setPostTime(new Timestamp(System.currentTimeMillis()));

        BlogDao blogDao = new BlogDao();
        blogDao.insert(blog);

        // 4、
        resp.sendRedirect("blog_list.html");
    }
}
