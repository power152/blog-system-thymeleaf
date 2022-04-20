package servlet;

import model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Util {
    public static User checkLogin(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) {
            //未登录状态
            return null;
        }
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // 未登录状态
            return null;
        }
        return user;
    }
}
