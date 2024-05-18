package com.example.kitapal.adapters

import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kitapal.BookDetailActivity
import com.example.kitapal.R
import com.example.kitapal.databinding.BookItemBinding
import com.example.kitapal.models.Book
import com.squareup.picasso.Picasso

class HomeAdapter(private val handleClick: (String) -> Unit): RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private val books: ArrayList<Book> = arrayListOf()

    fun setBooks(booksList: List<Book>) {
        val diffResult = DiffUtil.calculateDiff(BookDiffCallBack(books, booksList))
        books.clear()
        books.addAll(booksList)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(
        private val binding: BookItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context

        fun bind(book: Book) {

            with(binding) {
                val coverLink = book.volumeInfo.imageLinks.thumbnail
                Glide.with(context).load(coverLink.slice(0..3)+"s"+coverLink.slice(4..coverLink.length-1)).into(cover)
                title.text = book.volumeInfo.title
                var authorsStr: String = ""
                if(book.volumeInfo.authors != null) {
                    if (book.volumeInfo.authors.size != 1) {
                        for (author in book.volumeInfo.authors) {
                            authorsStr = authorsStr + author + ", "
                        }
                        authorsStr = authorsStr.slice(0..authorsStr.length - 3)
                    } else {
                        authorsStr = book.volumeInfo.authors.elementAt(0).toString()
                    }
                }
                authors.text = authorsStr
                var categoriesStr: String = ""
                if(book.volumeInfo.categories != null) {
                    if (book.volumeInfo.categories.size != 1) {
                        for (category in book.volumeInfo.categories) {
                            categoriesStr = categoriesStr + category + ", "
                        }
                        categoriesStr = categoriesStr.slice(0..categoriesStr.length - 3)
                    } else {
                        categoriesStr = book.volumeInfo.categories.elementAt(0).toString()
                    }
                }
                categories.text = categoriesStr
                root.setOnClickListener{
                    handleClick(book.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            BookItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int = books.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(books[position])
    }

    class BookDiffCallBack(
        private val oldList: List<Book>,
        private val newList: List<Book>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }
}