package br.votacao.mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.List;

import br.votacao.mobile.api.Candidato;

public class ListaAdapter extends BaseAdapter {

    private Context context;
    private List<Candidato> lista;
    private ViewHolder holder;

    public ListaAdapter(Context context, List<Candidato> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Candidato getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lista.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Candidato candidato = lista.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.linha, null);
            holder = new ViewHolder();
            holder.nome = (TextView) convertView.findViewById(R.id.nome);
            holder.foto = (ImageView) convertView.findViewById(R.id.foto);
            holder.totalVotos = (TextView) convertView.findViewById(R.id.totalVotos);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nome.setText(candidato.getNome());
        holder.totalVotos.setText("Total de votos: " + candidato.getTotalVotos().intValue());

        Ion.with(holder.foto)
                .centerCrop()
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.error)
                .animateIn(R.anim.fade_in)
                .load(Constants.PATH_URL + "/" + candidato.getFoto());

        return convertView;
    }

    static class ViewHolder {
        TextView nome;
        ImageView foto;
        TextView totalVotos;
    }
}
