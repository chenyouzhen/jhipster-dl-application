export interface ILogistics {
  id?: number;
  logisticsId?: string;
  expressCompany?: string;
  expressNumber?: string;
  status?: string;
  startPosition?: string;
  endPosition?: string;
  currentPosition?: string;
  orderIdId?: number;
}

export class Logistics implements ILogistics {
  constructor(
    public id?: number,
    public logisticsId?: string,
    public expressCompany?: string,
    public expressNumber?: string,
    public status?: string,
    public startPosition?: string,
    public endPosition?: string,
    public currentPosition?: string,
    public orderIdId?: number
  ) {}
}
