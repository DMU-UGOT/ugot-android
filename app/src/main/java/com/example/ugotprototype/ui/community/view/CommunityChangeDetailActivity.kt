package com.example.ugotprototype.ui.community.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ugotprototype.R
import com.example.ugotprototype.data.community.BoardService
import com.example.ugotprototype.data.community.CommunityDetailViewInterface
import com.example.ugotprototype.databinding.ActivityCommunityChangeDetailBinding
import com.example.ugotprototype.databinding.ActivityCommunityChangeSendMessageBinding
import com.example.ugotprototype.ui.community.viewmodel.CommunityChangeViewModel
import java.text.SimpleDateFormat
import java.util.*

class CommunityChangeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityChangeDetailBinding
    private lateinit var viewModel: CommunityChangeViewModel
    private var boardController: CommunityDetailViewInterface = BoardService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_change_detail)

        viewModel = ViewModelProvider(this).get(CommunityChangeViewModel::class.java)

        // 글쓴이 여부 확인 (여기서는 임의로 true/false를 지정하거나 실제 데이터를 사용하여 결정)
        val isAuthor = boardController.checkBoarderOwner()

        dataChangeSet()
        backCommunityChangeToMainActivity()
        clickSendMessage()

        // 글쓴이 여부에 따라 버튼 텍스트를 동적으로 변경
        viewModel.isAuthor.observe(this, Observer { isAuthor ->
            val buttonText = if (isAuthor) "수정하기" else "문의하기"
            binding.btChangeNewUpdate.text = buttonText

            if (isAuthor) {
                // 글쓴이인 경우
                binding.btChangeNewMessage.visibility = View.GONE // "보내기" 버튼 감추기
            } else {
                // 글쓴이가 아닌 경우
                binding.btChangeNewMessage.visibility = View.VISIBLE // "보내기" 버튼 보이기
            }
            binding.btChangeNewUpdate.visibility = if (isAuthor) View.VISIBLE else View.GONE // 수정하기 버튼 보이기/감추기
        })
        viewModel.setIsAuthor(isAuthor)

        // 아이템 정보 받기
        var comChangeExchange = intent.getStringExtra("comChangeExchange")

        // 거래 상태 표시
        binding.tvCommunityChangeDetailExchange.text = comChangeExchange

        // 수정 버튼 클릭 이벤트 처리
        binding.btChangeNewUpdate.setOnClickListener {
            // 거래 상태를 "완료"와 "가능" 사이에서 토글합니다.
            comChangeExchange = if (comChangeExchange == "완료") "가능" else "완료"
            binding.tvCommunityChangeDetailExchange.text = comChangeExchange
        }

        // 삭제 버튼 클릭 이벤트 처리
        binding.btChangeNewDelete.setOnClickListener {
            // 글 삭제를 위한 AlertDialog 생성
            val builder = AlertDialog.Builder(this)
            builder.setTitle("글 삭제")
            builder.setMessage("정말로 글을 삭제하시겠습니까?")

            builder.setPositiveButton("확인") { _, _ ->
                // 여기에 글 삭제 로직을 구현
                // 예를 들어, 글 삭제 API 호출이나 데이터 처리 등을 수행

                // 삭제 후 액티비티 종료
                finish()
            }

            builder.setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }

            val alertDialog = builder.create()
            alertDialog.show()
        }
    }

    private fun backCommunityChangeToMainActivity() {
        binding.ivCommunityChangeBack.setOnClickListener {
            Intent().putExtra("resultText", "text")
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }

    private fun dataChangeSet() {
        binding.tvCommunityChangeDetailGrade.text = intent.getStringExtra("comChangeGrade")
        binding.tvCommunityChangeDetailNickName.text = intent.getStringExtra("comChangeNickName")
        binding.tvCommunityChangeDetailTime.text = intent.getStringExtra("comChangeDate")
        binding.tvCommunityChangeDetailNowInput.text = intent.getStringExtra("comChangeNowClass")
        binding.tvCommunityChangeDetailChangeInput.text = intent.getStringExtra("comChangeClass")
        binding.tvCommunityChangeDetailExchange.text = intent.getStringExtra("comChangeExchange")
    }

    // 문자열로 된 날짜를 원하는 포맷으로 변환하는 함수 추가
    private fun formatDate(dateString: String?): String {
        if (dateString == null) return ""

        val inputDateFormat = SimpleDateFormat("yy.MM.dd HH:mm", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("yy.MM.dd", Locale.getDefault())
        val date = inputDateFormat.parse(dateString) ?: return ""
        return outputDateFormat.format(date)
    }

    private fun clickSendMessage(){
        binding.btChangeNewMessage.setOnClickListener {
            sendMessageDialog()
        }
    }

    private fun sendMessageDialog() {
        if (isFinishing) {
            return
        }
        val dialogBinding = ActivityCommunityChangeSendMessageBinding.inflate(layoutInflater)
        val dialogView = dialogBinding.root
        val builder = AlertDialog.Builder(this)

        builder.setView(dialogView)
        val alertDialog = builder.create()

        dialogBinding.btChangeSendMessage.setOnClickListener {
            val title = dialogBinding.etChangeSendTitle.toString()
            val text = dialogBinding.etChangeSendText.text.toString()

            // 입력된 이름과 내용을 사용하여 보내기 동작 수행
            send(title, text)
            alertDialog.dismiss()
        }

        dialogBinding.btChangeSendReturn.setOnClickListener {
            // 취소
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    private fun send(title: String, text: String) {
        // 여기에 실제로 보내기 동작을 구현
        // 예를 들어, 서버 API 호출이나 데이터 처리 등 구현
        // 예시로 Log 출력
        Log.d("Send", "제목: $title, 내용: $text")
    }
}