import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kitapal.BookDetailActivity
import com.example.kitapal.MainActivity
import com.example.kitapal.R
import com.example.kitapal.SearchActivity
import com.example.kitapal.adapters.HomeAdapter
import com.example.kitapal.adapters.SearchAdapter
import com.example.kitapal.api.BookApi
import com.example.kitapal.databinding.FragmentFirstBinding
import com.example.kitapal.databinding.SearchViewBinding
import com.example.kitapal.models.ApiResponse
import com.example.kitapal.models.Book
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FirstFragment:Fragment(R.layout.fragment_first) {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy {
        HomeAdapter(handleClick = {handleClick(it)})
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?) : View? {
        _binding = FragmentFirstBinding.inflate(layoutInflater, container, false)
        val view: View = inflater.inflate(R.layout.fragment_first, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val client = BookApi.instance
        val response = client.getBooks("Subject:Fiction")

        val button = view.findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            startActivity(intent)
        }

        setupUI()

        response.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {

                println("HttpResponse: ${response.body()}")

                var list = response.body()?.books

                list = list?.filter { it.volumeInfo.title != null}

                println(list)

                if (list != null) {
                    adapter?.setBooks(
                        booksList = list.map { it }
                    )
                }
                println(adapter.itemCount)
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                println("HttpResponse: ${t.message}")
            }
        })

    }

    private fun setupUI() {
        with(binding) {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(context)


        }
    }

    private fun handleClick(id: String) {
        val intent = Intent(context, BookDetailActivity::class.java)
        intent.putExtra("book", id)
        startActivity(intent)
    }


}

