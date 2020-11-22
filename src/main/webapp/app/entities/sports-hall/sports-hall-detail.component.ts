import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISportsHall } from 'app/shared/model/sports-hall.model';

@Component({
  selector: 'jhi-sports-hall-detail',
  templateUrl: './sports-hall-detail.component.html',
})
export class SportsHallDetailComponent implements OnInit {
  sportsHall: ISportsHall | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sportsHall }) => (this.sportsHall = sportsHall));
  }

  previousState(): void {
    window.history.back();
  }
}
