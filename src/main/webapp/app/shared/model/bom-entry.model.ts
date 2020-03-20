export interface IBomEntry {
  id?: number;
  material?: number;
  amount?: number;
  idId?: number;
  productIdId?: number;
}

export class BomEntry implements IBomEntry {
  constructor(public id?: number, public material?: number, public amount?: number, public idId?: number, public productIdId?: number) {}
}
