import { Component } from '@angular/core';
import {BookRequest} from '../../../../services/models/book-request';
import {BookService} from '../../../../services/services/book.service';
import {Router} from '@angular/router';
@Component({
  selector: 'app-manage-book',
  templateUrl: './manage-book.component.html',
  styleUrls: ['./manage-book.component.scss']
})
export class ManageBookComponent {
  constructor(
  private bookService: BookService,
  private router: Router
  ){}

  errorMsg: Array<string> = [];
  selectedPicture: string | undefined;
  selectedBookCover: any;
  bookRequest: BookRequest = {
      authorName: '',
      isbn: '',
      synopsis: '',
      title: ''
  };

    onFileSelected(event: any){
       this.selectedBookCover = event.target.files[0];
       console.log(this.selectedBookCover);

        if(this.selectedBookCover){
        const reader = new FileReader();
          reader.onload = () =>{
          this.selectedPicture = reader.result as string;
          }
          reader.readAsDataURL(this.selectedBookCover);
            }
      }

       saveBook(){
        this.bookService.saveBook({
          body: this.bookRequest
          }).subscribe({
        next: (bookId:number) => {
 this.bookService.uploadCoverPic({
'bookId':bookId,
body: {
 file: this.selectedBookCover
  }
}).subscribe({
 next: () => {
this.router.navigate(['/books/my-books']);
    }
})
                  },
error: (err) =>{
this.errorMsg = err.error.validationErrors;
}
              });
          }
}
