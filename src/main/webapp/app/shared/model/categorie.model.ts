import { ICategorie } from 'app/shared/model/categorie.model';
import { ICriteria } from 'app/shared/model/criteria.model';

export interface ICategorie {
    id?: number;
    name?: string;
    description?: string;
    header?: boolean;
    weight?: number;
    resultat?: number;
    comment?: string;
    names?: ICategorie[];
    criteria?: ICriteria[];
    subcat?: ICategorie;
}

export class Categorie implements ICategorie {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public header?: boolean,
        public weight?: number,
        public resultat?: number,
        public comment?: string,
        public names?: ICategorie[],
        public criteria?: ICriteria[],
        public subcat?: ICategorie
    ) {
        this.header = this.header || false;
    }
}
