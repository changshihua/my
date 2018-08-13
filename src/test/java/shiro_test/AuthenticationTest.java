package shiro_test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
/**
 * shiro认证
 * @author Administrator
 *
 */
public class AuthenticationTest {
	SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();
	
	@Before
	public void addUser() {
		//模拟数据源/realm
		simpleAccountRealm.addAccount("Mark", "123456");
	}
	
	@Test
	public void testAuthentication() {
		//1、构建securityManager环境
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		defaultSecurityManager.setRealm(simpleAccountRealm);
		
		//主体提交认证请求
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		Subject subject = SecurityUtils.getSubject();
		
		//提交认证
		UsernamePasswordToken token = new UsernamePasswordToken("Mark","123456");
		subject.login(token);
		
		System.out.println("isAuthenticated:"+subject.isAuthenticated());
		subject.isAuthenticated();
		
		subject.logout();
		System.out.println("isAuthenticated:"+subject.isAuthenticated());
	}
}
