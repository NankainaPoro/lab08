package g313.vakulenko.lab08;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    EditText txtid;
    EditText txtctl;

    int nid;
    String ntxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        txtid = findViewById(R.id.txt_id);
        txtctl = findViewById(R.id.txt_content);

        Intent i = getIntent();
        nid = i.getIntExtra("note-id", 0);
        ntxt = i.getStringExtra("note-txt");

        txtid.setText(String.valueOf(nid));
        txtctl.setText(ntxt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.itm_save) {
            try {
                int newID = Integer.parseInt(txtid.getText().toString());
                String txt = txtctl.getText().toString();
                g.notes.alterNote(nid, newID, txt);
                Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
                finish();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "ERROR: Wrong ID format", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (itemId == R.id.itm_delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete")
                    .setMessage("Are you sure you want to delete note?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            g.notes.delete_note(nid);
                            Toast.makeText(MainActivity2.this, "Note deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        } else if (itemId == R.id.itm_exit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Exit")
                    .setMessage("All unsaved changes will be lost. Exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
