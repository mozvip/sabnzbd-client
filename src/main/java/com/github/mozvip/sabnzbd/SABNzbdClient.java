package com.github.mozvip.sabnzbd;

import java.io.IOException;
import java.nio.file.Path;

import com.github.mozvip.sabnzbd.model.SABHistoryResponse;
import com.github.mozvip.sabnzbd.model.SabNzbdResponse;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class SABNzbdClient {
	
	public static final class Builder {
		
		private String baseUrl = "http://localhost:8080/";
		private String apiKey;
		
		public Builder baseUrl( String baseUrl ) {
			this.baseUrl = baseUrl;
			return this;
		}
		
		public Builder apiKey( String apiKey ) {
			this.apiKey = apiKey;
			return this;
		}
		
		public SABNzbdClient build() {
			return new SABNzbdClient( baseUrl, apiKey );
		}
		
	}
	
	public static Builder Builder() {
		return new Builder();
	}
	
	private String apiKey;
	
	private SABNzbdService service;

	public SABNzbdClient(String baseUrl, String apiKey) {
		this.apiKey = apiKey;
		
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(baseUrl)
				.addConverterFactory( JacksonConverterFactory.create() )
				.build();
		service = retrofit.create(SABNzbdService.class);		
	}


	public void deleteFromHistory(String clientId) throws IOException {
		service.deleteFromHistory(clientId, apiKey).execute();
	}

	public void remove(String clientId) throws IOException {
		service.delete(clientId, apiKey).execute();
	}

	public SabNzbdResponse getQueueStatus() throws IOException {
		SabNzbdResponse response = service.getQueueStatus(apiKey).execute().body();
		checkError( response );
		return response;
	}

	public SabNzbdResponse getQueue() throws IOException {
		SabNzbdResponse response = service.getQueue(apiKey).execute().body();
		checkError( response );
		return response;
	}

	public void delete(String nzo_id) {
		service.delete(nzo_id, apiKey);
	}

	public void deleteFailed() {
		service.deleteFailed(apiKey);
	}

	public String addNZB(Path nzbFilePath) throws IOException {
		RequestBody requestFile = RequestBody.create(MediaType.parse("application/x-nzb"), nzbFilePath.toFile());
	    MultipartBody.Part body = MultipartBody.Part.createFormData("name", nzbFilePath.getFileName().toString(), requestFile);		
		SabNzbdResponse response = service.addNZBByFileUpload(body, apiKey).execute().body();
		checkError( response );
		return response.getNzo_ids().get(0);
	}

	public SABHistoryResponse getHistory() throws IOException {
		SabNzbdResponse response = service.getHistory(apiKey).execute().body();
		checkError(response);
		return response.getHistory();
	}

	public String addNZB(String niceName, String nzbURL) throws IOException {
		SabNzbdResponse response = service.addNZBByURL(nzbURL, niceName, apiKey).execute().body();
		checkError(response);
		return response.getNzo_ids().get(0);
	}


	private void checkError(SabNzbdResponse response) throws IOException {
		if (response.getError() != null) {
			throw new IOException( response.getError() );
		}
	}	
}
