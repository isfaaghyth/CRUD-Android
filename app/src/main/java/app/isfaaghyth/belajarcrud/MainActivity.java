package app.isfaaghyth.belajarcrud;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Random;

import app.isfaaghyth.belajarcrud.Adapter.MainAdapter;

public class MainActivity extends AppCompatActivity {

    private ItemObject.ObjectBelajar objectBelajar;
    private MainAdapter adapter;
    private RecyclerView rv_item;
    private LinearLayoutManager layoutManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv_item = (RecyclerView) findViewById(R.id.rv_item);
        layoutManager = new LinearLayoutManager(getApplication());
        rv_item.setHasFixedSize(true);
        rv_item.setLayoutManager(layoutManager);
        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Silahkan Tunggu...");
        //load pertama kali
        GetData(URLServices.URL_SHOW);
    }

    public void GetData(String URL) {
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {;
            @Override
            public void onResponse(String response) {
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                objectBelajar = mGson.fromJson(response, ItemObject.ObjectBelajar.class);
                adapter = new MainAdapter(getApplication(), objectBelajar.belajar);
                rv_item.setAdapter(adapter);
                progressDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error :(", Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
        });
        queue.add(stringRequest);
    }

    public void LoadData(String Url) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url,
                new Response.Listener<String>() {;
            @Override
            public void onResponse(String response) {
                Log.d("Belajar", response);
                GetData(URLServices.URL_SHOW);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Belajar", error.toString());
            }
        });
        queue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnRefresh:
                GetData(URLServices.URL_SHOW);
                break;
            case R.id.mnAdd:
                DialogAddItem();
                break;
            case R.id.mnAuthor:
                Toast.makeText(getApplicationContext(), "http://github.com/isfaaghyth", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void DialogAddItem() {
        LayoutInflater inflater;
        AlertDialog.Builder dialog;
        View dialogView;

        dialog = new AlertDialog.Builder(MainActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_add, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Tambah Data");

        final TextView txtName = (EditText) dialogView.findViewById(R.id.txtName);
        final TextView txtOffice = (EditText) dialogView.findViewById(R.id.txtOffice);
        final TextView txtEmail = (EditText) dialogView.findViewById(R.id.txtEmail);

        dialog.setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoadData(URLServices.URL_INSERT(txtName.getText().toString(),
                        txtOffice.getText().toString(),
                        txtEmail.getText().toString()));
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
