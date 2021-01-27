import { Component, OnInit, OnDestroy, Inject } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IKorpa } from 'app/shared/model/korpa.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { KorpaService } from './korpa.service';
import { KorpaDeleteDialogComponent } from './korpa-delete-dialog.component';
import { DOCUMENT } from '@angular/common';
import jspdf from 'jspdf';
import 'jspdf-autotable';

@Component({
  selector: 'jhi-korpa',
  templateUrl: './korpa.component.html',
  styleUrls: ['./korpa.component.scss'],
})
export class KorpaComponent implements OnInit, OnDestroy {
  korpas?: IKorpa[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  artikal: any;
  cijena: any;

  constructor(
    protected korpaService: KorpaService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    @Inject(DOCUMENT) private document: Document
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.korpaService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IKorpa[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInKorpas();
  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    }).subscribe();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IKorpa): number {
    return item.id!;
  }

  registerChangeInKorpas(): void {
    this.eventSubscriber = this.eventManager.subscribe('korpaListModification', () => this.loadPage());
  }

  delete(korpa: IKorpa): void {
    const modalRef = this.modalService.open(KorpaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.korpa = korpa;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IKorpa[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/korpa'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.korpas = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }

  artikalSearch(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.korpaService
      .query({
        'artikal.in': this.artikal,
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IKorpa[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  cijenaSearch(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.korpaService
      .query({
        'cijena.in': this.cijena,
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IKorpa[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }
  prazanArtikal(): void {
    this.artikal = '';
    this.loadPage();
  }

  prazanCijena(): void {
    this.cijena = '';
    this.loadPage();
    // window.location.reload();
  }

  exelArtikal(): void {
    if (this.artikal === undefined) {
      this.document.location.href = 'http://localhost:8080/excel/download';
    } else {
      this.document.location.href = 'http://localhost:8080/excel/download/artikal/' + this.artikal;
    }
  }

  exelCijena(): void {
    //   if (this.cijena === undefined) {
    //     this.document.location.href = 'http://localhost:8080/excel/download';
    //   } else {
    //     this.document.location.href = 'http://localhost:8080/excel/download/cijena/' + this.cijena;
    //   }
    // }
    if (this.cijena === undefined && this.artikal !== undefined) {
      // eslint-disable-next-line no-console
      console.log('polje cijena je prazno a polje artikal je puno zato dajem samo potrgu za artikl exel');
    }

    if (this.artikal === undefined && this.cijena !== undefined) {
      // eslint-disable-next-line no-console
      console.log('polje cijena je puno a polje artikal je prazno zato dajem samo potrgu za cijenu exel');
    }
  }

  exelArtikalCijena(): void {
    // this.document.location.href = 'http://localhost:8080/api/artikalcijena?artikal=' + this.artikal+'& cijena='+ this.cijena;
    this.document.location.href = 'http://localhost:8080/excel/download/artikalcijena?artikal=' + this.artikal + '&cijena=' + this.cijena;
  }

  createPdf(): void {
    const doc = new jspdf('landscape', 'px', 'a4');
    doc.setFontSize(18);
    doc.text('Korpa', 11, 8);
    doc.setFontSize(11);
    doc.setTextColor(100);

    (doc as any).autoTable({
      head: [['ID', 'Artikal', 'Cijena', 'Izaberi']],

      theme: 'striped',
    });
    (doc as any).autoTable({
      body: this.korpas,
    });

    doc.output('dataurlnewwindow');
    doc.autoPrint();
  }
}
