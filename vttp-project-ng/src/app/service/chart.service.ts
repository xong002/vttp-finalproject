import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ChartService {
  http = inject(HttpClient);

  getChartInfo(building: string) {
    return this.http.get('/api/review', { params: { building: building, optLimit: 30 } })
  }
}
