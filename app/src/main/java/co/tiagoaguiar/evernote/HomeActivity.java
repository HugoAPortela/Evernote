package co.tiagoaguiar.evernote;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

  private NoteAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getBaseContext(), FormActivity.class);
        startActivity(intent);
      }
    });
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    NavigationView navigationView = findViewById(R.id.nav_view);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();
    navigationView.setNavigationItemSelectedListener(this);

    DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

    List<Note> notes = new ArrayList<>();

    adapter = new NoteAdapter(notes);
    RecyclerView rv = findViewById(R.id.recycler_view);
    rv.setLayoutManager(new LinearLayoutManager(this));
    rv.setAdapter(adapter);
    rv.addItemDecoration(divider);

  }

  @Override
  protected void onResume() {
    super.onResume();
    request();
  }

  private void request() {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://myevernote.glitch.me")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    EvernoteAPI api = retrofit.create(EvernoteAPI.class);
    api.listNotes().enqueue(new Callback<List<Note>>() {
      @Override
      public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
        if (response.isSuccessful()) {
          List<Note> notes = response.body();

          if (notes != null) {
            adapter.notes.clear();
            adapter.notes.addAll(notes);
            adapter.notifyDataSetChanged();
          }
        }
      }

      @Override
      public void onFailure(Call<List<Note>> call, Throwable t) {
        Log.i("t", t.getMessage());
      }
    });
  }

  private class NoteAdapter extends RecyclerView.Adapter<NoteView> {

    private final List<Note> notes;

    NoteAdapter(List<Note> notes) {
      this.notes = notes;
    }

    @NonNull
    @Override
    public NoteView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = getLayoutInflater().inflate(R.layout.list_item_note, parent, false);
      return new NoteView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteView holder, int position) {
      Note note = notes.get(position);

      holder.bind(note.getTitle(), note.getDesc(), note.getDate());
    }

    @Override
    public int getItemCount() {
      return notes.size();
    }

  }


  private static class NoteView extends RecyclerView.ViewHolder {
    TextView noteTitle, noteDesc, noteDate;

    public NoteView(@NonNull View itemView) {
      super(itemView);
      noteDate = itemView.findViewById(R.id.note_date);
      noteTitle = itemView.findViewById(R.id.note_title);
      noteDesc = itemView.findViewById(R.id.note_desc);
    }

    void bind(String title, String desc, String date) {
      noteTitle.setText(title);
      noteDate.setText(date);
      noteDesc.setText(desc);
    }
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.home, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();


    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }
}
