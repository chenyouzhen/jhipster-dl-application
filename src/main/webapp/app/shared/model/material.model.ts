export interface IMaterial {
  id?: number;
  name?: string;
  unit?: string;
  description?: string;
  idId?: number;
  bomEntryIdId?: number;
}

export class Material implements IMaterial {
  constructor(
    public id?: number,
    public name?: string,
    public unit?: string,
    public description?: string,
    public idId?: number,
    public bomEntryIdId?: number
  ) {}
}
