import { IDog } from 'app/shared/model/dog.model';

export interface IColor {
    id?: number;
    name?: string;
    names?: IDog[];
}

export class Color implements IColor {
    constructor(public id?: number, public name?: string, public names?: IDog[]) {}
}
