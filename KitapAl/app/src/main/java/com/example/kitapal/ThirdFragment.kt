import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.kitapal.MainActivity
import com.example.kitapal.R
import com.example.kitapal.databinding.FragmentThirdBinding
import com.example.kitapal.models.User

class ThirdFragment:Fragment(R.layout.fragment_third) {

    private var _binding : FragmentThirdBinding? = null
    private val binding get() = _binding!!
    val userDAO = MainActivity.database.getUserDAO()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThirdBinding.inflate(layoutInflater, container, false)
        val view: View = inflater.inflate(R.layout.fragment_third, container, false)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()

        val changeButton = view.findViewById<Button>(R.id.change_button)
        changeButton.setOnClickListener {
            val username = view.findViewById<EditText>(R.id.username).text.toString()
            val email = view.findViewById<EditText>(R.id.email).text.toString()
            val password = view.findViewById<EditText>(R.id.password).text.toString()
            userDAO.updateUser(MainActivity.user.id, username, email, password)
        }

        val logoutButton = view.findViewById<Button>(R.id.logout_button)
        logoutButton.setOnClickListener {
            MainActivity.user = User(0, "", "", "")
            MainActivity.loggedIn = false
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }

        val removeButton = view.findViewById<Button>(R.id.remove)
        removeButton.setOnClickListener {
            userDAO.removeUser(MainActivity.user.id)
            MainActivity.user = User(0, "", "", "")
            MainActivity.loggedIn = false
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupUI(){
        with(binding){
            username.setText(MainActivity.user.username)
            email.setText(MainActivity.user.email)
            password.setText(MainActivity.user.password)
        }
    }
}
