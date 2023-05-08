import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tempoapp.R
import com.example.tempoapp.model.HistoricTempoResponse

class HistoricTempoAdapter(private val historicTempoList: List<HistoricTempoResponse>) :
    RecyclerView.Adapter<HistoricTempoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val couleurTextView: TextView = itemView.findViewById(R.id.couleurTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.historic_tempo_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = historicTempoList[position]
        holder.dateTextView.text = item.date
        holder.couleurTextView.text = item.couleur
    }

    override fun getItemCount(): Int = historicTempoList.size
}
