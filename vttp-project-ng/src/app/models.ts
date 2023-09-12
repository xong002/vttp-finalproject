export interface User {
    id: number;
    userDetailsId: number;
    displayName: string;
}

export interface UserDetailsInput {
    email: string;
    password: string;
    role: string;
    status: string;
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
    reviewCount: number;
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
    building: string;
    images: string;
}

export interface AuthenticationRequest {
    email: string;
    password: string;
}