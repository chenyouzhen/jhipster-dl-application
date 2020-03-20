import { IBomEntry } from 'app/shared/model/bom-entry.model';
import { IDemand } from 'app/shared/model/demand.model';
import { IAssemblyLine } from 'app/shared/model/assembly-line.model';
import { IRecord } from 'app/shared/model/record.model';

export interface IProduct {
  id?: number;
  name?: string;
  category?: string;
  model?: string;
  description?: string;
  idId?: number;
  ids?: IBomEntry[];
  ids?: IDemand[];
  assemblylines?: IAssemblyLine[];
  records?: IRecord[];
  factoryName?: string;
  factoryId?: number;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public name?: string,
    public category?: string,
    public model?: string,
    public description?: string,
    public idId?: number,
    public ids?: IBomEntry[],
    public ids?: IDemand[],
    public assemblylines?: IAssemblyLine[],
    public records?: IRecord[],
    public factoryName?: string,
    public factoryId?: number
  ) {}
}
