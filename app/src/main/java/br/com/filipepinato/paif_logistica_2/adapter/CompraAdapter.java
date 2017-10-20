package br.com.filipepinato.paif_logistica_2.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.filipepinato.paif_logistica_2.R;
import br.com.filipepinato.paif_logistica_2.model.Compra;

public class CompraAdapter extends RecyclerView.Adapter<CompraAdapter.CompraViewHolder>{

    private List<Compra> compras;
    private Activity activity;
    private int lastPositionSelected;

    public class CompraViewHolder extends RecyclerView.ViewHolder implements
    View.OnClickListener, View.OnCreateContextMenuListener{

        public TextView tvCompra;

        public CompraViewHolder(View view){
            super(view);
            tvCompra = (TextView) view.findViewById(R.id.tvCompra);
            view.setOnClickListener(this);
            view.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view){
            activity.openContextMenu(view);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenu.ContextMenuInfo menuInfo){
            lastPositionSelected = getLayoutPosition();
            menu.setHeaderIcon(R.mipmap.ic_launcher);
            menu.setHeaderTitle(activity.getString(R.string.app_name));
            MenuInflater inflater = activity.getMenuInflater();
            inflater.inflate(R.menu.context_lista, menu);
        }
    }

    public int getLastPositionSelected(){
        return lastPositionSelected;
    }

    public CompraAdapter(Activity activity, List<Compra> compras){
        this.activity = activity;
        this.compras = compras;
    }

    @Override
    public CompraViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.compra_list_row, parent, false);
        return new CompraViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CompraViewHolder holder, int position){
        Compra movie = compras.get(position);
        holder.tvCompra.setText(movie.getDescricao());
    }

    @Override
    public int getItemCount(){
        return compras.size();
    }

    public void add(Compra compra){
        compras.add(compra);
        notifyDataSetChanged();
    }

    public void addAll(List<Compra> comprasList){
        compras.addAll(comprasList);
        notifyDataSetChanged();
    }

    public Compra getCompraSelected(){
        return compras.get(lastPositionSelected);
    }

    public void removeCompraSlected(){
        compras.remove(compras.get(lastPositionSelected));
        notifyItemRemoved(lastPositionSelected);
    }
}
