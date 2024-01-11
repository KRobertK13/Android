package hu.bme.aut.android.mobweb.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.mobweb.R
import hu.bme.aut.android.mobweb.data.InExItem
import hu.bme.aut.android.mobweb.databinding.FragmentNewInExItemDialogBinding

class NewInExItemDialogFragment : DialogFragment() {
    interface NewInExItemDialogListener {
        fun onInExItemCreated(newItem: InExItem)
    }

    private lateinit var listener: NewInExItemDialogListener

    private lateinit var binding: FragmentNewInExItemDialogBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewInExItemDialogListener
            ?: throw RuntimeException("Ugy hijvak, gond!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentNewInExItemDialogBinding.inflate(LayoutInflater.from(context))
        binding.spCategory.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.category_items)
        )

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_inex_item)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { dialogInterface, i ->
                if (isValid()) {
                    listener.onInExItemCreated(getInExItem())
                }
            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }
    private fun isValid() = binding.etName.text.isNotEmpty()

    private fun getInExItem() = InExItem(
        name = binding.etName.text.toString(),
        sign = binding.rgSign.isChecked,
        description = binding.etDescription.text.toString(),
        amount = binding.etEstimatedPrice.text.toString().toIntOrNull() ?: 0,
        category = InExItem.Category.getByOrdinal(binding.spCategory.selectedItemPosition) ?: InExItem.Category.WORK,
    )
    companion object {
        const val TAG = "NewInExItemDialogFragment"
    }
}