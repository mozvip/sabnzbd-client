package fr.mozvip.sabnzbd;

import fr.mozvip.sabnzbd.model.SabNzbdResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface SABNzbdService {

	@Multipart
	@POST("/sabnzbd/api?mode=addfile&output=json")
	public Call<SabNzbdResponse> addNZBByFileUpload(@Part MultipartBody.Part file, @Query("apikey") String apiKey);

	@GET("/sabnzbd/api?mode=addurl&output=json")
	public Call<SabNzbdResponse> addNZBByURL(@Query("name") String url, @Query("nzbname") String niceName, @Query("apikey") String apiKey);

	@GET("/sabnzbd/api?mode=qstatus&output=json")
	public Call<SabNzbdResponse> getQueueStatus(@Query("apikey") String apiKey);

	@GET("/sabnzbd/api?mode=queue&output=json")
	public Call<SabNzbdResponse> getQueue(@Query("apikey") String apiKey);

	@GET("/sabnzbd/api?mode=queue&name=delete&del_files=1")
	public Call<Void> delete(@Query("value") String id, @Query("apikey") String apiKey);

	@GET("/sabnzbd/api?mode=history&name=delete&del_files=1&output=json")
	public Call<Boolean> deleteFromHistory(@Query("value") String id, @Query("apikey") String apiKey);

	@GET("/sabnzbd/api?mode=history&limit=1000&output=json")
	public Call<SabNzbdResponse> getHistory(@Query("apikey") String apiKey);

	@GET("/sabnzbd/api?mode=history&name=delete&value=failed&del_files=1&output=json")
	public Call<String> deleteFailed(@Query("apikey") String apiKey);

}
