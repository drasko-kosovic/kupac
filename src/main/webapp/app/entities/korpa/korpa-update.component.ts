import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IKorpa, Korpa } from 'app/shared/model/korpa.model';
import { KorpaService } from './korpa.service';

@Component({
  selector: 'jhi-korpa-update',
  templateUrl: './korpa-update.component.html',
})
export class KorpaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    artikal: [null, [Validators.required]],
    cijena: [null, [Validators.required]],
    izaberi: [],
  });

  constructor(protected korpaService: KorpaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ korpa }) => {
      this.updateForm(korpa);
    });
  }

  updateForm(korpa: IKorpa): void {
    this.editForm.patchValue({
      id: korpa.id,
      artikal: korpa.artikal,
      cijena: korpa.cijena,
      izaberi: korpa.izaberi,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const korpa = this.createFromForm();
    if (korpa.id !== undefined) {
      this.subscribeToSaveResponse(this.korpaService.update(korpa));
    } else {
      this.subscribeToSaveResponse(this.korpaService.create(korpa));
    }
  }

  private createFromForm(): IKorpa {
    return {
      ...new Korpa(),
      id: this.editForm.get(['id'])!.value,
      artikal: this.editForm.get(['artikal'])!.value,
      cijena: this.editForm.get(['cijena'])!.value,
      izaberi: this.editForm.get(['izaberi'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKorpa>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
