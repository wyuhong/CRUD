package com.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.biz.IUserBiz;
import com.biz.impl.UserBizImpl;
import com.entity.UserInfo;

public class UserAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IUserBiz uBiz = new UserBizImpl();
       
    public UserAction() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1 固定部分 设置字符集
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("utf-8");
		
		//比较flag标签判断选择执行相应的方法
		String flag = request.getParameter("flag");
		if("login".equals(flag)){
			this.login(request,response);
		}else if("delete".equals(flag)){
			this.delete(request,response);
		}else if("toUpdate".equals(flag)){
			this.toUpdate(request,response);
		}else if("doUpdate".equals(flag)){
			this.doUpdate(request,response);
		}else if("doAdd".equals(flag)){
			this.doAdd(request,response);
		}
	}
	private void doAdd(HttpServletRequest request, HttpServletResponse response) {
		try{
		//获取前端页面控件的值
		String userName = request.getParameter("userName");
		String userPwd = request.getParameter("userPwd");
		//封装到对象中
		UserInfo ui = new UserInfo(userName,userPwd);
		//调用BIZ层方法执行插入操作
		int i = uBiz.doAdd(ui);
		if(i>0){
			//新增成功
			this.findAllUser(request, response);
		}
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 真正的修改，携带内容
	 * @param request
	 * @param response
	 */
	private void doUpdate(HttpServletRequest request, HttpServletResponse response) {
		try{
		//获取前端页面控件的值,将字符串转成int类型
		int id = Integer.parseInt(request.getParameter("id"));
		String userName = request.getParameter("userName");
		String userPwd = request.getParameter("userPwd");
		//封装到对象中
		UserInfo ui = new UserInfo(id,userName,userPwd);
 		//修改成功
		int i = uBiz.updateUser(ui);
		if(i>0){
			//修改成功
			this.findAllUser(request, response);
		}
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 去修改的路上，携带内容去下一个页面
	 * @param request
	 * @param response
	 */
	private void toUpdate(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			//1 通过userId来查询出该对象
			UserInfo ui = uBiz.findUserById(id);
			//2 把查询出的对象存放到request范围之内
			request.setAttribute("ui", ui);
			//3 转发到下一个页面
			request.getRequestDispatcher("update.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		
	}

	/**
	 * 根据id删除相应的用户
	 * @param request
	 * @param response
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response) {
		try{
			int id = Integer.parseInt(request.getParameter("id"));
			int i = uBiz.deleteUserById(id);
			if(i>0){
				this.findAllUser(request,response);
			}
			}catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
	}

private void findAllUser(HttpServletRequest request, HttpServletResponse response) {
	try{
			//1 使用BIZ的方法查询出所有用户记录
			List<UserInfo> ulist = uBiz.findAllUser();
			//2 将查询出的内容存放到request范围之内
			request.setAttribute("ulist", ulist);
			//3 携带数据转发到下一个页面,转发到一个新的JSP:welcome.jsp
			request.getRequestDispatcher("welcome.jsp").forward(request, response);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		
	}

/**
 * 登录，登录成功后，将数据库内容展示在登录后的页面，登陆失败重写回到登录界面
 * @param request
 * @param response
 */
	private void login(HttpServletRequest request, HttpServletResponse response) {
		try{
		String userName = request.getParameter("userName");
		String userPwd = request.getParameter("userPwd");
		UserInfo ui = new UserInfo(userName,userPwd);
	    boolean bl = uBiz.login(ui);
		if(bl){
			//1 使用BIZ的方法查询出所有用户记录
			List<UserInfo> ulist = uBiz.findAllUser();
			//2 将查询出的内容存放到request范围之内
			request.setAttribute("ulist", ulist);
			//3 携带数据转发到下一个页面,转发到一个新的JSP:welcome.jsp
			request.getRequestDispatcher("welcome.jsp").forward(request, response);
		}else{
			//重定向到首页
			response.sendRedirect("index.jsp");
		}
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

}
