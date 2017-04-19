package com.example.abdallahsamara.myapplication;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.asamarah.project2.R;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private EditText mEditText;
    private ListView mListView;
    private CustomListAdapter mCustomListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = (EditText) findViewById(R.id.editText);
        mListView = (ListView) findViewById(R.id.listView);

        mCustomListAdapter = new CustomListAdapter(MainActivity.this);

        mListView.setAdapter(mCustomListAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = mCustomListAdapter.getItem(position);
                if (s.startsWith("call")) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    } else {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);

                        int firstSpace = s.indexOf(" ");
                        callIntent.setData(Uri.parse("tel:" + s.substring(4, firstSpace)));

                        startActivity(callIntent);
                    }
                } else {
                    showDialogWith(mCustomListAdapter.getItem(position), position);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

    }

    private void showDialogWith(final String text, final int position) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(text)
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mCustomListAdapter.removeItemAt(position);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void appendTextToListView() {
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                new DatePickerDialog(MainActivity.this, datePickerListener,
                        year, month,day).show();
            }
        });
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            int year = selectedYear;
            int month = selectedMonth;
            int day = selectedDay;

            String textToAdd = mEditText.getText().toString();
            String date = new StringBuilder().append(month + 1).append("-").append(day)
                    .append("-").append(year).append(" ").toString();
            mCustomListAdapter.addItem(textToAdd + " " + date);
        }
    };

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                appendTextToListView();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
