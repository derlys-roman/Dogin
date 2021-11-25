package br.com.alura.dogin.activities

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.alura.dogin.adapter.RecyclerAdapter
import br.com.alura.dogin.databinding.ActivityMainBinding
import br.com.alura.dogin.model.APIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private var dogList = mutableListOf<String>()
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        searchByName("husky")
        binding.searchviewMainActivity.setOnQueryTextListener(this)
        initRecycler()
    }

    private fun initRecycler() {
        adapter = RecyclerAdapter(dogList)
        binding.recyclerviewMainActivity.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewMainActivity.adapter = adapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/    ")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchByName(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getDogsByBrands("$query/images")
            val puppies = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    val images: List<String> = puppies?.images ?: emptyList()
                    dogList.clear()
                    dogList.addAll(images)
                    adapter.notifyDataSetChanged()
                } else {
                    showError()
                }

                hideKeyBoard()
            }

        }
    }

    private fun hideKeyBoard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.maid.windowToken, 0)
    }

    private fun showError() {
        Toast.makeText(this, "Not found", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            searchByName(query = query.lowercase(Locale.getDefault()))
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

}