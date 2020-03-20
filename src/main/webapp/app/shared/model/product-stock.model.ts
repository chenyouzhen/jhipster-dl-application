export interface IProductStock {
  id?: number;
  quantity?: number;
  unit?: string;
  productIdId?: number;
}

export class ProductStock implements IProductStock {
  constructor(public id?: number, public quantity?: number, public unit?: string, public productIdId?: number) {}
}
