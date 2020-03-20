export interface IDemand {
  id?: number;
  demandId?: string;
  quantity?: number;
  productIdId?: number;
  orderName?: string;
  orderId?: number;
}

export class Demand implements IDemand {
  constructor(
    public id?: number,
    public demandId?: string,
    public quantity?: number,
    public productIdId?: number,
    public orderName?: string,
    public orderId?: number
  ) {}
}
