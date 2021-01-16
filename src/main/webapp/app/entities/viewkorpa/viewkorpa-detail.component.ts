import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IViewkorpa } from 'app/shared/model/viewkorpa.model';

@Component({
  selector: 'jhi-viewkorpa-detail',
  templateUrl: './viewkorpa-detail.component.html',
})
export class ViewkorpaDetailComponent implements OnInit {
  viewkorpa: IViewkorpa | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ viewkorpa }) => (this.viewkorpa = viewkorpa));
  }

  previousState(): void {
    window.history.back();
  }
}
