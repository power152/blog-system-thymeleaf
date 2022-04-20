package servlet;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class ThymeleafListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // 1、先创建engine对象
        TemplateEngine engine = new TemplateEngine();

        // 2、初始化代码
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver(servletContextEvent.getServletContext());
        // 针对 resolver 对象来设置一些属性
        //     接下来要加载  /WEB-INF/template/ 目录中, 以 .html 结尾的文件, 作为模板引擎.
        resolver.setPrefix("/WEB-INF/template/");
        resolver.setSuffix(".html");
        resolver.setCharacterEncoding("utf-8");
        // 把 resolver 和 engine 关联起来
        engine.setTemplateResolver(resolver);

        // 3、把创建好的 engine 给放到 ServletContext 里面
        servletContextEvent.getServletContext().setAttribute("engine",engine);
        System.out.println("Thymeleaf 初始化完毕");


    }

    // 这个方法暂时可以不用管
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
