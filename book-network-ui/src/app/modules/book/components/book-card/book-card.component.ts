import { Component, Input,Output, EventEmitter } from '@angular/core';
import {BookResponse} from '../../../../services/models/book-response';

@Component({
  selector: 'app-book-card',
  templateUrl: './book-card.component.html',
  styleUrls: ['./book-card.component.scss']
})
export class BookCardComponent{

    private _book: BookResponse = {};
    private _bookCover: string | undefined;
    private _manage: boolean = false;

            @Input()
            set book(value: BookResponse) {
                this._book = value;
              }
          //@Input since we'll require the setter as an input
          //the input is passed on the book-list.component.html

            get book(): BookResponse {
              return this._book;
            }

           get bookCover(): string | undefined {
             if(this._book.cover){
                     return 'data:image/jpg;base64,' + this._book.cover;
               }
             return this._bookCover;
            }

            @Input()
             set manage(value: boolean){
              this._manage = value;
            }

            get manage(): boolean{
            return this._manage;
            }

        @Output() private share: EventEmitter<BookResponse> = new EventEmitter<BookResponse>();
        @Output() private archive: EventEmitter<BookResponse> = new EventEmitter<BookResponse>();
        @Output() private addToWaitingList: EventEmitter<BookResponse> = new EventEmitter<BookResponse>();
        @Output() private borrow: EventEmitter<BookResponse> = new EventEmitter<BookResponse>();
        @Output() private edit: EventEmitter<BookResponse> = new EventEmitter<BookResponse>();
        @Output() private details: EventEmitter<BookResponse> = new EventEmitter<BookResponse>();

          onBorrow(){
          this.borrow.emit(this._book);
              }

          onShowDetails(){
            this.details.emit(this._book);
              }

          onAddToWaitingList(){
          this.addToWaitingList.emit(this._book);
              }

          onEdit(){
          this.edit.emit(this._book);
          }

          onShare(){
          this.share.emit(this._book);
          }

          onArchive(){
          this.archive.emit(this._book);
          }
}
