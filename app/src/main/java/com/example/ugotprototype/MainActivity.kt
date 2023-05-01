package com.example.ugotprototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ugotprototype.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // 바인딩 객체 선언
    private var viewBinding: ActivityMainBinding? = null
    // null체크 편의성을 위한 바인딩 변수 재선언
    private val binding get() = viewBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 뷰 객체화
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btGohomeactivity.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish();
        }




    }
}