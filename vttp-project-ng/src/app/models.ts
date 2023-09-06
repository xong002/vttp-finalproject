export interface Property {
    id: number;
    areaId: number;
    searchValue : string;
    blkNo : string;
    roadName : string;
    building : string;
    address : string;
    postal: string;
    latitude : string;
    longitude : string;
}

export interface PropertyResponse {
    searchVal : string
    currentPageNum : number;
    totalPages: number;
    addressList: Property[];
}