export interface IKorpa {
  id?: number;
  artikal?: string;
  cijena?: number;
}

export class Korpa implements IKorpa {
  constructor(public id?: number, public artikal?: string, public cijena?: number) {}
}
