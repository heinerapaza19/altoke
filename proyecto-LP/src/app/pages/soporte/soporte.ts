import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-soporte',
  imports: [CommonModule],
  templateUrl: './soporte.html',
  styleUrl: './soporte.css',
})
export class Soporte implements OnInit {
  faqOpen: number | null = null;

  ngOnInit() {}

  toggleFaq(index: number) {
    this.faqOpen = this.faqOpen === index ? null : index;
  }
}

