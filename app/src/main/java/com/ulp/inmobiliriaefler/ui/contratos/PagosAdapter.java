package com.ulp.inmobiliriaefler.ui.contratos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ulp.inmobiliriaefler.R;
import com.ulp.inmobiliriaefler.modelo.Inmueble;
import com.ulp.inmobiliriaefler.modelo.Pago;

import java.util.List;

public class PagosAdapter extends ArrayAdapter<Pago> {
    private List<Pago> lista;
    private Context contexto;
    private LayoutInflater layoutInflater;

    public PagosAdapter(@NonNull Context context, int resource, @NonNull List<Pago> objects) {
        super(context, resource, objects);
        lista= objects;
        contexto= context;
        layoutInflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView=convertView;
        if(itemView==null){

            itemView=layoutInflater.inflate(R.layout.item_pago,parent,false);

        }
        Pago pago = lista.get(position);
        TextView tvCodigoPago = itemView.findViewById(R.id.tvCodigoPago);
        TextView tvNumeroPago = itemView.findViewById(R.id.tvNumeroPago);
        TextView tvCodigoContratoPago = itemView.findViewById(R.id.tvCodigoContratoPago);
        TextView tvImportePago = itemView.findViewById(R.id.tvImportePago);
        TextView tvFechaPago =itemView.findViewById(R.id.tvFechaPago);
        tvCodigoPago.setText(pago.getIdPago()+"");
        tvNumeroPago.setText(pago.getNumero()+"");
        tvCodigoContratoPago.setText(pago.getContrato()+"");
        tvImportePago.setText("$"+String.format("%.2f",pago.getImporte()));
        tvFechaPago.setText(pago.getFechaDePago().toString());
        return  itemView;
    }
}
