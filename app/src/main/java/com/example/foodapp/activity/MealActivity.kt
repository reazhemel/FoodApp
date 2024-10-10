package com.example.foodapp.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.data.model.Meal
import com.example.foodapp.databinding.ActivityMealBinding
import com.example.foodapp.fragment.HomeFragment
import com.example.foodapp.viewmodel.MealViewModel

class MealActivity : AppCompatActivity() {

    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var youTubeLink: String
    private lateinit var binding: ActivityMealBinding

    private lateinit var mealViewModel: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealViewModel = ViewModelProvider(this)[MealViewModel::class.java]

        getMealInformationFromIntent()
        setInformationInViews()

        loadingCase()

        mealViewModel.getMealDetails(mealId)
        observerMealDetailsLivedata()

        onYoutubeImageClick()

    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youTubeLink))
            startActivity(intent)
        }
    }

    private fun observerMealDetailsLivedata() {
        mealViewModel.observerMealDetailsLiveData().observe(this, object : Observer<Meal>{
            override fun onChanged(value: Meal) {
                onResponseCase()
                val meal = value

                binding.tvCategoryFood.text = "Category: ${meal!!.strCategory}"
                binding.tvAreaLocation.text = "Area: ${meal!!.strArea}"
                binding.tvDescriptions.text = meal.strInstructions

                youTubeLink = meal.strYoutube
            }

        })
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))

    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.floatingBtn.visibility = View.INVISIBLE
        binding.tvCategoryFood.visibility = View.INVISIBLE
        binding.tvAreaLocation.visibility = View.INVISIBLE
        binding.tvDescriptions.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.floatingBtn.visibility = View.VISIBLE
        binding.tvCategoryFood.visibility = View.VISIBLE
        binding.tvAreaLocation.visibility = View.VISIBLE
        binding.tvDescriptions.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }
}