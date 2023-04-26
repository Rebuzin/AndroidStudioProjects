package com.example.trabalhoprimeirob;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabalhoprimeirob.Models.Cliente;
import com.example.trabalhoprimeirob.Models.Item;
import com.example.trabalhoprimeirob.Models.Pedido;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText edCPF;
    private EditText edCliente;
    private EditText edItem;
    private EditText edQuantidade;
    private EditText edValorUnit;
    private EditText edValorTot;

    private EditText edParcelas;
    private EditText edCodPed;
    private TextView tvListaItens;
    private TextView tvParcelas;
    private TextView tvListaParc;
    private LinearLayout layoutParcelas;

    private TextView tvResultado;

    private Button btInserir;

    private Button btSalvar;
    private Button btCalc;
    private Button btProc;
    private RadioGroup radioGroupPagamento;
    private RadioButton radioAVista;
    private RadioButton radioAPrazo;

    private ArrayList<Item> listItens = new ArrayList<Item>();
    private ArrayList<Pedido> listPedidos = new ArrayList<Pedido>();
    private double total = 0;

    private boolean vista;



    private double totalFmPagto = 0.0;
    private int idCdPed = 1;
    private int nrParc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edCPF = findViewById(R.id.edCPF);
        edCliente = findViewById(R.id.edCliente);
        edItem = findViewById(R.id.edItem);
        edQuantidade = findViewById(R.id.edQuantidade);
        edValorUnit = findViewById(R.id.edValorUnit);
        edValorTot = findViewById(R.id.edValorTot);

        edParcelas = findViewById(R.id.edtParcelas);
        edCodPed = findViewById(R.id.edCd_Ped);
        layoutParcelas= findViewById(R.id.layoutParcelas);
        tvParcelas = findViewById(R.id.tvParcelas);
        tvListaItens = findViewById(R.id.tvListaItens);
        tvListaParc = findViewById(R.id.tvListaParc);
        btSalvar = findViewById(R.id.btSalvar);
        btInserir = findViewById(R.id.btInserir);
        btCalc = findViewById(R.id.btCalc);
        btProc = findViewById(R.id.btCdPed);

        radioGroupPagamento = findViewById(R.id.radioGroupPagamento);
        radioAVista = findViewById(R.id.radioAVista);
        radioAPrazo = findViewById(R.id.radioAPrazo);

        radioGroupPagamento.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioAVista) {
                    layoutParcelas.setVisibility(View.GONE);
                    tvParcelas.setVisibility(View.GONE);
                    totalFmPagto = total - (total * 0.05);
                    edValorTot.setText(String.valueOf(totalFmPagto));
                } else if (checkedId == R.id.radioAPrazo) {
                    layoutParcelas.setVisibility(View.VISIBLE);
                    tvParcelas.setVisibility(View.VISIBLE);
                    totalFmPagto = total + (total * 0.05);
                    edValorTot.setText(String.valueOf(totalFmPagto));
                }
            }
        });

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalvarCliente();
            }
        });

        btInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insereItem();
            }
        });

        btCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcPrazo();
            }
        });

        btProc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {ProcurarPedido();
            }
        });
    }

    private void SalvarCliente(){

        String cpf = edCPF.getText().toString();
        String nome = edCliente.getText().toString();
        String item = edItem.getText().toString();
        String quantidade = edQuantidade.getText().toString();
        String valorunitario = edValorUnit.getText().toString();

        try {

            if(!nome.equals("") && !cpf.equals("") && !(listItens.size()==0) && (radioAVista.isChecked() || radioAPrazo.isChecked())){
                Cliente cliente = new Cliente(nome,cpf);
                if(radioAVista.isChecked()){
                    vista = true;
                }else{
                    vista = false;
                }
                Pedido pedido = new Pedido(idCdPed,cliente,listItens,totalFmPagto,vista);
                listPedidos.add(pedido);
                edCliente.setText("");
                edCPF.setText("");
                edItem.setText("");
                edQuantidade.setText("");
                edValorUnit.setText("");
                tvListaItens.setText("");
                radioAVista.setChecked(false);
                radioAPrazo.setChecked(false);
                edParcelas.setText("0");
                tvListaParc.setText("");
                edValorTot.setText("0");
                total = 0;
                idCdPed++;
                edCodPed.setText(String.valueOf(idCdPed));
                Toast.makeText(this, "Pedido de código "+(idCdPed-1)+" foi cadastrado com sucesso!", Toast.LENGTH_LONG).show();


            } else {
                if (cpf.equals("")){
                    edCPF.setError("Informe o CPF!");
                }
                if (nome.equals("")){
                    edCliente.setError("Informe o Cliente!");
                }
                if (item.equals("")){
                    edItem.setError("Informe o Item!");
                }
                if (quantidade.equals("")){
                    edQuantidade.setError("Informe a quantidade!");
                }
                if (valorunitario.equals("")){
                    edValorUnit.setError("Informe o Valor Unitário!");
                }
                if(radioAVista.isChecked() == false && radioAPrazo.isChecked() == false){
                    Toast.makeText(this, "Favor escolher uma forma de pagamento!", Toast.LENGTH_LONG).show();
                }
                if(listItens.size()==0){
                    Toast.makeText(this, "Favor inserir pelo menos um item a lista de pedido!", Toast.LENGTH_LONG).show();
                }
            }

        }catch (Exception ex){
            Log.e("ERRO", ex.getMessage());
        }


    }

    private void ProcurarPedido() {
        boolean existe = false;
        int cdExiste = 0;

        Integer cdPed = Integer.parseInt(edCodPed.getText().toString());

        if (cdPed.equals("")) {
            edCodPed.setText(idCdPed);
        }

        for(int i = 0; i<listPedidos.size();i++){
            if(cdPed.equals(listPedidos.get(i).getId())) {
                existe = true;
                cdExiste = i;
                break;
            }
        }

        try {
            String resultado = "";
            if (!cdPed.equals("") && !cdPed.equals(idCdPed)) {

                edCliente.setText(listPedidos.get(cdExiste).getCliente().getNome());
                edCPF.setText(listPedidos.get(cdExiste).getCliente().getCpf());

                for (int i = 0; i<listPedidos.get(cdExiste).getListItens().size();i++) {
                    resultado += "Item: "+listPedidos.get(cdExiste).getListItens().get(i).getNome()+" Quantidade: "+listPedidos.get(cdExiste).getListItens().get(i).getQtn()+" Valor Unitário: "+listPedidos.get(cdExiste).getListItens().get(i).getVl_unitario()+"\n";
                }
                tvListaItens.setText(resultado);
                if (listPedidos.get(cdExiste).isVista()){
                    radioAVista.setChecked(true);
                }else{
                    Toast.makeText(this, "prazo", Toast.LENGTH_SHORT).show();
                    radioAPrazo.setChecked(true);
                    edParcelas.setText(nrParc);
                    resultado += "--------------------------------------"+ "\n";
                    double vlTotParc = (total + (total * 0.05))/nrParc;
                    for(int i = 0;i<=nrParc;i++){
                        resultado += "Parcela "+(i+1)+"° custa: R$ "+vlTotParc+"\n";
                    }
                    resultado += "--------------------------------------"+ "\n";
                    tvListaParc.setText(resultado);
                }
                total = listPedidos.get(cdExiste).getVlTotalPagto();
                edValorTot.setText(Double.toString(listPedidos.get(cdExiste).getVlTotalPagto()));


            } else {

                if (cdPed <= 0) {
                    edItem.setError("Informe um código válido!");
                }
                if (existe == false) {
                    Toast.makeText(this, "Não existe nenhum pedido com código "+cdPed, Toast.LENGTH_SHORT).show();
                }
            }

        }catch(Exception ex){
            Log.e("ERRO", ex.getMessage());
        }
    }

    private void calcPrazo(){

        if(edParcelas.getText().toString().equals("")){
            edParcelas.setText("0");
        }

        Integer qtnParc = Integer.parseInt(edParcelas.getText().toString());

        try{
            String resultado = tvListaParc.getText().toString();
            if(!qtnParc.equals("0")){
                double vlTotParc = (total + (total * 0.05))/qtnParc;
                resultado += "--------------------------------------"+ "\n";
                for(int i = 1;i<=qtnParc;i++){
                    resultado += "Parcela "+i+"° custa: R$ "+vlTotParc+"\n";
                }
                resultado += "--------------------------------------"+ "\n";
                tvListaParc.setText(resultado);
                nrParc = qtnParc;
            }else{
                if(listItens.size()==0){
                    Toast.makeText(this, "Favor inserir pelo menos um item a lista de pedido!", Toast.LENGTH_LONG).show();
                }
                if(String.valueOf(qtnParc).startsWith("0")&& !qtnParc.equals("0")){
                    edQuantidade.setError("Informe a quantidade de parcelas!");
                }
            }

        }catch(Exception ex){
            Log.e("ERRO", ex.getMessage());
        }
    }

    private void insereItem(){
        if(edValorUnit.getText().toString().equals("")){
            edValorUnit.setText("0");
        }
        if(edQuantidade.getText().toString().equals("")){
            edQuantidade.setText("0");
        }

        String nm_item = edItem.getText().toString();
        Double vl_Unit = Double.parseDouble(edValorUnit.getText().toString());
        Integer qtn = Integer.parseInt(edQuantidade.getText().toString());

        try{
            String resultado = tvListaItens.getText().toString();
            if(!nm_item.equals("") && !vl_Unit.equals("0")&& !qtn.equals("0")){
                resultado += "Item: "+nm_item+"/ Quantidade: "+qtn+"/ Valor Unitário: "+vl_Unit+"\n";
                tvListaItens.setText(resultado);
                Item item = new Item(nm_item,qtn,vl_Unit);
                listItens.add(item);
                total = 0.0;
                for (int i=0;i<listItens.size();i++){
                    total += listItens.get(i).getTotal();
                }
                edValorTot.setText(String.valueOf(total));
                Toast.makeText(this, "Item adicionado a lista com sucesso!", Toast.LENGTH_LONG).show();
            }else{
                if(nm_item.equals("")){
                    edItem.setError("Informe o nome do item!");
                }
                if(String.valueOf(qtn).startsWith("0")&& !qtn.equals("0")){
                    edQuantidade.setError("Informe a quantidade do item!");
                }
                if(String.valueOf(vl_Unit).startsWith("0")&& !vl_Unit.equals("0")){
                    edValorUnit.setError("Informe o valor unitário!");
                }
            }

        }catch(Exception ex){
            Log.e("ERRO", ex.getMessage());
        }
    }

}