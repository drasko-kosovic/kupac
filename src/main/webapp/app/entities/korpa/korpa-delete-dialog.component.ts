import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IKorpa } from 'app/shared/model/korpa.model';
import { KorpaService } from './korpa.service';

@Component({
  templateUrl: './korpa-delete-dialog.component.html',
})
export class KorpaDeleteDialogComponent {
  korpa?: IKorpa;

  constructor(protected korpaService: KorpaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.korpaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('korpaListModification');
      this.activeModal.close();
    });
  }
}
