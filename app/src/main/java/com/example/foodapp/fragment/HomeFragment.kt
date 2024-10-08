package com.example.foodapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodapp.activity.MealActivity
import com.example.foodapp.data.model.Meal
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.viewmodel.HomeViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var randomMeal: Meal

    companion object{
        const val MEAL_ID = "com.example.foodapp.fragment.idMeal"
        const val MEAL_NAME = "com.example.foodapp.fragment.nameMeal"
        const val MEAL_THUMB = "com.example.foodapp.fragment.thumbMeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            homeViewModel.getRandomMeal()
            observerRandomMeal()
        onRandomMealClick()

    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        homeViewModel.observerRandomMealLivedata().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)
            this.randomMeal = meal
        }
    }

}