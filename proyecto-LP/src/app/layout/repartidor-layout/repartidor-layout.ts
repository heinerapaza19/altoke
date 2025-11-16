import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'repartidor-layout',
  standalone: true,
  imports: [RouterLink, RouterOutlet, CommonModule],
  templateUrl: './repartidor-layout.html',
  styleUrls: ['./repartidor-layout.css']
})
export class RepartidorLayout {}
