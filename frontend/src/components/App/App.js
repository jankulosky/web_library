import './App.css';
import React, {Component} from "react";
import {BrowserRouter as Router, Redirect, Route} from 'react-router-dom'
import Authors from '../Authors/authors';
import Categories from '../Categories/categories';
import Books from '../Books/BookList/books';
import Header from '../Header/header';
import BookAdd from '../Books/BookAdd/bookAdd';
import ELibraryService from "../../repository/elibraryRepository";
import BookEdit from "../Books/BookEdit/bookEdit";

class App extends Component {

  constructor(props) {
    super(props);
    this.state = {
      authors: [],
      books: [],
      categories: [],
      selectedBook: {}
    }
  }

  render() {
    return (
        <Router>
          <Header/>
          <main>
            <div className="container">
              <Route path={"/authors"} exact render={() =>
                  <Authors authors={this.state.authors}/>}/>
              <Route path={"/categories"} exact render={() =>
                  <Categories categories={this.state.categories}/>}/>
              <Route path={"/books/add"} exact render={() =>
                  <BookAdd categories={this.state.categories}
                              authors={this.state.authors}
                              onAddBook={this.addBook}/>}/>
              <Route path={"/books/edit/:id"} exact render={() =>
                  <BookEdit categories={this.state.categories}
                               authors={this.state.authors}
                               onEditBook={this.editBook}
                               book={this.state.selectedBook}/>}/>
              <Route path={"/books"} exact render={() =>
                  <Books books={this.state.books}
                            onDelete={this.deleteBook}
                            onEdit={this.getBook}/>}/>
            </div>
          </main>
        </Router>
    );
  }

  componentDidMount() {
    this.loadAuthors();
    this.loadCategories();
    this.loadBooks();
  }

  loadAuthors = () => {
    ELibraryService.fetchAuthors()
        .then((data) => {
          this.setState({
            authors: data.data
          })
        });
  }

  loadBooks = () => {
    ELibraryService.fetchBooks()
        .then((data) => {
          this.setState({
            books: data.data
          })
        });
  }

  loadCategories = () => {
    ELibraryService.fetchCategories()
        .then((data) => {
          this.setState({
            categories: data.data
          })
        });
  }

  deleteBook = (id) => {
    ELibraryService.deleteBook(id)
        .then(() => {
          this.loadBooks();
        });
  }

  addBook = (id, name, category, author, availableCopies) => {
    ELibraryService.addBook(id, name, category, author, availableCopies)
        .then(() => {
          this.loadBooks();
        });
  }

  getBook = (id) => {
    ELibraryService.getBook(id)
        .then((data) => {
          this.setState({
            selectedBook: data.data
          })
        })
  }

  editProduct = (id, name, category, author, availableCopies) => {
    ELibraryService.editBook(id, name, category, author, availableCopies)
        .then(() => {
          this.loadBooks();
        });
  }
}

export default App;
