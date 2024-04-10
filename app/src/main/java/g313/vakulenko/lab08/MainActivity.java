package g313.vakulenko.lab08;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lstctl;
    ArrayList<mynote> lst = new ArrayList<> ();
    ArrayAdapter<mynote> adp;

    Context ctx;

    void update_list(){
        lst.clear();
        g.notes.getAllNotes(lst);
        adp.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        update_list();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;

        g.notes = new DB(this,"notes.db",null,1);

        lstctl = findViewById(R.id.lst_note);
        adp = new ArrayAdapter<mynote>(this, android.R.layout.simple_list_item_1, lst);
        lstctl.setAdapter(adp);

        lstctl.setOnItemClickListener((parent, view, position, id) -> {
            mynote n = adp.getItem(position);
            Intent i = new Intent(ctx, MainActivity2.class);
            i.putExtra("note-id", n.id);
            i.putExtra("note-txt", n.txt);
            startActivityForResult(i, 1);
        });

        update_list();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();

        if (id == R.id.itm_new) {
            int nid = g.notes.getMaxId() + 1;
            g.notes.addNote(nid,"Sample text");
            update_list();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}