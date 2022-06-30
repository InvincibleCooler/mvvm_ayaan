package ayaan.mvvmayaan

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ayaan.mvvmayaan.databinding.BookItemBinding
import ayaan.mvvmayaan.databinding.FragmentMainBinding
import ayaan.mvvmayaan.net.res.BookInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    companion object {
        private const val TAG = "MainFragment"
    }

    // view bind
    private var _binding: FragmentMainBinding? = null
//
    private var adapter: RecyclerTextAdapter? = null

    private val viewModel: MainViewModel by viewModels()

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RecyclerTextAdapter()

        _binding?.btnClick?.setOnClickListener {
            val title = _binding?.etTitle?.text.toString()
            viewModel.getBookList(title)
        }

        _binding?.recyclerview?.adapter = adapter
        _binding?.recyclerview?.layoutManager = LinearLayoutManager(context)

        viewModel.searchRes.observe(viewLifecycleOwner) {
            val bookList = it?.books

            adapter?.clear()
            adapter?.setData(bookList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        // test up
    }

    class RecyclerTextAdapter: RecyclerView.Adapter<RecyclerTextAdapter.ViewHolder>() {
        private var mData: ArrayList<BookInfo>? = null

        @SuppressLint("NotifyDataSetChanged")
        fun setData(list: ArrayList<BookInfo>?) {
            mData = list
            notifyDataSetChanged()
        }

        fun clear() {
            mData?.clear()
        }

        // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                BookItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(mData?.get(position))
        }

        // getItemCount() - 전체 데이터 갯수 리턴.
        override fun getItemCount(): Int {
            return mData?.size ?: 0
        }


        // 아이템 뷰를 저장하는 뷰홀더 클래스.
        inner class ViewHolder constructor(private var binding: BookItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(bookInfo: BookInfo?) {
               binding.title.text = bookInfo?.title ?: ""
            }
        }
    }
}