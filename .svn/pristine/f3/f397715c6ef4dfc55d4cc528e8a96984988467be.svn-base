package com.hlzt.power.shiro;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.hlzt.commons.exception.SystemUpdateException;
import com.hlzt.commons.helper.StringHelper;
import com.hlzt.power.model.Permission;
import com.hlzt.power.model.Role;
import com.hlzt.power.model.User;
import com.hlzt.power.model.UserRole;
import com.hlzt.power.service.PermissionSer;
import com.hlzt.power.service.RolePermissionSer;
import com.hlzt.power.service.RoleSer;
import com.hlzt.power.service.UserRoleSer;
import com.hlzt.power.service.UserSer;
/**
 * shiro登录以及角色授权
 * @author user
 *
 */
public class MyRealm extends AuthorizingRealm {

	@Resource
	private UserSer userSer;

	@Resource
	private RoleSer roleSer;

	@Resource
	private PermissionSer permissionSer;

	@Resource
	private RolePermissionSer rPermissionSer;

	@Resource
	private UserRoleSer uRoleSer;

	/**
	 * 为当前登录的Subject授予角色和权限
	 * 
	 * @see 经测试:本例中该方法的调用时机为需授权资源被访问时
	 * @see 经测试:并且每次访问需授权资源时都会执行该方法中的逻辑,这表明本例中默认并未启用AuthorizationCache
	 * @see 个人感觉若使用了Spring3
	 *      .1开始提供的ConcurrentMapCache支持,则可灵活决定是否启用AuthorizationCache
	 * @see 比如说这里从数据库获取权限信息时,先去访问Spring3.1提供的缓存,而不使用Shior提供的AuthorizationCache
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
	
		// 获取当前登录的用户名,等价于(String)principals.fromRealm(this.getName()).iterator().next()
		String userNum = (String) super
				.getAvailablePrincipal(principals);
		//当前登录用户所拥有的角色list
		List<String> roleList = new ArrayList<String>();
		
		//当前登录用户所拥有的权限
		List<String> permissionList = new ArrayList<String>();
		System.out.println("这里开始为当前登录用户授予角色和权限！");
		// 从数据库中获取当前登录用户的详细信息
		User user = userSer.findUserByUserNum(userNum);
		//权限对象info，用来存放查出来的用户所有角色（role）以及权限（permission）
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		List<String>userRoleNameList=new ArrayList<String>();
		if (null != user) {	
			List<UserRole> list = uRoleSer.findRolesByUserId(user.getId());
			
			Role role = new Role();
			for (int i = 0; i < list.size(); i++) {
				role = roleSer.findById(list.get(i).getId());
				System.out.println("角色："+role.getRoleName());
				userRoleNameList.add(role.getRoleName());
			
				info.addRole(role.getRoleName());
			}
			/*if(!userRoleNameList.contains("manager"))
			{
				
			}*/
			
		} else {
			throw new AuthorizationException();
		}
		return info;
		// 若该方法什么都不做直接返回null的话,就会导致任何用户访问/admin/listUser.jsp时都会自动跳转到unauthorizedUrl指定的地址
		// 详见applicationContext.xml中的<bean id="shiroFilter">的配置

	}

	/**
	 * 验证当前登录的Subject
	 * 
	 * @see 经测试:本例中该方法的调用时机为LoginController.login()方法中执行Subject.login()时
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		// 获取基于用户名和密码的令牌
		// 实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
		// 两个token的引用都是一样的
		//UsernamePasswordToken对象用来存放提交的登录信息
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		System.out.println("验证当前Subject时获取到token为"
				+ ReflectionToStringBuilder.toString(token,
						ToStringStyle.MULTI_LINE_STYLE));
		//查询是否有此用户
		User user =null;
		try {			
			user = userSer.findUserByUserNum(token.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (null != user) {
			AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(
					user.getUserNum(), user.getPassword(), getName());
			return authcInfo;
		}else{
			throw new UnknownAccountException("用户不存在！");
		}
			
	}

	@Override
	public void onLogout(PrincipalCollection principals) {

		System.out.println("用户退出***********************");

		super.onLogout(principals);

	}

	/**
	 * 将一些数据放到ShiroSession中,以便于其它地方使用
	 * 
	 * @see 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
	 */
	/*private void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			System.out
					.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
			if (null != session) {
				session.setAttribute(key, value);
			}
		}
	}*/
}