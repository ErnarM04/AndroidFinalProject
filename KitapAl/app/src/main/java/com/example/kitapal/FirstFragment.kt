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
import com.example.kitapal.MainActivity
import com.example.kitapal.R
import com.example.kitapal.SearchActivity
import com.example.kitapal.adapters.HomeAdapter
import com.example.kitapal.api.BookApi
import com.example.kitapal.databinding.FragmentFirstBinding
import com.example.kitapal.databinding.SearchViewBinding
import com.example.kitapal.models.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FirstFragment:Fragment(R.layout.fragment_first) {

    companion object{
        fun newInstance() = HomeAdapter()
    }

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy {
        HomeAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?) : View? {
        _binding = FragmentFirstBinding.inflate(layoutInflater, container, false)
        val view: View = inflater.inflate(R.layout.fragment_first, container, false)
        val button = view.findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val transaction = childFragmentManager.beginTransaction()
            val searchActivity : SearchActivity = SearchActivity()
            transaction.replace(R.id.fragmentContainerView, searchActivity)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val client = BookApi.instance
        val response = client.getBooks("Subject:Fiction")

        setupUI()

        response.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {

                println("HttpResponse: ${response.body()}")

                var list = response.body()?.books

                list = list?.filter { it.volumeInfo.title != null }

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

}

