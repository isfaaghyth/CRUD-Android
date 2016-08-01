package app.isfaaghyth.belajarcrud;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Isfahani on 30-Jul-16.
 */
public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txt_name, txt_office, txt_email;
    private Button btnDelete, btnModify;
    private String id, name, office, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_office = (TextView) findViewById(R.id.txt_office);
        txt_email = (TextView) findViewById(R.id.txt_email);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnModify = (Button) findViewById(R.id.btnModify);
        btnDelete.setOnClickListener(this);
        btnModify.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LoadExtraContent();
    }

    private void LoadExtraContent() {
        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        office = getIntent().getStringExtra("office");
        email = getIntent().getStringExtra("email");
        txt_name.setText(name);
        txt_office.setText(office);
        txt_email.setText(email);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDelete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Konfirmasi");
                builder.setMessage("Apakah anda yakin ingin menghapus\n" +
                        name + " ?");
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        LoadData(URLServices.URL_DELETE(id));
                        Intent i = new Intent(DetailActivity.this, MainActivity.class);
                        startActivityForResult(i, 1);
                        finish();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Batalkan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                break;
            case R.id.btnModify:
                DialogModify();
                break;
        }
    }

    public void LoadData(String Url) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url,
                new Response.Listener<String>() {;
                    @Override
                    public void onResponse(String response) {
                        Log.d("Belajar", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Belajar", error.toString());
            }
        });
        queue.add(stringRequest);
    }

    private void DialogModify() {
        LayoutInflater inflater;
        AlertDialog.Builder dialog;
        View dialogView;

        dialog = new AlertDialog.Builder(DetailActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_add, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Modifikasi Data");

        final TextView txtName = (EditText) dialogView.findViewById(R.id.txtName);
        final TextView txtOffice = (EditText) dialogView.findViewById(R.id.txtOffice);
        final TextView txtEmail = (EditText) dialogView.findViewById(R.id.txtEmail);

        txtName.setText(name);
        txtOffice.setText(office);
        txtEmail.setText(email);

        dialog.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoadData(URLServices.URL_MODIFY(id,
                        txtName.getText().toString(),
                        txtOffice.getText().toString(),
                        txtEmail.getText().toString()));
                txt_name.setText(txtName.getText().toString());
                txt_office.setText(txtOffice.getText().toString());
                txt_email.setText(txtEmail.getText().toString());
                dialog.dismiss();
            }
        });
        dialog.setNegativeButton("Batalkan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
