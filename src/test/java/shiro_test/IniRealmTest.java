package shiro_test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * iniRealm演示 除此外还有jdbcRealm
 * 
 * @author Administrator
 *
 */
public class IniRealmTest {

	@Test
	public void testAuthentication() {
		IniRealm iniRealm = new IniRealm("classpath:user.ini");

		// 1、构建securityManager环境
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		defaultSecurityManager.setRealm(iniRealm);

		// 主体提交认证请求
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		Subject subject = SecurityUtils.getSubject();

		// 提交认证
		UsernamePasswordToken token = new UsernamePasswordToken("Mark", "123456");
		subject.login(token);

		System.out.println("isAuthenticated:" + subject.isAuthenticated());
		subject.isAuthenticated();
		// 检查用户角色
		subject.checkRole("admin");
		// 检查用户权限
		subject.checkPermission("user:update");
	}
}
