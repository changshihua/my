package shiro_test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import shiro_01.CustomRealm;
/**
 * 测试自定义realm
 * 加密
 * 加盐
 * @author Administrator
 *
 */
public class CustomRealmTest {
	@Test
	public void testAuthentication() {
		CustomRealm customRealm = new CustomRealm();
		
		// 1、构建securityManager环境
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		defaultSecurityManager.setRealm(customRealm);
		//加密算法
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
		matcher.setHashAlgorithmName("md5");//设置加密算法的名称
		matcher.setHashIterations(1);//设置加密的次数
		customRealm.setCredentialsMatcher(matcher);//设置加密对象

		// 主体提交认证请求
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		Subject subject = SecurityUtils.getSubject();

		// 提交认证
		UsernamePasswordToken token = new UsernamePasswordToken("Tom", "12345");
		subject.login(token);

		System.out.println("isAuthenticated:" + subject.isAuthenticated());
		subject.isAuthenticated();
		// 检查用户角色
		//subject.checkRole("admin");
		// 检查用户权限
		//subject.checkPermissions("user:update","user:add");
	}
}
