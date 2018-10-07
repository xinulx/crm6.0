package com.mine.App.controller.login;

import com.mine.App.common.Base.BaseController;
import com.mine.App.common.util.DataHelper;
import com.mine.App.dao.login.CodeDao;
import com.mine.App.model.User;
import com.mine.App.model.UserInfo;
import com.mine.App.service.login.UserService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

public class LoginAction extends BaseController {
    @Autowired
    private UserService userService;

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private String viewName;

    // 依赖注入一个名为viewName的参数,例如一个JSP文件，作为展示model的视图
    public String getViewName() {
        return this.viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    /**
     * 登陆成功跳转
     *
     * @param req ,res
     * @return ModelAndView
     */
    public ModelAndView goIndex(HttpServletRequest req,
                                HttpServletResponse res) throws IOException, ServletException {
        logger.info("--> 正在转到系统首页");
        Map model = new HashMap();
        model.put("msg", "登陆成功!");
        return new ModelAndView("pages/index/index", model);
    }

    /**
     * 打开首页右上角菜单：INDEX-index;MESSAGE:message;PERSONAL:personal;SYSTEM:system;
     * LOGOUT:logout
     *
     * @param req
     * @param res
     * @return ModelAndView
     * @throws IOException
     * @throws ServletException
     */
    public ModelAndView openRightMenu(HttpServletRequest req,
                                      HttpServletResponse res) throws IOException, ServletException {
        logger.info("--> 获取当前菜单路径");
        Map model = new HashMap();
        String menu_id = req.getParameter("menu_id");
        String menu_type = req.getParameter("menu_type");
        String menuMethod = req.getParameter("menuMethod");
        Map viewMap = userService.getUrlPath(menu_id, menu_type);
        if (viewMap != null) {
            model.put("msg", viewMap.get("msg"));
        } else {
            model.put("msg", viewMap.get("error"));
        }
        return new ModelAndView((String) viewMap.get("path") + "&" + menuMethod, model);
    }

    /**
     * 获取登陆用户信息：登陆用户及用户的员工信息
     *
     * @param req
     * @param res
     * @throws IOException
     * @throws ServletException
     */
    public void getUserInfo(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        logger.info("--> 获取登陆用户信息");
        Map model = new HashMap();
        HttpSession session = req.getSession();
        UserInfo ui = (UserInfo) session.getAttribute("user");
        User u = (User) session.getAttribute("loginUser");
        model.put("user", ui);
        model.put("loginUser", u);
        DataHelper dh = new DataHelper(req, res);
        dh.responseData(model);
        res.getWriter().close();
    }

    /**
     * 用户校验、营业厅查询
     *
     * @param req
     * @param res
     * @throws ServletRequestBindingException
     * @throws IOException
     */
    public void chkAndQryRole(HttpServletRequest req, HttpServletResponse res)
            throws ServletRequestBindingException, IOException {
        logger.info("chkAndQryRole--> 用户校验、营业厅查询");
        String userId = req.getParameter("username");
        String password = req.getParameter("pwd");
        Map map = userService.getUserInfoByUserId(userId, password);
        DataHelper dh = new DataHelper(req, res);
        dh.responseData(map);
        res.getWriter().close();
    }

    /**
     * 用户登陆,存储用户登录信息
     *
     * @param req
     * @param res
     * @throws ServletRequestBindingException
     * @throws IOException
     */
    public void toLoginAction(HttpServletRequest req, HttpServletResponse res)
            throws ServletRequestBindingException, IOException {
        logger.info("toLoginAction--> 用户登陆,存储用户登录信息");
        HttpSession session = req.getSession();
        String userId = req.getParameter("userId");
        String orgId = req.getParameter("orgId");
        String roleId = req.getParameter("roleId");
        Map map = userService.getUserInfo(userId, orgId, roleId);
        UserInfo userInfo = (UserInfo) map.get("user");
        User user = (User) map.get("loginUser");
        if (userInfo != null && user != null) {
            // 登陆成功将登陆信息存入session
            session.setAttribute("user", userInfo);
            session.setAttribute("userId", userInfo.getUserId());
            session.setAttribute("userName", userInfo.getUserName());

            session.setAttribute("loginUser", user);
            session.setAttribute("empNo", user.getEmpNo());
            session.setAttribute("empName", user.getEmpName());
            session.setAttribute("managerId", user.getManagerId());
            session.setAttribute("roleId", user.getRoleid());
            session.setAttribute("roleName", user.getRoleName());

            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(new Date());
            // 将登陆时间存储到session
            session.setAttribute("loginDate", date);

            // 登陆成功插入登陆系统日志表中，监控和管理登陆用户
            Map maps = new HashMap();
            maps.put("user", userInfo);
            maps.put("loginUser", user);
            maps.put("loginDate", date);
            this.userService.insertLoginLog(maps);
            res.getWriter().print(JSONObject.fromObject(maps));
            res.getWriter().close();
        }
    }

    /**
     * 获取验证码
     *
     * @param req
     * @param res
     * @throws IOException
     * @throws ServletException
     */
    public void showCode(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        logger.info("--> 获取验证码成功");
        new CodeDao().getCode(req, res);// 幻彩
        // new CodeDao().getSimpleCode(req, res);// 简单粗暴
       // new CodeDao().getOtherCode(req, res);// 其他
    }

    /**
     * 用户注销：退出登录
     *
     * @param req
     * @param res
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ModelAndView logout(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        logger.info("--> 正在退出登录...");
        HttpSession session = req.getSession(false);
        if (session.getAttribute("user") != null) {
            // 拦截器只对登陆账号信息进行判断，因此这里只需要删除user即可，其他session可以进行预注销
            session.removeAttribute("user");
            session.invalidate();
        } else {
            logger.info("--> 你未登陆，无需退出");
        }
        return new ModelAndView("login", new HashMap<String, Object>());
    }

    /**
     * 修改密码
     *
     * @param req
     * @param res
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public void modiPasswordAction(HttpServletRequest req,
                                   HttpServletResponse res) throws IOException, ServletException {
        logger.info("--> 修改密码：");
        Map<String, String[]> map = req.getParameterMap();
        Set<Entry<String, String[]>> set = map.entrySet();
        Iterator<Entry<String, String[]>> it = set.iterator();
        while (it.hasNext()) {
            Entry<String, String[]> entry = it.next();

            System.out.println("KEY:" + entry.getKey());
            for (String i : entry.getValue()) {
                System.out.println(i);
            }
        }
        // 参数Map
        Map properties = req.getParameterMap();
        // 返回值Map
        Map returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        String userId = req.getParameter("userId");
        String old_password = req.getParameter("old_password");
        String password = req.getParameter("password");
        String final_password = req.getParameter("final_password");
        String code = req.getParameter("code");

        Map rstMap = new HashMap();
        rstMap.put("userId", userId == null ? "" : userId);
        rstMap.put("old_password", old_password == null ? "" : old_password);
        rstMap.put("password", password == null ? "" : password);
        rstMap.put("final_password", final_password == null ? ""
                : final_password);
        rstMap.put("code", code == null ? "" : code);

        HttpSession session = req.getSession();
        String sessionCode = (String) session.getAttribute("code");
        rstMap.put("sessionCode", sessionCode == null ? "" : sessionCode);

        returnMap.put("sessionCode", sessionCode == null ? "" : sessionCode);

        Map dataMap = this.userService.excuteQueryByUser(returnMap, rstMap);
        if (dataMap.get("msg") == null) {
            throw new RuntimeException("程序异常！");
        }
        DataHelper dh = new DataHelper(req, res);
        dh.responseData(dataMap);
    }
}