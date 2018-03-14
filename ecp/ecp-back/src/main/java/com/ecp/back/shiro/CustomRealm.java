package com.ecp.back.shiro;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.ecp.bean.UserBean;
import com.ecp.entity.Menu;
import com.ecp.entity.User;
import com.ecp.service.back.IUserService;

/**
 * 自定义Realm
 * 
 * @author srd
 * @date 2017-7-5下午18:30:47
 */
public class CustomRealm extends AuthorizingRealm {
	
	@Resource(name="userServiceBean")
	private IUserService userService;

	// 设置realm的名称
	@Override
	public void setName(String name) {
		super.setName("customRealm");
	}

	//realm的认证方法，从数据库查询用户信息
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		
		// token是用户输入的用户名和密码 
		// 第一步从token中取出用户名
		String username = (String) token.getPrincipal();

		// 第二步：根据用户输入的userCode从数据库查询
		User user = null;
		try {
			List<User> userList = userService.getByUsername(username);
			if(!userList.isEmpty() && userList.size()==1){
				user = userList.get(0);
			}
			System.out.println("需要登录的用户："+user);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// 如果查询不到返回null
		if(user==null){//
			return null;
		}
		// 从数据库查询到密码
		String password = user.getPassword();
		
		//盐
		String salt = username+":";

		// 如果查询到返回认证信息AuthenticationInfo
		
		//activeUser就是用户身份信息
		UserBean activeUser = new UserBean();
		
		activeUser.setId(user.getId());
		activeUser.setUsername(user.getUsername());
		activeUser.setPassword(user.getPassword());
		activeUser.setNickname(user.getNickname());
		activeUser.setLinkMan(user.getLinkMan());
		activeUser.setLinkPhoneNum(user.getLinkPhoneNum());
		activeUser.setMobile(user.getMobile());
		activeUser.setEmail(user.getEmail());
		activeUser.setType(user.getType());
		activeUser.setCreatedTime(user.getCreatedTime());
		activeUser.setUpdateTime(user.getUpdateTime());
		//..
		
		//根据用户id取出菜单
		List<Menu> menuList  = null;
		try {
			//通过service取出菜单 
			menuList = userService.getMenuPermissionListByUserId(user.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//将菜单 设置到activeUser
		activeUser.setMenuList(menuList);
		
		//将activeUser设置simpleAuthenticationInfo
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(activeUser, password,ByteSource.Util.bytes(salt), this.getName());
		//SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(activeUser, password, this.getName());

		return simpleAuthenticationInfo;
	}
	
	

	// 用于授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		
		//从 principals获取主身份信息
		//将getPrimaryPrincipal方法返回值转为真实身份类型（在上边的doGetAuthenticationInfo认证通过填充到SimpleAuthenticationInfo中身份类型），
		UserBean activeUser =  (UserBean) principals.getPrimaryPrincipal();
		System.out.println("验证权限");
		//根据身份信息获取权限信息
		//从数据库获取到权限数据
		/*List<SysPermission> permissionList = null;
		try {
			permissionList = sysService.findPermissionListByUserId(activeUser.getUserid());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//单独定一个集合对象 
		List<String> permissions = new ArrayList<String>();
		if(permissionList!=null){
			for(SysPermission sysPermission:permissionList){
				//将数据库中的权限标签 符放入集合
				permissions.add(sysPermission.getPercode());
			}
		}*/
		
		
	/*	List<String> permissions = new ArrayList<String>();
		permissions.add("user:create");//用户的创建
		permissions.add("item:query");//商品查询权限
		permissions.add("item:add");//商品添加权限
		permissions.add("item:edit");//商品修改权限
*/		//....
		
		//查到权限数据，返回授权信息(要包括 上边的permissions)
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		//将上边查询到授权信息填充到simpleAuthorizationInfo对象中
		//simpleAuthorizationInfo.addStringPermissions(permissions);

		return simpleAuthorizationInfo;
	}
	
	//清除缓存
	public void clearCached() {
		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
		super.clearCache(principals);
	}


}
