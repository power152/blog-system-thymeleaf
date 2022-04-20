package servlet;

import model.Blog;
import model.BlogDao;
import model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

// 此处写的 ServletPath 是可以带后缀的，加了 .html ，前端可以不用关注页面是动态的，还是静态的了
@WebServlet("/blog_list.html")
public class BlogListServlet extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf8");
        User user = Util.checkLogin(req);
        if (user == null) {
            resp.sendRedirect("blog_login.html");
        }

        // 1、先从数据库中查询博客列表
        BlogDao blogDao = new BlogDao();
        List<Blog> blogs = blogDao.selectAll();
        // 2、通过模板引擎进行渲染操作
        TemplateEngine engine = (TemplateEngine) getServletContext().getAttribute("engine");
        WebContext webContext = new WebContext(req, resp, getServletContext());
        webContext.setVariable("blogs", blogs);
        // 在这里再加上一个 "user" 这样的替换~
        webContext.setVariable("user", user);
        engine.process("blog_list", webContext, resp.getWriter());
    }
}
