import { IObservation } from 'app/shared/model/observation.model';

export interface IAssemblyLine {
  id?: number;
  name?: string;
  description?: string;
  capacity?: number;
  planCapacity?: number;
  deviceId?: string;
  observations?: IObservation[];
  productName?: string;
  productId?: number;
}

export class AssemblyLine implements IAssemblyLine {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public capacity?: number,
    public planCapacity?: number,
    public deviceId?: string,
    public observations?: IObservation[],
    public productName?: string,
    public productId?: number
  ) {}
}
