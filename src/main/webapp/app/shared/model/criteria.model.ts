import { ICategorie } from 'app/shared/model/categorie.model';
import { IBilanCheck } from 'app/shared/model/bilan-check.model';

export interface ICriteria {
    id?: number;
    name?: string;
    description?: string;
    weight?: number;
    value?: number;
    comment?: string;
    categories?: ICategorie[];
    bilanChecks?: IBilanCheck[];
}

export class Criteria implements ICriteria {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public weight?: number,
        public value?: number,
        public comment?: string,
        public categories?: ICategorie[],
        public bilanChecks?: IBilanCheck[]
    ) {}
}
