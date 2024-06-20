import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.gardenguard.data.model.Fruit
import com.dicoding.gardenguard.databinding.ItemFruitBinding

class FruitAdapter(
    private val context: Context,
    private var fruitList: List<Fruit>,
    private val onItemClick: ((Fruit) -> Unit)?
) :
    RecyclerView.Adapter<FruitAdapter.FruitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitViewHolder {
        val binding = ItemFruitBinding.inflate(LayoutInflater.from(context), parent, false)
        return FruitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FruitViewHolder, position: Int) {
        val fruit = fruitList[position]
        Glide.with(context)
            .load(fruit.imageUrl)
            .into(holder.binding.ivFruitImage)
        holder.binding.tvFruitName.text = fruit.name
        holder.binding.tvFruitScientificName.text = fruit.scientificName
        holder.binding.root.setOnClickListener {
            onItemClick?.invoke(fruit)
        }
    }

    override fun getItemCount(): Int {
        return fruitList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newFruitList: List<Fruit>) {
        fruitList = newFruitList
        notifyDataSetChanged()
    }

    inner class FruitViewHolder(val binding: ItemFruitBinding) : RecyclerView.ViewHolder(binding.root)
}
