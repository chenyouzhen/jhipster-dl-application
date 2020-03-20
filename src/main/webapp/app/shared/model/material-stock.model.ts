export interface IMaterialStock {
  id?: number;
  quantity?: number;
  unit?: string;
  materialIdId?: number;
}

export class MaterialStock implements IMaterialStock {
  constructor(public id?: number, public quantity?: number, public unit?: string, public materialIdId?: number) {}
}
