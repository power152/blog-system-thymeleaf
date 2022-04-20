package servlet;

import model.User;
import model.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 实现登录页面，基本逻辑都差不多
 * 1、先从请求中获取到usrname 和 password
 * 2、对用户名和密码进行校验
 * 3、判定是否登录成功
 * 4、创建临时会话，保存用户信息
 * 5、重定向，跳转指定页面（博客列表页）
 */



@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //
        req.setCharacterEncoding("utf8");
        resp.setContentType("text/html;charset=utf8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (username == null || "".equals(username) || password == null || "".equals(password)) {
            resp.getWriter().write("<h3>用户名或者密码为空</h3>");
            return;
        }
        // 登录成功
        // 读取数据库，根据用户名查询该用户在数据库中真实的密码是啥，看看用户提交的密码和真实密码是否一致
        UserDao userDao = new UserDao();
        User user = userDao.selectByName(username);

        if (user == null) {
            // 用户名不存在
            resp.getWriter().write("<h3>用户名或者密码不正确</h3>");
            return;
        }
        if(!user.getPassword().equals(password)) {
            resp.getWriter().write("<h3>用户名或者密码不正确!</h3>");
            return;
        }
        // 登录成功了
        // 创建临时会话，Session，保存用户信息
        HttpSession session = req.getSession(true);
        session.setAttribute("user",user);

        // 跳转列表页面，重定向,路径
        resp.sendRedirect("blog_list.html");

    }
}
