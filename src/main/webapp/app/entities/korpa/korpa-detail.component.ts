import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKorpa } from 'app/shared/model/korpa.model';

@Component({
  selector: 'jhi-korpa-detail',
  templateUrl: './korpa-detail.component.html',
})
export class KorpaDetailComponent implements OnInit {
  korpa: IKorpa | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ korpa }) => (this.korpa = korpa));
  }

  previousState(): void {
    window.history.back();
  }
}
