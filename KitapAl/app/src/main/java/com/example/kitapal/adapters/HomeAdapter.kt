package com.example.kitapal.adapters

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kitapal.R
import com.example.kitapal.databinding.BookItemBinding
import com.example.kitapal.models.Book
import com.squareup.picasso.Picasso

class HomeAdapter(): RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private val books: ArrayList<Book> = arrayListOf()

    fun setBooks(booksList: List<Book>) {
        val diffResult = DiffUtil.calculateDiff(BookDiffCallBack(books, booksList))
        books.clear()
        books.addAll(booksList)
        diffResult.dispatchUpdatesTo(this)
        println("ADAPTER")
        println(books)
    }

    inner class ViewHolder(
        private val binding: BookItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context

        fun bind(book: Book) {

            with(binding) {
                Glide.with(context).load(book.volumeInfo.imageLinks.smallThumbnail).into(cover)
                title.text = book.volumeInfo.title
                description.text = book.volumeInfo.description
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
            return oldList[oldItemPosition].volumeInfo.title == newList[newItemPosition].volumeInfo.title
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }
}