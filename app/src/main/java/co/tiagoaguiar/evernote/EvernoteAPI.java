package co.tiagoaguiar.evernote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Julho, 31 2019
 *
 * @author suporte@moonjava.com.br (Tiago Aguiar).
 */
public interface EvernoteAPI {

  @GET("/")
  Call<List<Note>> listNotes();

  @POST("/create")
  Call<Note> createNote(@Body Note note);

  @GET("/{id}")
  Call<Note> getNote(@Path("id") int id);

}
