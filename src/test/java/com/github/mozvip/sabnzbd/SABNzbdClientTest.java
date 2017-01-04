package com.github.mozvip.sabnzbd;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

import com.github.mozvip.sabnzbd.SABNzbdClient;
import com.github.mozvip.sabnzbd.model.SabNzbdResponse;
import com.github.mozvip.sabnzbd.model.SabNzbdResponseSlot;

import junit.framework.Assert;

public class SABNzbdClientTest {
	
	private static SABNzbdClient client;
	
	@BeforeClass
	public static void init() throws IOException {
		client = SABNzbdClient.Builder().baseUrl("http://192.168.1.75:8080").apiKey("19876488f6210d84583f94b6c2b8be71").build();
	}

	@Test
	public void testUploadRemove() throws IOException, URISyntaxException {
		URL resource = this.getClass().getClassLoader().getResource("test.nzb");
		Path nzbFilePath = Paths.get( resource.toURI() );
		String nzbId = client.addNZB(nzbFilePath);
		
		boolean found = false;

		SabNzbdResponse queue = client.getQueue();
		for (SabNzbdResponseSlot slot : queue.getQueue().getSlots()) {
			if (slot.getNzo_id().equals( nzbId )) {
				Assert.assertTrue( slot.getFilename().equals("test"));
				found = true;
			}
		}
		
		Assert.assertTrue( found );
		
		client.remove( nzbId );
		
		found = false;

		queue = client.getQueue();
		for (SabNzbdResponseSlot slot : queue.getQueue().getSlots()) {
			if (slot.getNzo_id().equals( nzbId )) {
				found = true;
			}
		}
		
		Assert.assertFalse( found );
	}	
}
