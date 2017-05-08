package test.util;

import java.text.MessageFormat;

import org.junit.Test;

import util.EmailUtils;

public class MessageTest {

	@Test
	public void MesageStringTest(){
//		System.out.println(EmailUtils.content);
		String name = EmailUtils.username;
		System.out.println(EmailUtils.username);
		System.out.println(EmailUtils.password);
		System.out.println(EmailUtils.content);
		String content = MessageFormat.format(EmailUtils.content, "cc", "cc");
		System.out.println(content);
	}
}
