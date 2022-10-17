import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-dialog-message',
  templateUrl: './dialog-message.component.html',
  styleUrls: ['./dialog-message.component.scss']
})
export class DialogMessageComponent implements OnInit {

    title: string;
    description: string;

    constructor(
        public dialogRef: MatDialogRef<DialogMessageComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any
    ) { }

    ngOnInit(): void {
        this.title = this.data.title;
        this.description = this.data.description;
    }

    onClose() {
        this.dialogRef.close();
    }
}