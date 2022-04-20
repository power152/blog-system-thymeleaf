package servlet;

import model.Blog;
import model.BlogDao;
import model.User;
import model.UserDao;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/blog_detail.html")
public class BlogDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf8");
        User user = Util.checkLogin(req);
        if (user == null) {
            resp.sendRedirect("blog_login.html");
        }
        // 1、根据请求url的参数，从数据库中查询指出的博客内容
        String blogId = req.getParameter("blogId");
        if (blogId == null || "".equals(blogId)) {
            String html = "<h3>blogId 为空</h3>";
            resp.getWriter().write(html);
            return;
        }
        BlogDao blogDao = new BlogDao();
        // 转换成整数，因为数据库中selectOne的参数是int类型
        Blog blog = blogDao.selectOne(Integer.parseInt(blogId));
        if (blog == null || "".equals(blog)) {
            String html = "<h3>博客详情 为空</h3>";
            resp.getWriter().write(html);
            return;
        }

        // 在这里, 需要根据当前博客的作者 id 查询出对应的作者的详细身份.
        // 然后把作者的身份信息给渲染到模板中.
        UserDao userDao = new UserDao();
        User author = userDao.selectById(blog.getUserId());
        if (author == null) {
            String html = "<h3>指定的博客作者不存在</h3>";
            resp.getWriter().write(html);
            return;
        }


        // 2、这一步就是要基于模板引擎了，把博客数据给构造成博客详情页
        //    但是有个问题，就是 Thymeleaf 这个模板会用很多次，不能每一次都初始化，这样会浪费内存空间，所以我们可以常见一个监听器把 Thymeleaf 给放入监听器里面

        // [1] 这里就要取出之前监听器里面初始化好的 ServletContext 中把 engine 给取出来
        ServletContext context = getServletContext();
        TemplateEngine engine = (TemplateEngine) context.getAttribute("engine");

        // Thymeleaf中的对象，键值对结构，取得上下文模板渲染
        WebContext webContext = new WebContext(req, resp, context);
        // 这里是动态变化的信息，也是键值对的结构，插入的是什么，动态的就是什么
        webContext.setVariable("blog", blog);
        // 判断用户是否和作者是同一个
        webContext.setVariable("showDeleteButton",user.getUserId() == author.getUserId());
        webContext.setVariable("user", author);
        engine.process("blog_detail", webContext, resp.getWriter());


    }
}
