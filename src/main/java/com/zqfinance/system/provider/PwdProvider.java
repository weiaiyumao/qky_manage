package com.zqfinance.system.provider;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.springframework.util.DefaultPropertiesPersister;

import com.zqfinance.system.util.Encrypter;

public class PwdProvider extends DefaultPropertiesPersister {

	@Override
	public void load(Properties props, InputStream is) throws IOException {
		Properties properties = new Properties();
		properties.load(is);
		if (properties.get("jdbc.password") != null) {
			String password = Encrypter.decrypt(properties.getProperty("jdbc.password"));
			properties.setProperty("jdbc.password" , password);     
		}
		OutputStream outputStream = null;
	    try {
	        outputStream = new ByteArrayOutputStream();
	        properties.store(outputStream, "");
	        is = outStream2InputStream(outputStream);
	        super.load(props, is);
	    }catch(IOException e) {
	        throw e;
	    }finally {
	        outputStream.close();
	    }
		super.load(props, is);
	}

	private InputStream outStream2InputStream(OutputStream out) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos = (ByteArrayOutputStream) out;
		ByteArrayInputStream swapStream = new ByteArrayInputStream(
				bos.toByteArray());
		return swapStream;
	}
}
