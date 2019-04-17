import { Moment } from 'moment';
import { IDog } from 'app/shared/model/dog.model';

export interface IWeight {
    id?: number;
    date?: Moment;
    weight?: number;
    qtyRation?: number;
    nbRation?: number;
    observation?: string;
    dog?: IDog;
}

export class Weight implements IWeight {
    constructor(
        public id?: number,
        public date?: Moment,
        public weight?: number,
        public qtyRation?: number,
        public nbRation?: number,
        public observation?: string,
        public dog?: IDog
    ) {}
}
