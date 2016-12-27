package fr.mozvip.sabnzbd;

import java.io.IOException;
import java.nio.file.Path;

import fr.mozvip.sabnzbd.model.SABHistoryResponse;
import fr.mozvip.sabnzbd.model.SabNzbdResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
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
		return service.getQueueStatus(apiKey).execute().body();
	}

	public SabNzbdResponse getQueue() throws IOException {
		return service.getQueue(apiKey).execute().body();
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
		Response<SabNzbdResponse> response = service.addNZBByFileUpload(body, apiKey).execute();
		SabNzbdResponse sr = response.body();
		return sr.getNzo_ids().get(0);
	}

	public SABHistoryResponse getHistory() throws IOException {
		return service.getHistory(apiKey).execute().body().getHistory();
	}

	public String addNZB(String niceName, String nzbURL) throws IOException {
		SabNzbdResponse response = service.addNZBByURL(nzbURL, niceName, apiKey).execute().body();
		return response.getNzo_ids().get(0);
	}	
}
