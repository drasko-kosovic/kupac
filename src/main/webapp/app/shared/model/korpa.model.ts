export interface IKorpa {
  id?: number;
  artikal?: string;
  cijena?: number;
  izaberi?: boolean;
}

export class Korpa implements IKorpa {
  constructor(public id?: number, public artikal?: string, public cijena?: number, public izaberi?: boolean) {
    this.izaberi = this.izaberi || false;
  }
}
