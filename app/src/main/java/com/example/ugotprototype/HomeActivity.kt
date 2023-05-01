package com.example.ugotprototype

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.databinding.adapters.AdapterViewBindingAdapter.setOnItemSelectedListener
import androidx.fragment.app.Fragment
import com.example.ugotprototype.databinding.ActivityHomeBinding
import com.example.ugotprototype.databinding.ActivityMainBinding
import com.example.ugotprototype.fragment.CommunityFragment
import com.example.ugotprototype.fragment.GroupFragment
import com.example.ugotprototype.fragment.ProfileFragment
import com.example.ugotprototype.fragment.StudyFragment
import com.example.ugotprototype.fragment.TeamFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    // 이동할 Fragment 변수
    private val teamFragment = TeamFragment()
    private val studyFragment = StudyFragment()
    private val groupFragment = GroupFragment()
    private val profileFragment = ProfileFragment()
    private val communityFragment = CommunityFragment()

    // 바인딩 객체 선언
    private var viewBinding: ActivityHomeBinding? = null
    // null체크 편의성을 위한 바인딩 변수 재선언
    private val binding get() = viewBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 초기화면셋팅
        binding.navMenuBar.setSelectedItemId(R.id.nav_menu_team)
        changeFragment(TeamFragment());

        // 화면전환 관련 함수
        initNav()
    }
    private fun initNav()
    {
        binding.navMenuBar.setOnItemSelectedListener { name ->
            when(name.itemId)
            {
                R.id.nav_menu_community -> { changeFragment(CommunityFragment()) }
                R.id.nav_menu_team      -> { changeFragment(TeamFragment())      }
                R.id.nav_menu_group     -> { changeFragment(GroupFragment())     }
                R.id.nav_menu_profile   -> { changeFragment(ProfileFragment())   }
                R.id.nav_menu_study     -> { changeFragment(StudyFragment())     }
            }
            true
        }
    }
    private fun changeFragment(fragment: Fragment)
    {
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_view_layout, fragment)
            .commit()
    }
}