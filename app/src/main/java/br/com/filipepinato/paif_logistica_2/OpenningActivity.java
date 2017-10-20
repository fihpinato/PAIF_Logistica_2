package br.com.filipepinato.paif_logistica_2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class OpenningActivity extends AppCompatActivity {

    private EditText etNome;
    private CheckBox cbSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openning);

        etNome = (EditText) findViewById(R.id.etNome);
        cbSalvar = (CheckBox) findViewById(R.id.cbSalvar);
        if(isConectado())
            iniciarApp();
    }

    private boolean isConectado(){
        SharedPreferences shared = getSharedPreferences("info",MODE_PRIVATE);
        String login = shared.getString("login", "");
        if(login.equals(""))
            return false;
        else
            return true;
    }

    private void iniciarApp(){
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("NOME", etNome.getText().toString());
        startActivity(i);
        finish();
    }

    private void manterConectado(){
        String login = etNome.getText().toString();
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("login", login);
        editor.apply();
    }

    public void login(View view) {
        if(cbSalvar.isChecked())
            manterConectado();

        iniciarApp();
    }
}
