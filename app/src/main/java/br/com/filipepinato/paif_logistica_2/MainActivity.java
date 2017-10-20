package br.com.filipepinato.paif_logistica_2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

import br.com.filipepinato.paif_logistica_2.adapter.CompraAdapter;
import br.com.filipepinato.paif_logistica_2.dao.CompraDAO;
import br.com.filipepinato.paif_logistica_2.model.Compra;

public class MainActivity extends AppCompatActivity {

    private TextView tvNomeUsuario;

    private RecyclerView rvMinhasCompras;
    private CompraAdapter mAdapter;
    private FloatingActionMenu fMenu;

    private CompraDAO compraDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNomeUsuario = (TextView) findViewById(R.id.tvNomeUsuario);
        tvNomeUsuario.setText(getIntent().getStringExtra("NOME"));

        fMenu = (FloatingActionMenu) findViewById(R.id.fMenu);
        compraDAO = new CompraDAO();
        inicializaLista();
        carregaMinhasCompras();
    }

    private void inicializaLista() {
        rvMinhasCompras = (RecyclerView) findViewById(R.id.rvMinhasCompras);
        mAdapter = new CompraAdapter(this, new ArrayList<Compra>());
        rvMinhasCompras.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvMinhasCompras.setItemAnimator(new DefaultItemAnimator());
        rvMinhasCompras.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));
        rvMinhasCompras.setAdapter(mAdapter);
    }

    private void carregaMinhasCompras() {
        mAdapter.addAll(compraDAO.findAll());
        mAdapter.notifyDataSetChanged();
    }

    public boolean onContextItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.menu_apagar:
                mAdapter.getCompraSelected().delete();
                mAdapter.removeCompraSlected();
                Toast.makeText(this, "Compra apagada com sucesso", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_editar:
                dialogCompra(mAdapter.getCompraSelected());
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void dialogCompra(final Compra compra) {
        final boolean isInsert = compra.getId() == null ? true:false;
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.new_compra);

        dialog.setTitle("Nova Compra");

        final EditText etCompra = (EditText)dialog.findViewById(R.id.etCompra);
        etCompra.setText(compra.getDescricao());

        Button btConfirmar = (Button) dialog.findViewById(R.id.btConfirmar);

        btConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compra.setDescricao(etCompra.getText().toString());
                compra.save();

                if(isInsert)
                    mAdapter.add(compra);

                mAdapter.notifyDataSetChanged();

                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Compra gravada com sucesso!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        Button btCancelar = (Button) dialog.findViewById(R.id.btCancelar);

        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void novaCompra(View view) {
        fMenu.close(true);
        dialogCompra(new Compra());
    }
}
