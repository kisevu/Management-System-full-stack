import { Component, OnInit } from '@angular/core';
import {PageResponseBorrowedBookResponse} from '../../../../services/models/page-response-borrowed-book-response';
import {BorrowedBookResponse} from '../../../../services/models/borrowed-book-response';
import {BookService} from '../../../../services/services/book.service';
import {BookResponse} from '../../../../services/models/book-response';
@Component({
  selector: 'app-return-books',
  templateUrl: './return-books.component.html',
  styleUrls: ['./return-books.component.scss']
})
export class ReturnBooksComponent implements OnInit{
    returnedBooks: PageResponseBorrowedBookResponse= {};
    page = 0;
    size = 5;
    pages: any = [];
    message: string = '';
    level: string = 'success';

      constructor(
         private bookService: BookService,
        ){}

          ngOnInit(){
            this.findAllReturnedBooks();
          }

        private findAllReturnedBooks(){
        this.bookService.findAllReturnedBooks({
               page: this.page,
               size: this.size
             }).subscribe({
            next: (response)=> {
              this.returnedBooks = response;
                          }
                     })
          }


         goToFirstPage(){
             this.page = 0;
             this.findAllReturnedBooks();
             }

        goToPreviousPage(){ this.page--; this.findAllReturnedBooks();}

        gotToPage(page: number){this.page=page; this.findAllReturnedBooks();}

        goToNextPage(){ this.page++; this.findAllReturnedBooks();}

        goToLastPage(){ this.page = this.returnedBooks.totalPages as number -1; this.findAllReturnedBooks();}

        get isLastPage() : boolean {
         return this.page == this.returnedBooks.totalPages as number -1;
          }

         approveBookReturn(book:BorrowedBookResponse){
            if(!book.returned){
              this.level='error';
              this.message='Book is not yet returned.';
              return;
             }
            this.bookService.approveReturnBorrowedBook({
            'bookId': book.id as number
                      }).subscribe({
                          next: () =>{
                              this.level='success';
                              this.message='Book return approved.';
                              this.findAllReturnedBooks();
                                        }
                           });
          }
}
