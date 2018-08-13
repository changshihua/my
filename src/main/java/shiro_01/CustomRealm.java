package shiro_01;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
/**
 * 自定义realm
 * @author Administrator
 *
 */
public class CustomRealm extends AuthorizingRealm{
	Map<String,String> userMap = new HashMap<String,String>();
	{
		userMap.put("Tom", "7a9e3cef8b553fa41b6a6a3155d55a0d");
		super.setName("customRealm");
	}
	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName = (String)principals.getPrimaryPrincipal();
		Set<String> roles = getRolesByUserName(userName);
		Set<String> permissions = getPermissionsByUserName(userName);
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		//设置角色
		authorizationInfo.setRoles(roles);
		//设置权限
		authorizationInfo.setStringPermissions(permissions);
		
		return authorizationInfo;
	}
	
	private Set<String> getPermissionsByUserName(String userName) {
		Set<String> sets = new HashSet<String>();
		sets.add("user:delete");
		sets.add("user:add");
		return sets;
	}

	/**
	 * 模拟从数据库或缓存中获取角色数据
	 * @param username
	 * @return
	 */
	private Set<String> getRolesByUserName(String userName) {
		Set<String> sets = new HashSet<String>();
		sets.add("admin");
		sets.add("user");
		return sets;
	}

	/**
	 * 认证
	 * 参数token就是subject提交的认证信息，用户名和密码等
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//1、从主体传过来的信息中获取用户名
		String userName = (String)token.getPrincipal();
		//2、通过用户名去数据库获取凭证（密码）
		String password =  getPasswordByUsername(userName);
		if(password == null) {
			return null;
		}
		//customRealm这第三个参数可以随便写，在之前要调用父类设置super.setName("customRealm");
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userName,password,"customRealm");
		//返回authenticationInfo之前，设置盐
		authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("Tom"));
		return authenticationInfo;
	}
	
	/**
	 * 模拟从数据库取数据
	 * @param username
	 * @return
	 */
	private String getPasswordByUsername(String username) {
		//通常开发模式下，这里需要访问数据库
		return userMap.get(username);
	}
	
	public static void main(String[] args) {
		//new Md5Hash("12345","Tom");第一个参数为加密的参数，第二个为盐
		//加盐一般用的随机数
		Md5Hash md5Hash = new Md5Hash("12345","Tom");
		System.out.println(md5Hash.toString());
	}
}
