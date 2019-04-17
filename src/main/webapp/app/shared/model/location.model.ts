import { ICenter } from 'app/shared/model/center.model';
import { IBreeding } from 'app/shared/model/breeding.model';
import { IFamilly } from 'app/shared/model/familly.model';

export interface ILocation {
    id?: number;
    street?: string;
    postalCode?: string;
    city?: string;
    state?: string;
    phone?: string;
    mail?: string;
    centers?: ICenter[];
    breedings?: IBreeding[];
    famillies?: IFamilly[];
}

export class Location implements ILocation {
    constructor(
        public id?: number,
        public street?: string,
        public postalCode?: string,
        public city?: string,
        public state?: string,
        public phone?: string,
        public mail?: string,
        public centers?: ICenter[],
        public breedings?: IBreeding[],
        public famillies?: IFamilly[]
    ) {}
}
