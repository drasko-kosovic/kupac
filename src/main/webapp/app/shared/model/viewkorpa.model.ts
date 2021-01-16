export interface IViewkorpa {
  id?: number;
  artikal?: string;
  cijena?: number;
  ukupno?: number;
}

export class Viewkorpa implements IViewkorpa {
  constructor(public id?: number, public artikal?: string, public cijena?: number, public ukupno?: number) {}
}
