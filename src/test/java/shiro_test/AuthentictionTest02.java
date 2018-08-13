package shiro_test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
/**
 * shiro授权演示
 * @author Administrator
 *
 */
public class AuthentictionTest02 {
	
	@Test
	public void testAutentiction() {
		SimpleAccountRealm simpleAccount = new SimpleAccountRealm();
		//添加一个管理员的角色
		simpleAccount.addAccount("Jack", "123456","admin");
		//构建securityManager环境
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		defaultSecurityManager.setRealm(simpleAccount);
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		
		//获得主体
		Subject subject = SecurityUtils.getSubject();
		
		//提交认证
		UsernamePasswordToken token = new UsernamePasswordToken("Jack","123456");
		subject.login(token);
		System.out.println("认证结果："+subject.isAuthenticated());
		//检查主体是否具备某角色
		subject.checkRole("admin");
	}
}
