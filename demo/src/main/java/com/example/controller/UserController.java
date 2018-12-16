package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.common.DicConstant;
import com.example.entity.User;
import com.example.service.UserService;
import com.example.utils.LogUtils;
import com.example.utils.Md5Util;
import com.example.utils.exportword.WordGeneratorWithFreemarker;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/userlist")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 查询所有用户
	 * @return
	 */
	@RequestMapping(value = "/getAll", produces = { "application/json;charset=UTF-8" })
	public @ResponseBody Map<String,Object> getAllUser() {
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			List<User> userList = userService.getAll();
			map.put("data", userList);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.error(e);
			map.put("data", "error");
		}
		return map;
	}
	
	/**
	 * 按ID查询用户
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getUserById", produces = { "application/json;charset=UTF-8" })
	public @ResponseBody Map<String,Object> getLabourById(int id) {
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			List<User> List = userService.getUserById(id);
			map.put("data",List);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.error(e);
			map.put("data", "error");
		}
		return map;
	}
	
	/**
	 * 按ID删除用户
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteId", produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String deleteUser(int id) {
		String Msg = "error";
		try {
			if (id > 0) {
				int result = userService.deleteId(id);
				if (result > 0) {
					Msg = "success";
				}
			}
		} catch (Exception e) {
			LogUtils.error(e.toString());
		}
		return Msg;
	}
	
	/**
	 * 新增用户
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/addUser", produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String save(String name,String remark,String date) {
		try {
			User user = new User();
			user.setUser_name(name);
			user.setRemark(remark);
			user.setValid_time(date);
			user.setUser_status(DicConstant.YXBS_01); 
			int i = userService.insertUserInfo(user);
			if (i > 0) {
				return "success";
			} else {
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.error(e);
			return "error";
		}
	}
	
	/**
	 * 富文本导出world
	 * @param name
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/exprotWord", produces = { "application/json;charset=UTF-8" })
	public @ResponseBody Object exprotWord(String remark,String remarkTitle,HttpServletRequest request) {
		String path = request.getServletContext().getRealPath(""); //获取项目动态绝对路径 
		try {
			String exportPath = WordGeneratorWithFreemarker.exportWord(remark,remarkTitle,path);
				Map<String, String> map = new HashMap<String, String>();
				exportPath = " 导出成功！已保存到 "+exportPath;
			    map.put("info","success");
			    map.put("content",exportPath);
			    JSONObject result = JSONObject.fromObject(map);
			    return result;
			
		} catch (Exception e) {
			LogUtils.error(e.toString());
		}
		return "error";
	}
	
	/**
	 * 富文本编辑器 上传图片
	 * @param mf
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	 @RequestMapping(value ="/upload",method=RequestMethod.POST)
	 public @ResponseBody String UpLoadImg(@RequestParam(value="myFileName")MultipartFile mf,HttpServletRequest request) throws IOException {
		 String realPath = request.getSession().getServletContext().getRealPath("upload");
	    //获取源文件
	    String filename = mf.getOriginalFilename();
	    String[] names=filename.split("\\.");//
	    String tempNum=(int)(Math.random()*100000)+"";
	    String uploadFileName=tempNum +System.currentTimeMillis()+"."+names[names.length-1];
	    File targetFile = new File (realPath,uploadFileName);//目标文件
	    
	    String dfef= getClass().getResource("/").getFile().toString();
        System.out.println("##"+dfef);
        
	    try {
	        mf.transferTo(targetFile);
	    } catch (IllegalStateException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    String path = request.getServletContext().getRealPath(""); //获取项目动态绝对路径 
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("data","../../upload/"+uploadFileName);//项目路径
	    map.put("path",path); //项目绝对路径
	    
	    JSONObject jsonObject = JSONObject.fromObject(map);
        String result = jsonObject.toString();
        
	    return result; //将图片地址返回
    }
	
	
	/**
	 * 修改用户
	 * @param id
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/updateUser", produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String update(int id, String name,String remark,String date) {
		try {
			User user = new User();
			user.setUser_id(id);
			user.setUser_name(name);
			user.setRemark(remark);
			user.setValid_time(date);
			int i = userService.updateUserInfo(user);
			if (i > 0) {
				return "success";
			} else {
				return "error";
			}
		} catch (Exception e) {
			LogUtils.error(e.toString());
		}
		return "error";
	}

	/**
	 * 用户登录
	 * @param name
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String loginUser(String account, String password,HttpServletRequest request) {
		String Msg = "success";
		try {
			List<User> info = userService.queryAccount(account);
			if (info.size() > 0) {
				password = Md5Util.MD5(password);
				if(info.get(0).getPassword().equals(password)) {
					HttpSession session = request.getSession();
					session.setAttribute("user", info.get(0));	
				}
				else {
					Msg = "用户名或密码错误";
				}
			}
			else {
				Msg = "用户不存在";
			}
		} catch (Exception e) {
			LogUtils.error(e.toString());
			Msg = "登陆异常，请联系管理员";
		}
		return Msg;
	}
	
	/**
	 * 获取session值
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/GetSession", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Object GetSessionValue(HttpServletRequest request) {
		Object info = null;
		try {
			HttpSession session = request.getSession();
			User temp = new User();
			temp = (User) session.getAttribute("user");
			info = temp;
		} catch (Exception e) {
			LogUtils.error(e.toString());
		}
		return info;
	}
	/**
	 * 退出账户
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/SignOut", produces = { "application/json;charset=UTF-8" })
	public @ResponseBody Object SignOut(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			session.removeAttribute("user");
		} catch (Exception e) {
			LogUtils.error(e.toString());
		}
		return "../../html/login.html";
	}
	
	/**
	 * 判断session是否为空
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/SessionExit", produces = { "application/json;charset=UTF-8" })
	public @ResponseBody Object SessionExit(HttpServletRequest request) {
		String Msg = "Null";
		try {
			HttpSession session = request.getSession();
			if(session.getAttribute("user") != null && session.getAttribute("user") != "") {
				Msg = "notNull";
			}
		} catch (Exception e) {
			LogUtils.error(e.toString());
		}
		return Msg;
	}
	
	/**
	 * 验证旧密码是否正确
	 * @param oldPwd
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/validateOldPwd", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Object validateOldPwd(String oldPwd,HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			User temp = new User();
			temp = (User) session.getAttribute("user");	
			
			if(temp != null && temp.getPassword().equals(Md5Util.MD5(oldPwd))) {
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.error(e);			
		}
		return false;
	}
	/**
	 * 修改密码
	 * @param oldpwd
	 * @param newpwd
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/EditPwd", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Object EditPwd(String oldpwd,String newpwd,HttpServletRequest request) {		
		boolean boo = false;
		try {
			HttpSession session = request.getSession();	
			if(oldpwd != null && oldpwd.trim() != "" && newpwd != null && newpwd.trim() != "" && session.getAttribute("user") != null && session.getAttribute("user") != "") {						
				User user = (User)session.getAttribute("user");
				if(userService.getUserById(user.getUser_id()).size() > 0) {
					User info = (User) userService.getUserById(user.getUser_id()).get(0);
					if(info.getPassword().equals(Md5Util.MD5(oldpwd))) {	
						info.setPassword(Md5Util.MD5(newpwd));
						int result = userService.updatePassWord(info);
						if(result>0) {
							session.setAttribute("user", info);  //重新放进session里
						}						
						boo = true;
					}					
				}								
			}			
		} catch (Exception e) {
			LogUtils.error(e.toString());
		}
		return boo;
	}
	
}
