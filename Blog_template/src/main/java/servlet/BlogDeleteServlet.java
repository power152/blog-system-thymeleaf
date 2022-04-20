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

/**
 * 1. 先验证用户是否是登录状态. 如果未登录, 还是先跳转到登录页面.
 * 2. 从请求中读取要删除的博客 id
 * 3. 读出博客 id 之后, 去数据库里查一下, 看看这个博客是不是存在.
 * 4. 如果博客存在, 再进行一次校验, 校验当前登录的用户是否就是这个博客的作者
 * 5. 真正执行删除. 删除数据库中的内容.
 * 6. 跳转到博客列表页.
 */

@WebServlet("/blog_delete")
public class BlogDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf8");
        // 1、
        User user = Util.checkLogin(req);
        if (user == null) {
            resp.sendRedirect("blog_login.html");
            return;
        }
        // 2、从请求中读取要删除的博客 id
        String blogId =req.getParameter("blogId");
        if (blogId == null || "".equals(blogId)) {
            String html  = "<h3>要删除的博客id缺失</h3>";
            resp.getWriter().write(html);
            return;
        }

        // 3. 读出博客 id 之后, 去数据库里查一下, 看看这个博客是不是存在.
        BlogDao blogDao = new BlogDao();
        // selectOne 是 int 类型
        Blog blog = blogDao.selectOne(Integer.parseInt(blogId));
        if (blog == null || "".equals(blog)) {
            String html = "<h3>要删除的博客不存在</h3>";
            resp.getWriter().write(html);
            return;
        }
        // 4. 如果博客存在, 再进行一次校验, 校验当前登录的用户是否就是这个博客的作者
        //  这步操作是真滴有必要，double check
        if (user.getUserId() != blog.getUserId()) {
            String html = "<h3>你不能删除别人博客</h3>";
            resp.getWriter().write(html);
            return;
        }
        // 5. 真正执行删除. 删除数据库中的内容.
        blogDao.delete(Integer.parseInt(blogId));
        //6. 跳转到博客列表页.
        resp.sendRedirect("blog_list.html");
    }
}
