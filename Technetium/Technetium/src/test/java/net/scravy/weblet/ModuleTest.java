package net.scravy.weblet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import junit.framework.Assert;
import net.scravy.technetium.modules.auth.Login;
import net.scravy.technetium.modules.auth.Logout;
import net.scravy.technetium.util.IOUtil;

import org.junit.Before;
import org.junit.Test;

public class ModuleTest {

	private static Module testModule() {
		return new Module(
				new Module.HandlerXml("/login", Login.class),
				new Module.HandlerXml("/logout", Logout.class));
	}

	private Marshaller m;
	private Unmarshaller u;

	@Before
	public void setup() throws Exception {
		JAXBContext c = JAXBContext.newInstance(Module.class);
		m = c.createMarshaller();
		u = c.createUnmarshaller();
	}
	
	@Test
	public void testMarshallingAndUnmarshalling() throws Exception {
		Module module = testModule();

		FileOutputStream tmpFile;
		FileInputStream tmpFileIn;
		File tmp;

		tmp = File.createTempFile("test-", "-test");
		m.marshal(module, tmpFile = new FileOutputStream(tmp));
		tmpFile.close();

		String xml1 = IOUtil.getContents(tmp);
		Module module2 = (Module) u.unmarshal(tmpFileIn = new FileInputStream(
				tmp));
		tmpFileIn.close();
		
		Assert.assertEquals(module.getHandlers().get(0).getHandler(), module2
				.getHandlers().get(0).getHandler());

		tmp = File.createTempFile("test-", "-test");
		m.marshal(module, tmpFile = new FileOutputStream(tmp));
		tmpFile.close();

		String xml2 = IOUtil.getContents(tmp);
		module = (Module) u.unmarshal(tmpFileIn = new FileInputStream(tmp));
		tmpFileIn.close();

		Assert.assertEquals(xml1, xml2);
	}
}
