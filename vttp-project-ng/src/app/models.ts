export interface User {
    id: number;
    userDetailsId: number;
    displayName: string;
}

export interface Property {
    id: number;
    images: string;
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
    searchVal : string;
    currentPageNum : number;
    totalPages: number;
    addressList: Property[];
}

export interface Review {
    id: string;
    userId: number;
    propertyId: number;
    title: string;
    monthlyRentalCost: number;
    floor: string;
    apartmentFloorArea: string;
    rentalFloorArea: string;
    furnishings: string;
    sharedToilet: boolean;
    rules: string;
    rentalStartDate: string;
    rentalDuration: number;
    occupants: number;
    rating: number;
    comments: string;
    status: string;
    createdDate: string;
    updatedAt: string;
    user: User;
}