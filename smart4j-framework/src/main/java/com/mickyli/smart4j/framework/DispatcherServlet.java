package com.mickyli.smart4j.framework;

import com.mickyli.smart4j.framework.bean.Data;
import com.mickyli.smart4j.framework.bean.Handler;
import com.mickyli.smart4j.framework.bean.Param;
import com.mickyli.smart4j.framework.bean.View;
import com.mickyli.smart4j.framework.helper.BeanHelper;
import com.mickyli.smart4j.framework.helper.ConfigHelper;
import com.mickyli.smart4j.framework.helper.ControllerHelper;
import com.mickyli.smart4j.framework.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求转发器
 * Created by liqian on 2017/7/28.
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet{
    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求方法与请求路径
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();
        //获取action处理器
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if (handler != null){
            //获取controller类及其bean实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            //创建请求参数对象
            Map<String, Object> paramMap = new HashMap<>();
            Enumeration<String> paramNames = req.getParameterNames();
            while (paramNames.hasMoreElements()){
                String paramName = paramNames.nextElement();
                String paramValue = req.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }
            String body = CodecUtil.decodeUrl(StreamUtil.getString(req.getInputStream()));
            if (StringUtil.isNotEmpty(body)){
                String[] params = StringUtil.splitString(body, "&");
                if (ArrayUtil.isNotEmpty(params)){
                    for (String param : params){
                        String[] array = StringUtil.splitString(param, "=");
                        if (ArrayUtil.isNotEmpty(array) && array.length == 2){
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramName, paramValue);
                        }
                    }
                }
            }

            Param param = new Param(paramMap);
            //调用action方法
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMetho(controllerBean, actionMethod, param);
            //处理action方法的返回值
            if (result instanceof View){
                //返回jsp页面
                View view = (View)result;
                String path = view.getPath();
                if (StringUtil.isNotEmpty(path)){
                    if(path.startsWith("/")){
                        resp.sendRedirect(req.getContextPath() + path);
                    } else {
                      Map<String, Object> model = view.getModel();
                      for (Map.Entry<String,Object> entry : model.entrySet()){
                          req.setAttribute(entry.getKey(), entry.getValue());
                      }
                      req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
                    }
                }
            } else if (result instanceof Data){
                //返回json数据
                Data data = (Data) result;
                Object model = data.getModel();
                if (model != null){
                    resp.setCharacterEncoding("UTF-8");
                    resp.setContentType("application/json");
                    PrintWriter printWriter = resp.getWriter();
                    String json = JsonUtil.toJson(model);
                    printWriter.print(json);
                    printWriter.flush();
                    printWriter.close();
                }
            }
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //初始化相关helper类
        HelperLoader.init();
        //获取servletconfig对象
        ServletContext servletContext = config.getServletContext();
        //获取处理jsp的servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        //获取处理静态资源的servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }
}
