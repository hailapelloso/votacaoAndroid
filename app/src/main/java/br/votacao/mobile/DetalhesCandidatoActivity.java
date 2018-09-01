package br.votacao.mobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import br.votacao.mobile.api.ApiClient;
import br.votacao.mobile.api.ApiServices;
import br.votacao.mobile.api.Candidato;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalhesCandidatoActivity extends AppCompatActivity {

    private Button votar;
    private TextView nome;
    private Candidato candidato;
    private ImageView foto;
    private TextView partido;
    private TextView propostas;
    private TextView detalhes;
    private TextView site;
    private TextView totalVotos;
    private ApiServices apiServices;
    private ProgressDialog progress = null;
    private  Long idCandidato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_candidatos);
        Toolbar myChildToolbar = (Toolbar) findViewById(R.id.toolbar_detalhes);
        myChildToolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(myChildToolbar);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        nome = findViewById(R.id.nome);
        foto = findViewById(R.id.foto);
        partido = findViewById(R.id.partido);
        propostas = findViewById(R.id.propostas);
        detalhes = findViewById(R.id.detalhes);
        site = findViewById(R.id.site);
        totalVotos = findViewById(R.id.totalVotos);

        votar = findViewById(R.id.votar);
        votar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    enviarVoto();
                    finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if(intent != null){
            idCandidato = intent.getLongExtra("id", 1l);
            if(idCandidato != null){
                getCandidato(idCandidato);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.votar:
                enviarVoto();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void enviarVoto(){
        progress = ProgressDialog.show(DetalhesCandidatoActivity.this,
                "Aguarde ...", "Recebendo informações da web", true, true);
        apiServices = ApiClient.getClient().create(ApiServices.class);
        Call<Candidato> call = apiServices.sendVoto ("application/json", idCandidato);
        call.enqueue(new Callback<Candidato>() {
            @Override
            public void onResponse(Call<Candidato> call, Response<Candidato> response) {
                if (response.isSuccessful()) {
                    progress.dismiss();
                    finish();
                } else {
                    progress.dismiss();
                    finish();
                }
            }
            @Override
            public void onFailure(Call<Candidato> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getCandidato(Long id) {
        progress = ProgressDialog.show(DetalhesCandidatoActivity.this,
                "Aguarde ...", "Recebendo informações da web", true, true);
        apiServices = ApiClient.getClient().create(ApiServices.class);

        Call<Candidato> call = apiServices.getCandidato ("application/json", id);
        call.enqueue(new Callback<Candidato>() {
            @Override
            public void onResponse(Call<Candidato> call, Response<Candidato> response) {
                if (response.isSuccessful()) {
                    candidato =  response.body();

                    if(candidato != null){
                        nome.setText(candidato.getNome());
                        partido.setText("Partido: " + candidato.getPartido());
                        propostas.setText(candidato.getPropostas());
                        detalhes.setText(candidato.getDetalhes());
                        site.setText("Site: " + candidato.getSite());
                        totalVotos.setText("Total de votos: " + candidato.getTotalVotos());
                        Ion.with(foto)
                                .centerCrop()
                                .placeholder(R.drawable.place_holder)
                                .error(R.drawable.error)
                                .animateIn(R.anim.fade_in)
                                .load(Constants.PATH_URL + "/" + candidato.getFoto());

                    }
                    progress.dismiss();

                } else {
                    progress.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Candidato> call, Throwable t) {
                progress.dismiss();
                t.printStackTrace();
            }
        });
    }
}
