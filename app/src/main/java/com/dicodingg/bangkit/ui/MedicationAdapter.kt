package com.dicodingg.bangkit.ui.medication

import android.view.LayoutInflater
import android.view.ViewGroup
import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicodingg.bangkit.databinding.ItemMedicationBinding

class MedicationAdapter(
    private val onDeleteClick: (Medication) -> Unit
) : ListAdapter<Medication, MedicationAdapter.MedicationViewHolder>(MedicationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicationViewHolder {
        val binding = ItemMedicationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedicationViewHolder(binding, onDeleteClick)
    }

    override fun onBindViewHolder(holder: MedicationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MedicationViewHolder(
        private val binding: ItemMedicationBinding,
        private val onDeleteClick: (Medication) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(medication: Medication) {
            binding.textNamaObat.text = medication.nama
            binding.textDosis.text = "${medication.dosis}"  // Display the dose
            binding.textJenisObat.text = medication.jenisObat  // Display the type (tablet, kaplet, etc.)
            binding.textFrekuensi.text = medication.frekuensi
            binding.textWaktu.text = "${medication.jam} - ${medication.waktu}"

            // Set the delete button click listener
            binding.buttonDelete.setOnClickListener {
                onDeleteClick(medication)
            }
        }
    }

    class MedicationDiffCallback : DiffUtil.ItemCallback<Medication>() {
        override fun areItemsTheSame(oldItem: Medication, newItem: Medication): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Medication, newItem: Medication): Boolean {
            return oldItem == newItem
        }
    }
}
