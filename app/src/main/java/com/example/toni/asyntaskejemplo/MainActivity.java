package com.example.toni.asyntaskejemplo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ProgressBar pb;
    private Button btninicio, btnfin;
    private MiTareaAsincrona tarea1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb = (ProgressBar) findViewById(R.id.pb);
        btninicio = (Button) findViewById(R.id.btninicio);
        btnfin = (Button) findViewById(R.id.btnfin);

        btninicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tarea1 = new MiTareaAsincrona();
                tarea1.execute();
            }
        });

        btnfin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tarea1.cancel(true);
            }
        });
    }

    private class MiTareaAsincrona extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            for(int i=1; i<=10; i++) {
                tareaLarga();

                publishProgress(i*10);

                if(isCancelled())
                    break;
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pb.setProgress(values[0].intValue());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setMax(100);
            pb.setProgress(0);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(aBoolean)
                Toast.makeText(MainActivity.this, "Tarea finalizada", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(MainActivity.this, "Tarea Cancelada", Toast.LENGTH_SHORT).show();
        }

        private void tareaLarga(){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }
    }
}
