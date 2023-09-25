import android.app.DatePickerDialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import com.example.ugotprototype.R
import com.example.ugotprototype.data.group.GroupSetNoticeData
import com.example.ugotprototype.ui.group.viewmodel.GroupDetailViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import java.util.Calendar


class BottomSheetHelper(
    private val lifecycleOwner: LifecycleOwner,
    private val context: Context,
    private val groupViewModel: GroupDetailViewModel,
) {

    fun createBottomSheet() {
        val bottomSheet = BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme)
        val view = LayoutInflater.from(context).inflate(R.layout.activity_group_notice_write, null)
        bottomSheet.setContentView(view)

        with(view) {
            val tvSelectedDay = findViewById<TextView>(R.id.tv_selected_day)
            val tvNoticeTitle = findViewById<EditText>(R.id.tv_notice_title)
            val mbNoticeFinish = findViewById<MaterialButton>(R.id.mb_notice_finish)

            tvSelectedDay.setOnClickListener {
                showDatePicker(tvSelectedDay)
            }

            mbNoticeFinish.setOnClickListener {
                val dateParts = tvSelectedDay.text.toString().split("-")
                groupViewModel.setNoticeData(
                    GroupSetNoticeData(
                        year = dateParts[0],
                        month = dateParts[1],
                        dateOfMonth = dateParts[2],
                        content = tvNoticeTitle.text.toString()
                    )
                )
                groupViewModel.isBottomSheetClickCheck(true)
                bottomSheet.dismiss()
            }

            setupPostWriteFinish(tvSelectedDay, tvNoticeTitle, mbNoticeFinish)
        }

        bottomSheet.show()
    }

    private fun setupPostWriteFinish(
        tvSelectedDay: TextView, tvNoticeTitle: EditText, mbNoticeFinish: MaterialButton
    ) {
        val totalListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkFieldsAndUpdateButtonState(tvSelectedDay, tvNoticeTitle, mbNoticeFinish)
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        tvSelectedDay.addTextChangedListener(totalListener)
        tvNoticeTitle.addTextChangedListener(totalListener)

        groupViewModel.isNoticeWriteButtonState.observe(lifecycleOwner) {
            mbNoticeFinish.isEnabled = it
        }
    }

    private fun checkFieldsAndUpdateButtonState(
        tvSelectedDay: TextView, tvNoticeTitle: EditText, mbNoticeFinish: MaterialButton
    ) {
        val isFieldsValid = tvNoticeTitle.length() != 0 && tvSelectedDay.length() != 0
        groupViewModel.isNoticeWriteStateButton(isFieldsValid)
    }

    private fun showDatePicker(tvSelectedDay: TextView) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context, { _, selectedYear, selectedMonth, selectedDay ->
                // 선택된 날짜 처리
                val selectedDate =
                    String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                tvSelectedDay.text = selectedDate
            }, year, month, day
        )

        datePickerDialog.show()
    }
}