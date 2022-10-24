package com.ulp.inmobiliriaefler.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ulp.inmobiliriaefler.R;
import com.ulp.inmobiliriaefler.modelo.Inmueble;
import com.ulp.inmobiliriaefler.modelo.Pago;

import java.util.List;

public class PagoFragment extends Fragment {

    private PagoViewModel vm;
    private ListView lvPagos;
    private List<Pago> listaPagos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vm =new ViewModelProvider(this).get(PagoViewModel.class);
        View view = inflater.inflate(R.layout.fragment_pago, container, false);

        vm.getMutablePagos().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> pagos) {
                listaPagos= pagos ;
                PagosAdapter pag = new PagosAdapter(getContext(),R.layout.item_pago,listaPagos);
                lvPagos.setAdapter(pag);
            }
        });
        inicializarVista(view);
        return view;
    }
    private void inicializarVista(View view){
        lvPagos =view.findViewById(R.id.lvPagos);
        Bundle bundle = getArguments();
        vm.obtenerPagos(bundle);
    }

}