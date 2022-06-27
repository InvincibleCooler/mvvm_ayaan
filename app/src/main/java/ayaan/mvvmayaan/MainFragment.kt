package ayaan.mvvmayaan

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ayaan.mvvmayaan.databinding.FragmentMainBinding
import ayaan.mvvmayaan.net.RequestManager
import ayaan.mvvmayaan.net.data.BookRepository


class MainFragment : Fragment() {
    companion object {
        private const val TAG = "MainFragment"
    }

    // view bind
    private var _binding: FragmentMainBinding? = null

    private val serviceApi = RequestManager.getServiceApi(null)
    private val repository = BookRepository(serviceApi)

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(repository)
    }

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.btnClick?.setOnClickListener {
            val title = _binding?.etTitle?.text.toString()
            repository.getBookList(title)
        }

        viewModel.searchRes.observe(viewLifecycleOwner) {
            val bookList = it?.books

            bookList?.let {
                for (a in it) {
                    Log.i("kim", "title : " + a.title)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        // test up
    }
}