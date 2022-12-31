package ph.crisaroa.movielistapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton

class SortBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private lateinit var listener: SortListener

    interface SortListener {
        fun changeSort(sort: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_sort, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnTitle: MaterialButton = view.findViewById(R.id.btn_sort_title)
        val btnReleasedDate: MaterialButton = view.findViewById(R.id.btn_sort_released_date)
        val btnCancel: MaterialButton = view.findViewById(R.id.btn_sort_cancel)

        btnTitle.setOnClickListener {
            listener.changeSort("Title")
            dismiss()
        }
        btnReleasedDate.setOnClickListener {
            listener.changeSort("Released date")
            dismiss()
        }
        btnCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as SortListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                (context.toString() +
                        " must implement SortListener")
            )
        }
    }
}